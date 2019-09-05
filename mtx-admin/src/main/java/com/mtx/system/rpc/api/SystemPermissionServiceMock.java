package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseServiceMock;
import com.mtx.common.util.tag.Menu;
import com.mtx.system.dao.dto.SystemPermissionDto;
import com.mtx.system.dao.mapper.SystemPermissionMapper;
import com.mtx.system.dao.model.SystemPermission;
import com.mtx.system.dao.model.SystemPermissionExample;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.vo.SystemPermissionVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
* 降级实现SystemPermissionService接口
* Created by yu on 2018/12/9.
*/
@Slf4j
public class SystemPermissionServiceMock extends BaseServiceMock<SystemPermissionMapper, SystemPermission, SystemPermissionExample> implements SystemPermissionService {
    @Override
    public Map selectForMenu() {
        log.debug("SystemPermissionServiceMock => selectForMenu");
        return null;
    }

    @Override
    public List selectForTopMenu() {
        log.debug("SystemPermissionServiceMock => selectForTopMenu");
        return null;
    }

    @Override
    public String selectForTree() {
        log.debug("SystemPermissionServiceMock => selectForTree");
        return null;
    }

    @Override
    public SystemPermissionVo selectByIdWithLeft(int permissionId) {
        log.debug("SystemPermissionServiceMock => selectByIdWithLeft");
        return null;
    }

    @Override
    public String test() {
        log.debug("SystemPermissionServiceMock => test");
        return null;
    }

    @Override
    public int insertDto(SystemPermissionDto systemPermissionDto) {
        return 0;
    }

    @Override
    public List<Menu> selectForMenuUseJs(int nowMenuId,List<SystemRole> roleList) {
        return null;
    }

    @Override
    public int updateDto(SystemPermissionDto systemPermissionDto) {
        return 0;
    }

    @Override
    public String selectForChooseTree(int id) {
        return null;
    }
}
