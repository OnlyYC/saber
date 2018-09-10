package com.liaoyb.saber.modules.job.mapper;

import com.liaoyb.saber.modules.job.domain.ScheduleJobHistory;
import com.liaoyb.saber.modules.job.dto.ScheduleJobHistoryDTO;

import com.liaoyb.saber.modules.sys.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity ScheduleJobHistory and its DTO ScheduleJobHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ScheduleJobMapper.class})
public interface ScheduleJobHistoryMapper extends EntityMapper<ScheduleJobHistoryDTO, ScheduleJobHistory> {

    @Mapping(source = "scheduleJob.id", target = "scheduleJobId")
    ScheduleJobHistoryDTO toDto(ScheduleJobHistory scheduleJobHistory);

    @Mapping(source = "scheduleJobId", target = "scheduleJob")
    ScheduleJobHistory toEntity(ScheduleJobHistoryDTO scheduleJobHistoryDTO);

    default ScheduleJobHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScheduleJobHistory scheduleJobHistory = new ScheduleJobHistory();
        scheduleJobHistory.setId(id);
        return scheduleJobHistory;
    }
}
