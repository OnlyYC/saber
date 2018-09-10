package com.liaoyb.saber.modules.job.service;

import com.liaoyb.saber.modules.job.dto.ScheduleJobHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ScheduleJobHistory.
 */
public interface ScheduleJobHistoryService {

    /**
     * Save a scheduleJobHistory.
     *
     * @param scheduleJobHistoryDTO the entity to save
     * @return the persisted entity
     */
    ScheduleJobHistoryDTO save(ScheduleJobHistoryDTO scheduleJobHistoryDTO);

    /**
     * Get all the scheduleJobHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ScheduleJobHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" scheduleJobHistory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ScheduleJobHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" scheduleJobHistory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
