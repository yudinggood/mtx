package com.mtx.system.dao.model;

import java.io.Serializable;
import java.util.Date;

public class SystemAttach implements Serializable {
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer attachId;

    /**
     * 业务编码
     *
     * @mbg.generated
     */
    private String bizType;

    /**
     * 业务id
     *
     * @mbg.generated
     */
    private Integer bizId;

    /**
     * 文件名称
     *
     * @mbg.generated
     */
    private String fileName;

    /**
     * 存放路径
     *
     * @mbg.generated
     */
    private String filePath;

    /**
     * 后缀名
     *
     * @mbg.generated
     */
    private String suffix;

    /**
     * 1.本地2.七牛云
     *
     * @mbg.generated
     */
    private Byte addressType;

    /**
     * 新UUID名称
     *
     * @mbg.generated
     */
    private String newName;

    /**
     * 文件大小
     *
     * @mbg.generated
     */
    private Long fileSize;

    /**
     * 修改日期
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

    private static final long serialVersionUID = 1L;

    public Integer getAttachId() {
        return attachId;
    }

    public void setAttachId(Integer attachId) {
        this.attachId = attachId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Byte getAddressType() {
        return addressType;
    }

    public void setAddressType(Byte addressType) {
        this.addressType = addressType;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attachId=").append(attachId);
        sb.append(", bizType=").append(bizType);
        sb.append(", bizId=").append(bizId);
        sb.append(", fileName=").append(fileName);
        sb.append(", filePath=").append(filePath);
        sb.append(", suffix=").append(suffix);
        sb.append(", addressType=").append(addressType);
        sb.append(", newName=").append(newName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", editDate=").append(editDate);
        sb.append(", editUser=").append(editUser);
        sb.append(", deleted=").append(deleted);
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
        SystemAttach other = (SystemAttach) that;
        return (this.getAttachId() == null ? other.getAttachId() == null : this.getAttachId().equals(other.getAttachId()))
            && (this.getBizType() == null ? other.getBizType() == null : this.getBizType().equals(other.getBizType()))
            && (this.getBizId() == null ? other.getBizId() == null : this.getBizId().equals(other.getBizId()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getFilePath() == null ? other.getFilePath() == null : this.getFilePath().equals(other.getFilePath()))
            && (this.getSuffix() == null ? other.getSuffix() == null : this.getSuffix().equals(other.getSuffix()))
            && (this.getAddressType() == null ? other.getAddressType() == null : this.getAddressType().equals(other.getAddressType()))
            && (this.getNewName() == null ? other.getNewName() == null : this.getNewName().equals(other.getNewName()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getEditDate() == null ? other.getEditDate() == null : this.getEditDate().equals(other.getEditDate()))
            && (this.getEditUser() == null ? other.getEditUser() == null : this.getEditUser().equals(other.getEditUser()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAttachId() == null) ? 0 : getAttachId().hashCode());
        result = prime * result + ((getBizType() == null) ? 0 : getBizType().hashCode());
        result = prime * result + ((getBizId() == null) ? 0 : getBizId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFilePath() == null) ? 0 : getFilePath().hashCode());
        result = prime * result + ((getSuffix() == null) ? 0 : getSuffix().hashCode());
        result = prime * result + ((getAddressType() == null) ? 0 : getAddressType().hashCode());
        result = prime * result + ((getNewName() == null) ? 0 : getNewName().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getEditDate() == null) ? 0 : getEditDate().hashCode());
        result = prime * result + ((getEditUser() == null) ? 0 : getEditUser().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }
}