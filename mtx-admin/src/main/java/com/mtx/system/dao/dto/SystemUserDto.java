package com.mtx.system.dao.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel
@Accessors(chain = true)
public class SystemUserDto implements Serializable {
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
    /**
     * qq授权id
     *
     * @mbg.generated
     */
    private String qqOpenId;


    private Map extProps = new HashMap();
    public void setExtProps(Map extProps) {
        this.extProps = extProps;
        if(null!=getRealName()){
            extProps.put("realName",getRealName());
        }
        if(null!=getEmailToken()){
            extProps.put("emailToken",getEmailToken());
        }
    }

    public Map getExtProps() {
        return extProps;
    }
    //姓名
    private String realName;
    //邮箱token
    private String emailToken;


    //用户组织
    private String[] organizationId;
    //用户角色
    private String[] roleId;

    //**登录相关**
    //记住密码
    private String rememberMe;
    private String backUrl;//登录前的地址
    private String code;//验证码
    private String verifyNo;//动态码

    //旧密码
    private String oldPwd;
    //新密码
    private String newPwd;
}
