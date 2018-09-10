package com.liaoyb.saber.modules.job.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ScheduleJob entity.
 */
public class ScheduleJobDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String beanName;

    private String methodName;

    private String params;

    private String cronExpression;

    private Integer status;

    private String remark;

    private Instant createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScheduleJobDTO scheduleJobDTO = (ScheduleJobDTO) o;
        if (scheduleJobDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduleJobDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduleJobDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            ", methodName='" + getMethodName() + "'" +
            ", params='" + getParams() + "'" +
            ", cronExpression='" + getCronExpression() + "'" +
            ", status=" + getStatus() +
            ", remark='" + getRemark() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
