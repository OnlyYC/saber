package com.liaoyb.saber.modules.job.service.impl;

import com.liaoyb.saber.modules.job.service.ScheduleJobHistoryService;
import com.liaoyb.saber.modules.job.domain.ScheduleJobHistory;
import com.liaoyb.saber.modules.job.repository.ScheduleJobHistoryRepository;
import com.liaoyb.saber.modules.job.dto.ScheduleJobHistoryDTO;
import com.liaoyb.saber.modules.job.mapper.ScheduleJobHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing ScheduleJobHistory.
 */
@Service
@Transactional
public class ScheduleJobHistoryServiceImpl implements ScheduleJobHistoryService {

    private final Logger log = LoggerFactory.getLogger(ScheduleJobHistoryServiceImpl.class);

    private final ScheduleJobHistoryRepository scheduleJobHistoryRepository;

    private final ScheduleJobHistoryMapper scheduleJobHistoryMapper;

    public ScheduleJobHistoryServiceImpl(ScheduleJobHistoryRepository scheduleJobHistoryRepository, ScheduleJobHistoryMapper scheduleJobHistoryMapper) {
        this.scheduleJobHistoryRepository = scheduleJobHistoryRepository;
        this.scheduleJobHistoryMapper = scheduleJobHistoryMapper;
    }

    /**
     * Save a scheduleJobHistory.
     *
     * @param scheduleJobHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduleJobHistoryDTO save(ScheduleJobHistoryDTO scheduleJobHistoryDTO) {
        log.debug("Request to save ScheduleJobHistory : {}", scheduleJobHistoryDTO);
        ScheduleJobHistory scheduleJobHistory = scheduleJobHistoryMapper.toEntity(scheduleJobHistoryDTO);
        scheduleJobHistory = scheduleJobHistoryRepository.save(scheduleJobHistory);
        return scheduleJobHistoryMapper.toDto(scheduleJobHistory);
    }

    /**
     * Get all the scheduleJobHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleJobHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScheduleJobHistories");
        return scheduleJobHistoryRepository.findAll(pageable)
            .map(scheduleJobHistoryMapper::toDto);
    }


    /**
     * Get one scheduleJobHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleJobHistoryDTO> findOne(Long id) {
        log.debug("Request to get ScheduleJobHistory : {}", id);
        return scheduleJobHistoryRepository.findById(id)
            .map(scheduleJobHistoryMapper::toDto);
    }

    /**
     * Delete the scheduleJobHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleJobHistory : {}", id);
        scheduleJobHistoryRepository.deleteById(id);
    }
}
