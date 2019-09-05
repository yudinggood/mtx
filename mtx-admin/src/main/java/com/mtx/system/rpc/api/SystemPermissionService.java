package com.mtx.system.rpc.api;

import com.mtx.common.base.BaseService;
import com.mtx.common.util.tag.Menu;
import com.mtx.system.dao.dto.SystemPermissionDto;
import com.mtx.system.dao.model.SystemPermission;
import com.mtx.system.dao.model.SystemPermissionExample;
import com.mtx.system.dao.model.SystemRole;
import com.mtx.system.dao.vo.SystemPermissionVo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* SystemPermissionService接口
* Created by yu on 2018/12/9.
*/
public interface SystemPermissionService extends BaseService<SystemPermission, SystemPermissionExample> {

    Map selectForMenu();

    List selectForTopMenu();

    String selectForTree();

    SystemPermissionVo selectByIdWithLeft(int permissionId);

    String test();

    int insertDto(SystemPermissionDto systemPermissionDto);

    List<Menu> selectForMenuUseJs(int nowMenuId,List<SystemRole> roleList);

    int updateDto(SystemPermissionDto systemPermissionDto);

    String selectForChooseTree(int id);
}