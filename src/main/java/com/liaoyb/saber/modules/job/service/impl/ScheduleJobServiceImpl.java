package com.liaoyb.saber.modules.job.service.impl;

import com.liaoyb.saber.modules.job.domain.ScheduleJob;
import com.liaoyb.saber.enums.ScheduleStatus;
import com.liaoyb.saber.modules.job.repository.ScheduleJobRepository;
import com.liaoyb.saber.modules.job.service.ScheduleJobService;
import com.liaoyb.saber.modules.job.dto.ScheduleJobDTO;
import com.liaoyb.saber.modules.job.mapper.ScheduleJobMapper;
import com.liaoyb.saber.modules.job.core.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ScheduleJob.
 */
@Service
@Transactional
public class ScheduleJobServiceImpl implements ScheduleJobService {

    private final Logger log = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);

    private final ScheduleJobRepository scheduleJobRepository;

    private final ScheduleJobMapper scheduleJobMapper;

    private final Scheduler scheduler;

    public ScheduleJobServiceImpl(ScheduleJobRepository scheduleJobRepository, ScheduleJobMapper scheduleJobMapper, Scheduler scheduler) {
        this.scheduleJobRepository = scheduleJobRepository;
        this.scheduleJobMapper = scheduleJobMapper;
        this.scheduler = scheduler;
    }

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJob> scheduleJobList = scheduleJobRepository.findAll();
        for (ScheduleJob scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    /**
     * Save a scheduleJob.
     *
     * @param scheduleJobDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduleJobDTO save(ScheduleJobDTO scheduleJobDTO) {
        log.debug("Request to save ScheduleJob : {}", scheduleJobDTO);
        ScheduleJob scheduleJob = scheduleJobMapper.toEntity(scheduleJobDTO);
        scheduleJob = scheduleJobRepository.save(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        return scheduleJobMapper.toDto(scheduleJob);
    }

    /**
     * Get all the scheduleJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleJobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScheduleJobs");
        return scheduleJobRepository.findAll(pageable)
                .map(scheduleJobMapper::toDto);
    }


    /**
     * Get one scheduleJob by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleJobDTO> findOne(Long id) {
        log.debug("Request to get ScheduleJob : {}", id);
        return scheduleJobRepository.findById(id)
                .map(scheduleJobMapper::toDto);
    }

    /**
     * Delete the scheduleJob by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleJob : {}", id);
        ScheduleUtils.deleteScheduleJob(scheduler, id);
        scheduleJobRepository.deleteById(id);
    }

    /**
     * Update a scheduleJob.
     *
     * @param scheduleJobDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ScheduleJobDTO update(ScheduleJobDTO scheduleJobDTO) {
        log.debug("Request to update ScheduleJob : {}", scheduleJobDTO);
        ScheduleJob scheduleJob = scheduleJobMapper.toEntity(scheduleJobDTO);
        scheduleJob = scheduleJobRepository.save(scheduleJob);

        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        return scheduleJobMapper.toDto(scheduleJob);
    }

    @Override
    public void run(Long id) {
        ScheduleJob scheduleJob = scheduleJobRepository.getOne(id);
        log.debug("load ScheduleJob : {} :{}", id, scheduleJob);

        ScheduleUtils.run(scheduler, scheduleJob);
    }

    @Override
    public void pause(Long id) {
        ScheduleUtils.pauseJob(scheduler, id);

        updateScheduleStatus(id, ScheduleStatus.PAUSE);
    }

    @Override
    public void resume(Long id) {
        ScheduleUtils.resumeJob(scheduler, id);

        updateScheduleStatus(id, ScheduleStatus.NORMAL);
    }

    private void updateScheduleStatus(Long id, ScheduleStatus scheduleStatus) {
        ScheduleJob scheduleJob = scheduleJobRepository.getOne(id);
        scheduleJob.setStatus(scheduleStatus.getValue());
        scheduleJobRepository.save(scheduleJob);
    }
}
