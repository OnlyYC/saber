package com.liaoyb.saber.modules.job.repository;

import com.liaoyb.saber.modules.job.domain.ScheduleJobHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ScheduleJobHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleJobHistoryRepository extends JpaRepository<ScheduleJobHistory, Long> {

}
