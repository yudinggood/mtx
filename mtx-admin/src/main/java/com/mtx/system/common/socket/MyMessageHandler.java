package com.mtx.system.common.socket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//消息处理器
@Slf4j
public class MyMessageHandler implements WebSocketHandler {
    //用户key
    public static final String USER_KEY = "current_user";

    /**
     * userMap:存储用户连接webscoket信息
     */
    private final static Map<String, WebSocketSession> USER_MAP;
    static {
        USER_MAP = new ConcurrentHashMap<String,WebSocketSession>(30);
    }
    /**
     * 关闭websocket时调用该方法
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = this.getUserId(session);
        if(StringUtils.isNotBlank(userId)){
            USER_MAP.remove(userId);
        }

    }

    /**
     * 建立websocket连接时调用该方法
     * @see org.springframework.web.socket.WebSocketHandler#afterConnectionEstablished(org.springframework.web.socket.WebSocketSession)
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {


        String userId = this.getUserId(session);
        if(StringUtils.isNotBlank(userId)){
            USER_MAP.put(userId, session);
            //session.sendMessage(new TextMessage("建立WebSocket连接成功！"));
        }

    }

    /**
     * 客户端调用websocket.send时候，会调用该方法,进行数据通信
     * @see org.springframework.web.socket.WebSocketHandler#handleMessage(org.springframework.web.socket.WebSocketSession, org.springframework.web.socket.WebSocketMessage)
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.toString();
        message = new TextMessage("服务端已经接收到消息，msg="+msg);
        session.sendMessage(message);

    }

    /**
     * 传输过程出现异常时，调用该方法
     * @see org.springframework.web.socket.WebSocketHandler#handleTransportError(org.springframework.web.socket.WebSocketSession, java.lang.Throwable)
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable e) throws Exception {

    }

    /**
     *
     * @see org.springframework.web.socket.WebSocketHandler#supportsPartialMessages()
     */
    @Override
    public boolean supportsPartialMessages() {

        return false;
    }

    /**
     * sendMessageToUser:发给指定用户
     */
    public void sendMessageToUser(String userId,String contents) {
        WebSocketSession session = USER_MAP.get(userId);
        if(session !=null && session.isOpen()) {
            try {
                TextMessage message = new TextMessage(contents);
                session.sendMessage(message);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    /**
     * sendMessageToAllUsers:发给所有的用户
     */
    public void sendMessageToAllUsers(String contents) {
        Set<String> userIds = USER_MAP.keySet();
        for(String userId: userIds) {
            this.sendMessageToUser(userId, contents);
        }
    }

    /**
     * getUserId:获取用户id
     */
    private String getUserId(WebSocketSession session){
        try {
            String userId = (String)session.getAttributes().get(USER_KEY);
            return userId;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

}
