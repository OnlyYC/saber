package com.liaoyb.saber.modules.job.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ScheduleJobHistory entity.
 */
public class ScheduleJobHistoryDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String beanName;

    private String methodName;

    private String params;

    private String cronExpression;

    private Integer status;

    private String error;

    private Long elapsedTime;

    private Instant createTime;

    private Instant endTime;

    private Long scheduleJobId;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(Long scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScheduleJobHistoryDTO scheduleJobHistoryDTO = (ScheduleJobHistoryDTO) o;
        if (scheduleJobHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduleJobHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduleJobHistoryDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            ", methodName='" + getMethodName() + "'" +
            ", params='" + getParams() + "'" +
            ", cronExpression='" + getCronExpression() + "'" +
            ", status=" + getStatus() +
            ", error='" + getError() + "'" +
            ", elapsedTime=" + getElapsedTime() +
            ", createTime='" + getCreateTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", scheduleJob=" + getScheduleJobId() +
            "}";
    }
}
