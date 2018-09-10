package com.liaoyb.saber.modules.job.mapper;

import com.liaoyb.saber.modules.job.domain.ScheduleJob;
import com.liaoyb.saber.modules.job.dto.ScheduleJobDTO;

import com.liaoyb.saber.modules.sys.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ScheduleJob and its DTO ScheduleJobDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScheduleJobMapper extends EntityMapper<ScheduleJobDTO, ScheduleJob> {



    default ScheduleJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setId(id);
        return scheduleJob;
    }
}
