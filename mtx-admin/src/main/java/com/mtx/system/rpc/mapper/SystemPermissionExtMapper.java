package com.mtx.system.rpc.mapper;


import com.mtx.system.dao.model.SystemPermission;
import com.mtx.system.dao.vo.SystemPermissionVo;

import java.util.List;

public interface SystemPermissionExtMapper {
	SystemPermissionVo selectByIdWithLeft(int permissionId);

	List<SystemPermission> selectByroleList(List<Integer> list);
}
