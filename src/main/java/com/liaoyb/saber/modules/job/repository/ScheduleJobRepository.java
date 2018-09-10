package com.liaoyb.saber.modules.job.repository;

import com.liaoyb.saber.modules.job.domain.ScheduleJob;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ScheduleJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long> {

}
