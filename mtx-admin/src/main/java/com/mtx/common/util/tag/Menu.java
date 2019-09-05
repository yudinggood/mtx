package com.mtx.common.util.tag;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer permissionId;

    /**
     * 系统id
     *
     * @mbg.generated
     */
    private Integer systemId;

    /**
     * 父id
     *
     * @mbg.generated
     */
    private Integer permissionPid;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Integer permissionOrder;

    /**
     * 权限名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 权限值
     *
     * @mbg.generated
     */
    private String permissionValue;

    /**
     * 路径
     *
     * @mbg.generated
     */
    private String uri;

    /**
     * 图标
     *
     * @mbg.generated
     */
    private String icon;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String description;

    /**
     * 权限类型0根1目录2菜单3按钮
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     * 状态0禁止1正常
     *
     * @mbg.generated
     */
    private Byte status;

    /**
     * 菜单层级 0开始 没有则不是菜单
     *
     * @mbg.generated
     */
    private Byte menuLevel;

    /**
     * 修改时间
     *
     * @mbg.generated
     */
    private Date editDate;

    /**
     * 修改人
     *
     * @mbg.generated
     */
    private Integer editUser;



    public boolean hasSubFunction(Map<Integer, List<Menu>> map) {
        if(map.containsKey(this.getMenuLevel()+1)){
            return hasSubFunction(map.get(this.getMenuLevel()+1));
        }
        return false;
    }
    public boolean hasSubFunction(List<Menu> functions) {
        for (Menu f : functions) {
            if(f!=null ){
                if(f.getPermissionPid().equals(this.getPermissionId())){
                    return true;
                }
            }

        }
        return false;
    }

    //js  menu用到的
    private List<Menu> subMenu;
    private boolean hasMenu = false;
}










