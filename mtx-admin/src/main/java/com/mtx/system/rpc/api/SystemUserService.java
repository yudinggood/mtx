package com.mtx.system.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.mtx.common.base.BaseService;
import com.mtx.system.dao.dto.SystemUserDto;
import com.mtx.system.dao.model.SystemUser;
import com.mtx.system.dao.model.SystemUserExample;
import com.mtx.system.dao.vo.SystemUserVo;

import java.util.List;

/**
* SystemUserService接口
* Created by yu on 2018/12/9.
*/
public interface SystemUserService extends BaseService<SystemUser, SystemUserExample> {


    List<SystemUserVo> list(Page<SystemUserVo> page, int organizationId, String search);

    SystemUserVo selectByIdWithLeft(int userId);

    Integer insertDto(SystemUserDto systemUserDto);

    Integer updateDto(SystemUserDto systemUserDto);

    int deleteByPrimaryKeyFaker(int id);

    int deleteByPrimaryKeysFaker(String ids);

    SystemUser selectByQqOpenId(String openid);

    void updateOpenId(SystemUser systemUser);

    void updateByUser(SystemUser newUser);

    int updateDtoOnly(SystemUserDto systemUserDto);

    void activeEmail(SystemUserDto systemUserDto);
}