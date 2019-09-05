package com.mtx.system.dao.model;

import java.io.Serializable;
import java.util.Date;

public class SystemDic implements Serializable {
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer dicId;

    /**
     * 父id
     *
     * @mbg.generated
     */
    private Integer dicPid;

    /**
     * 字典编码
     *
     * @mbg.generated
     */
    private String dicCode;

    /**
     * 字典名称
     *
     * @mbg.generated
     */
    private String dicName;

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

    private static final long serialVersionUID = 1L;

    public Integer getDicId() {
        return dicId;
    }

    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }

    public Integer getDicPid() {
        return dicPid;
    }

    public void setDicPid(Integer dicPid) {
        this.dicPid = dicPid;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dicId=").append(dicId);
        sb.append(", dicPid=").append(dicPid);
        sb.append(", dicCode=").append(dicCode);
        sb.append(", dicName=").append(dicName);
        sb.append(", editDate=").append(editDate);
        sb.append(", editUser=").append(editUser);
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
        SystemDic other = (SystemDic) that;
        return (this.getDicId() == null ? other.getDicId() == null : this.getDicId().equals(other.getDicId()))
            && (this.getDicPid() == null ? other.getDicPid() == null : this.getDicPid().equals(other.getDicPid()))
            && (this.getDicCode() == null ? other.getDicCode() == null : this.getDicCode().equals(other.getDicCode()))
            && (this.getDicName() == null ? other.getDicName() == null : this.getDicName().equals(other.getDicName()))
            && (this.getEditDate() == null ? other.getEditDate() == null : this.getEditDate().equals(other.getEditDate()))
            && (this.getEditUser() == null ? other.getEditUser() == null : this.getEditUser().equals(other.getEditUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDicId() == null) ? 0 : getDicId().hashCode());
        result = prime * result + ((getDicPid() == null) ? 0 : getDicPid().hashCode());
        result = prime * result + ((getDicCode() == null) ? 0 : getDicCode().hashCode());
        result = prime * result + ((getDicName() == null) ? 0 : getDicName().hashCode());
        result = prime * result + ((getEditDate() == null) ? 0 : getEditDate().hashCode());
        result = prime * result + ((getEditUser() == null) ? 0 : getEditUser().hashCode());
        return result;
    }
}