package com.liaoyb.saber.modules.job.service;

import com.liaoyb.saber.modules.job.dto.ScheduleJobDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ScheduleJob.
 */
public interface ScheduleJobService {

    /**
     * Save a scheduleJob.
     *
     * @param scheduleJobDTO the entity to save
     * @return the persisted entity
     */
    ScheduleJobDTO save(ScheduleJobDTO scheduleJobDTO);

    /**
     * Get all the scheduleJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ScheduleJobDTO> findAll(Pageable pageable);

    /**
     * Get the "id" scheduleJob.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ScheduleJobDTO> findOne(Long id);

    /**
     * Delete the "id" scheduleJob.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * update a scheduleJob
     *
     * @param scheduleJobDTO the entity to update
     * @return the persisted entity
     */
    ScheduleJobDTO update(ScheduleJobDTO scheduleJobDTO);


    /**
     * 立即执行任务
     *
     * @param id ob id
     */
    void run(Long id);


    /**
     * 暂停定时任务
     *
     * @param id job id
     */
    void pause(Long id);

    /**
     * 恢复定时任务
     *
     * @param id job id
     */
    void resume(Long id);

}
