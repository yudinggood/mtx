package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseServiceMock;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.mapper.SystemUserMapper;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.dao.model.SystemUserExample;
import com.mtx.system.dao.vo.SystemUserVo;

import java.util.List;

/**
* 降级实现SystemUserService接口
* Created by yu on 2018/12/9.
*/
public class SystemUserServiceMock extends BaseServiceMock<SystemUserMapper, SystemUser, SystemUserExample> implements SystemUserService {
    @Override
    public List<SystemUserVo> list(Page<SystemUserVo> page, int organizationId, String search) {
        return null;
    }

    @Override
    public SystemUserVo selectByIdWithLeft(int userId) {
        return null;
    }

    @Override
    public Integer insertDto(SystemUserDto systemUserDto) {
        return 0;
    }

    @Override
    public Integer updateDto(SystemUserDto systemUserDto) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKeyFaker(int id) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKeysFaker(String ids) {
        return 0;
    }

    @Override
    public SystemUser selectByQqOpenId(String openid) {
        return null;
    }

    @Override
    public void updateOpenId(SystemUser systemUser) {

    }

    @Override
    public void updateByUser(SystemUser newUser) {

    }

    @Override
    public int updateDtoOnly(SystemUserDto systemUserDto) {
        return 0;
    }

    @Override
    public void activeEmail(SystemUserDto systemUserDto) {

    }
}
