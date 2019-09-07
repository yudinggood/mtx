package com.mtx.system.rpc.factory;

import com.mtx.common.util.base.ToolUtil;
import com.mtx.common.util.base.TypeConversionUtil;
import com.mtx.common.util.factory.BaseFactory;
import com.mtx.common.util.tag.Menu;
import com.mtx.system.common.bean.DictCacheKit;
import com.mtx.system.common.enums.DictEnum;
import com.mtx.system.dao.dto.SystemPermissionDto;
import com.mtx.system.dao.model.SystemPermission;
import com.mtx.system.dao.model.SystemRolePermission;
import com.mtx.system.dao.vo.SystemPermissionVo;
import com.mtx.system.dao.vo.Ztree;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SystemPermissionFactory extends BaseFactory{

    @Override
    protected <V, E> V convertAttribute(E e, V v) {


        return v;
    }


    public SystemPermission convertDtoToDo(SystemPermissionDto systemPermissionDto, Class<SystemPermission> systemPermissionClass) {
        SystemPermission systemPermission = convertModel(systemPermissionDto,systemPermissionClass);
        systemPermission.setEditDate(new Date());
        if(systemPermission.getType().equals(TypeConversionUtil.objectToByte(2))){//过期代码
            systemPermission.setMenuLevel(TypeConversionUtil.objectToByte(0));
        }
        return systemPermission;
    }

    //递归
    public List<Menu> convertRecursion(List<Menu> menuList,Integer nowMenuId) {
        List<Menu> result= new ArrayList<>();
        for (Menu menu : menuList) {
            Integer pid=menu.getPermissionPid();
            Integer id =menu.getPermissionId();
            if (nowMenuId.equals(pid)) {
                List<Menu> sublist=convertRecursion(menuList,id);
                menu.setSubMenu(sublist);
                if(!ToolUtil.isEmpty(sublist)){
                    menu.setHasMenu(true);
                }
                result.add(menu);
            }
        }
        return result;
    }

    public SystemPermissionVo convertDic(SystemPermissionVo vo) {
        vo.setTypeName(DictCacheKit.me().getDictNameByCode(DictEnum.PERMISSION_TYPE,""+vo.getType()));
        vo.setStatusName(DictCacheKit.me().getDictNameByCode(DictEnum.PERMISSION_STATE,""+vo.getStatus()));
        return vo;
    }

    public List<Ztree> convertChooseTree(List<SystemPermission> list, List<SystemRolePermission> systemRolePermissionList) {
        List<Ztree> ztreeList = new ArrayList<>();
        for (SystemPermission systemPermission:list){
            Ztree ztree = dozer.map(systemPermission,Ztree.class);
            if(systemPermission.getType()==0||systemPermission.getType()==1){
                ztree.setNocheck(true);
            }
            for (SystemRolePermission systemRolePermission:systemRolePermissionList){
                if(systemRolePermission.getPermissionId().equals(systemPermission.getPermissionId())){
                    ztree.setChecked(true);
                    break;
                }
            }

            ztreeList.add(ztree);
        }
        return ztreeList;
    }
}
