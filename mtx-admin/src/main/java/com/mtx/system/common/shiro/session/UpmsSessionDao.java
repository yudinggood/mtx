package com.mtx.system.common.shiro.session;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.*;
import com.mtx.system.dao.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 基于redis的sessionDao，缓存共享session
 */
@Slf4j
public class UpmsSessionDao extends CachingSessionDAO {
    // 会话key
    private final static String UPMS_SHIRO_SESSION_ID = "upms-shiro-session-id";
    // 全局会话key
    private final static String UPMS_SERVER_SESSION_ID = "upms-server-session-id";
    // 全局会话列表key
    private final static String UPMS_SERVER_SESSION_IDS = "upms-server-session-ids";
    // code key
    private final static String UPMS_SERVER_CODE = "upms-server-code";




    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return;
        }
        // 更新session的最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute(SystemConstant.FORCE_LOGOUT, cacheUpmsSession.getAttribute(SystemConstant.FORCE_LOGOUT));
        }
        RedisUtil.set(UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        // 更新UPMS_SERVER_SESSION_ID、UPMS_SERVER_CODE过期时间 TODO
        log.debug("doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        String upmsType = ObjectUtils.toString(session.getAttribute(SystemConstant.UPMS_TYPE));
        String oauthLogin = RedisUtil.get(SystemConstant.UPMS_WITHOUT_PASSWORD + "_" + sessionId);
        if (SystemConstant.CLIENT.equals(upmsType)||ToolUtil.isNotEmpty(oauthLogin)) {
            // 删除局部会话和同一code注册的局部会话
            String code = RedisUtil.get(SystemConstant.UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            Jedis jedis = RedisUtil.getJedis();
            jedis.del(SystemConstant.UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            jedis.del(SystemConstant.UPMS_WITHOUT_PASSWORD + "_" + sessionId);
            jedis.srem(SystemConstant.UPMS_CLIENT_SESSION_IDS + "_" + code, sessionId);
            jedis.close();
        }
        if (SystemConstant.SERVER.equals(upmsType)) {
            // 当前全局会话code
            String code = RedisUtil.get(UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除全局会话
            RedisUtil.remove(UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除code校验值
            RedisUtil.remove(UPMS_SERVER_CODE + "_" + code);
            // 清除所有局部会话
            Jedis jedis = RedisUtil.getJedis();
            Set<String> clientSessionIds = jedis.smembers(SystemConstant.UPMS_CLIENT_SESSION_IDS + "_" + code);
            for (String clientSessionId : clientSessionIds) {
                jedis.del(SystemConstant.UPMS_CLIENT_SESSION_ID + "_" + clientSessionId);
                jedis.srem(SystemConstant.UPMS_CLIENT_SESSION_IDS + "_" + code, clientSessionId);
            }
            log.debug("当前code={}，对应的注册系统个数：{}个", code, jedis.scard(SystemConstant.UPMS_CLIENT_SESSION_IDS + "_" + code));
            jedis.close();
            // 维护会话id列表，提供会话分页管理
            RedisUtil.lrem(UPMS_SERVER_SESSION_IDS, 1, sessionId);
        }
        // 删除session
        RedisUtil.remove(UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        log.debug("doUpdate >>>>> sessionId={}", sessionId);
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        RedisUtil.set(UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        log.debug("doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = RedisUtil.get(UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        log.debug("doReadSession >>>>> sessionId={}", sessionId);
        return SerializableUtil.deserialize(session);
    }

    /**
     * 获取会话列表
     * @return
     */

    public List<Session> list(Page<Session> page) {
        List<Session> list =new ArrayList<>();
        Jedis jedis = RedisUtil.getJedis();
        long total = jedis.llen(UPMS_SERVER_SESSION_IDS);
        // 获取当前页会话详情 0-19
        List<String> ids = jedis.lrange(UPMS_SERVER_SESSION_IDS, page.getOffset(), (page.getOffset() + page.getLimit() - 1));
        //List<String> ids = jedis.lrange(UPMS_SERVER_SESSION_IDS, (page.getCurrent()-1)*page.getSize()+1, page.getCurrent()*page.getSize() );
        for (String id : ids) {
            String sessionId = RedisUtil.get(UPMS_SHIRO_SESSION_ID + "_" + id);
            // 过滤redis过期session
            if (null == sessionId) {
                RedisUtil.lrem(UPMS_SERVER_SESSION_IDS, 1, id);
                total = total - 1;
                continue;
            }
            list.add(SerializableUtil.deserialize(sessionId));
        }
        page.setTotal(TypeConversionUtil.objectToInt(total));
        jedis.close();
        return list;
    }

    /**
     * 强制退出
     * @param sessionId
     * @return
     */
    public int forceout(String sessionId) {
        // 会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
        String session = RedisUtil.get(UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
        upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
        upmsSession.setAttribute(SystemConstant.FORCE_LOGOUT,SystemConstant.FORCE_LOGOUT);
        RedisUtil.set(UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(upmsSession), (int) upmsSession.getTimeout() / 1000);
        return 1;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, UpmsSession.OnlineStatus onlineStatus) {
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }

    //同时只允许一个账号登录
    public void otherFouceOut(Integer userId) {
        Jedis jedis = RedisUtil.getJedis();
        List<String> ids = jedis.lrange(UPMS_SERVER_SESSION_IDS, 0,9);
        for (String sessionId : ids){
            String session = RedisUtil.get(UPMS_SHIRO_SESSION_ID + "_" + sessionId);
            if(ToolUtil.isNotEmpty(session)){
                UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
                SystemUser tempUser = (SystemUser)  ThreadLocalUtil.get(SystemConstant.SESSION_SYSTEM_USER);
                if(tempUser != null&&userId.equals(tempUser.getUserId())) {
                    upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
                    upmsSession.setAttribute(SystemConstant.FORCE_LOGOUT, SystemConstant.FORCE_LOGOUT);
                    RedisUtil.set(UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(upmsSession), (int) upmsSession.getTimeout() / 1000);
                    break;
                }
            }


        }
    }


}
