package com.liaoyb.saber.modules.job.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ScheduleJobHistory.
 */
@Entity
@Table(name = "schedule_job_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ScheduleJobHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "bean_name")
    private String beanName;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "params")
    private String params;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "status")
    private Integer status;

    @Column(name = "error")
    private String error;

    @Column(name = "elapsed_time")
    private Long elapsedTime;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "end_time")
    private Instant endTime;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ScheduleJob scheduleJob;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public ScheduleJobHistory title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public ScheduleJobHistory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public ScheduleJobHistory beanName(String beanName) {
        this.beanName = beanName;
        return this;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public ScheduleJobHistory methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public ScheduleJobHistory params(String params) {
        this.params = params;
        return this;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public ScheduleJobHistory cronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Integer getStatus() {
        return status;
    }

    public ScheduleJobHistory status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public ScheduleJobHistory error(String error) {
        this.error = error;
        return this;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public ScheduleJobHistory elapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
        return this;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public ScheduleJobHistory createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public ScheduleJobHistory endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public ScheduleJob getScheduleJob() {
        return scheduleJob;
    }

    public ScheduleJobHistory scheduleJob(ScheduleJob scheduleJob) {
        this.scheduleJob = scheduleJob;
        return this;
    }

    public void setScheduleJob(ScheduleJob scheduleJob) {
        this.scheduleJob = scheduleJob;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScheduleJobHistory scheduleJobHistory = (ScheduleJobHistory) o;
        if (scheduleJobHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduleJobHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduleJobHistory{" +
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
            "}";
    }
}
