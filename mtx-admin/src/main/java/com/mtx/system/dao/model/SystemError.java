package com.mtx.system.dao.model;

import java.io.Serializable;
import java.util.Date;

public class SystemError implements Serializable {
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer errorId;

    /**
     * 错误类型1登录日志2异常日志3业务异常日志
     *
     * @mbg.generated
     */
    private Byte errorType;

    /**
     * 类
     *
     * @mbg.generated
     */
    private String className;

    /**
     * 方法
     *
     * @mbg.generated
     */
    private String method;

    /**
     * 用户
     *
     * @mbg.generated
     */
    private Integer editUser;

    /**
     * 修改时间
     *
     * @mbg.generated
     */
    private Date editDate;

    /**
     * 1是删除了
     *
     * @mbg.generated
     */
    private Byte deleted;

    /**
     * 编码
     *
     * @mbg.generated
     */
    private String code;

    /**
     * 信息
     *
     * @mbg.generated
     */
    private String message;

    private static final long serialVersionUID = 1L;

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public Byte getErrorType() {
        return errorType;
    }

    public void setErrorType(Byte errorType) {
        this.errorType = errorType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getEditUser() {
        return editUser;
    }

    public void setEditUser(Integer editUser) {
        this.editUser = editUser;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", errorId=").append(errorId);
        sb.append(", errorType=").append(errorType);
        sb.append(", className=").append(className);
        sb.append(", method=").append(method);
        sb.append(", editUser=").append(editUser);
        sb.append(", editDate=").append(editDate);
        sb.append(", deleted=").append(deleted);
        sb.append(", code=").append(code);
        sb.append(", message=").append(message);
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
        SystemError other = (SystemError) that;
        return (this.getErrorId() == null ? other.getErrorId() == null : this.getErrorId().equals(other.getErrorId()))
            && (this.getErrorType() == null ? other.getErrorType() == null : this.getErrorType().equals(other.getErrorType()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getEditUser() == null ? other.getEditUser() == null : this.getEditUser().equals(other.getEditUser()))
            && (this.getEditDate() == null ? other.getEditDate() == null : this.getEditDate().equals(other.getEditDate()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getErrorId() == null) ? 0 : getErrorId().hashCode());
        result = prime * result + ((getErrorType() == null) ? 0 : getErrorType().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getEditUser() == null) ? 0 : getEditUser().hashCode());
        result = prime * result + ((getEditDate() == null) ? 0 : getEditDate().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        return result;
    }
}