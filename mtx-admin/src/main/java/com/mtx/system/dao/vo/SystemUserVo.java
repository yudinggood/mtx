package com.mtx.system.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class SystemUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * 登录id 邮箱或手机号
     *
     * @mbg.generated
     */
    private String loginId;

    /**
     * 昵称
     *
     * @mbg.generated
     */
    private String nickName;

    /**
     * 密码
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 密码的盐
     *
     * @mbg.generated
     */
    private String salt;

    /**
     * 头像路径
     *
     * @mbg.generated
     */
    private String avatar;

    /**
     * 手机号
     *
     * @mbg.generated
     */
    private String phone;

    /**
     * 最后登录IP
     *
     * @mbg.generated
     */
    private String lastIp;

    /**
     * 邮箱
     *
     * @mbg.generated
     */
    private String email;

    /**
     * 性别
     *
     * @mbg.generated
     */
    private Byte sex;

    /**
     * 状态1正常2封禁
     *
     * @mbg.generated
     */
    private Byte userState;

    /**
     * 最后登录时间
     *
     * @mbg.generated
     */
    private Date lastLogin;

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

    /**
     * 1是删除了
     *
     * @mbg.generated
     */
    private Byte deleted;

    /**
     * 扩展字段
     *
     * @mbg.generated
     */
    private byte[] extendMap;

    //修改或添加页面
    private String page;
    private Map extProps = new HashMap();

    public Map getExtProps() {
        return extProps;
    }

    public void setExtProps(Map extProps) {
        this.extProps = extProps;
        if(null!=extProps){
            realName= (String) extProps.get("realName");
        }

    }

    //姓名
    private String realName;


    //性别
    private String sexName ;
    //状态
    private String userStateName;



}
