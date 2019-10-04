package com.mtx.system.dao.model;

import java.io.Serializable;
import java.util.Date;

public class SystemTask implements Serializable {
    /**
     * id
     *
     * @mbg.generated
     */
    private Integer taskId;

    /**
     * 名称
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 任务类型
     *
     * @mbg.generated
     */
    private Byte type;

    /**
     * 任务状态 1提醒2不提醒
     *
     * @mbg.generated
     */
    private Byte state;

    /**
     * cron表达式
     *
     * @mbg.generated
     */
    private String cron;

    /**
     * 任务时间
     *
     * @mbg.generated
     */
    private String taskTime;

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

    private static final long serialVersionUID = 1L;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
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
        sb.append(", taskId=").append(taskId);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", state=").append(state);
        sb.append(", cron=").append(cron);
        sb.append(", taskTime=").append(taskTime);
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
        SystemTask other = (SystemTask) that;
        return (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getCron() == null ? other.getCron() == null : this.getCron().equals(other.getCron()))
            && (this.getTaskTime() == null ? other.getTaskTime() == null : this.getTaskTime().equals(other.getTaskTime()))
            && (this.getEditDate() == null ? other.getEditDate() == null : this.getEditDate().equals(other.getEditDate()))
            && (this.getEditUser() == null ? other.getEditUser() == null : this.getEditUser().equals(other.getEditUser()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getCron() == null) ? 0 : getCron().hashCode());
        result = prime * result + ((getTaskTime() == null) ? 0 : getTaskTime().hashCode());
        result = prime * result + ((getEditDate() == null) ? 0 : getEditDate().hashCode());
        result = prime * result + ((getEditUser() == null) ? 0 : getEditUser().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }
}