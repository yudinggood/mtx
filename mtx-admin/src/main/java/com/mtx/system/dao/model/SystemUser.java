package com.mtx.system.dao.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class SystemUser implements Serializable {
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer userId;

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
     * qq授权id
     *
     * @mbg.generated
     */
    private String qqOpenId;

    /**
     * 状态1正常2封禁3未激活
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

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public Byte getUserState() {
        return userState;
    }

    public void setUserState(Byte userState) {
        this.userState = userState;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Integer getEditUser() {
        return editUser;
    }

    public void setEditUser(Integer editUser) {
        this.editUser = editUser;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public byte[] getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(byte[] extendMap) {
        this.extendMap = extendMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", nickName=").append(nickName);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", avatar=").append(avatar);
        sb.append(", phone=").append(phone);
        sb.append(", lastIp=").append(lastIp);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", qqOpenId=").append(qqOpenId);
        sb.append(", userState=").append(userState);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", editDate=").append(editDate);
        sb.append(", editUser=").append(editUser);
        sb.append(", deleted=").append(deleted);
        sb.append(", extendMap=").append(extendMap);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SystemUser other = (SystemUser) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getLastIp() == null ? other.getLastIp() == null : this.getLastIp().equals(other.getLastIp()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getQqOpenId() == null ? other.getQqOpenId() == null : this.getQqOpenId().equals(other.getQqOpenId()))
            && (this.getUserState() == null ? other.getUserState() == null : this.getUserState().equals(other.getUserState()))
            && (this.getLastLogin() == null ? other.getLastLogin() == null : this.getLastLogin().equals(other.getLastLogin()))
            && (this.getEditDate() == null ? other.getEditDate() == null : this.getEditDate().equals(other.getEditDate()))
            && (this.getEditUser() == null ? other.getEditUser() == null : this.getEditUser().equals(other.getEditUser()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (Arrays.equals(this.getExtendMap(), other.getExtendMap()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getLastIp() == null) ? 0 : getLastIp().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getQqOpenId() == null) ? 0 : getQqOpenId().hashCode());
        result = prime * result + ((getUserState() == null) ? 0 : getUserState().hashCode());
        result = prime * result + ((getLastLogin() == null) ? 0 : getLastLogin().hashCode());
        result = prime * result + ((getEditDate() == null) ? 0 : getEditDate().hashCode());
        result = prime * result + ((getEditUser() == null) ? 0 : getEditUser().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + (Arrays.hashCode(getExtendMap()));
        return result;
    }
}