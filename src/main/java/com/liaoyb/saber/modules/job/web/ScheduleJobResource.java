package com.liaoyb.saber.modules.job.web;

import com.codahale.metrics.annotation.Timed;
import com.liaoyb.saber.modules.job.service.ScheduleJobService;
import com.liaoyb.saber.modules.job.dto.ScheduleJobDTO;
import com.liaoyb.saber.web.errors.BadRequestAlertException;
import com.liaoyb.saber.web.util.HeaderUtil;
import com.liaoyb.saber.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ScheduleJob.
 */
@RestController
@RequestMapping("/api")
public class ScheduleJobResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleJobResource.class);

    private static final String ENTITY_NAME = "scheduleJob";

    private final ScheduleJobService scheduleJobService;

    public ScheduleJobResource(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    /**
     * POST  /schedule-jobs : Create a new scheduleJob.
     *
     * @param scheduleJobDTO the scheduleJobDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduleJobDTO, or with status 400 (Bad Request) if the scheduleJob has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedule-jobs")
    @Timed
    public ResponseEntity<ScheduleJobDTO> createScheduleJob(@RequestBody ScheduleJobDTO scheduleJobDTO) throws URISyntaxException {
        log.debug("REST request to save ScheduleJob : {}", scheduleJobDTO);
        if (scheduleJobDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleJobDTO result = scheduleJobService.save(scheduleJobDTO);
        return ResponseEntity.created(new URI("/api/schedule-jobs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /schedule-jobs : Updates an existing scheduleJob.
     *
     * @param scheduleJobDTO the scheduleJobDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduleJobDTO,
     * or with status 400 (Bad Request) if the scheduleJobDTO is not valid,
     * or with status 500 (Internal Server Error) if the scheduleJobDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedule-jobs")
    @Timed
    public ResponseEntity<ScheduleJobDTO> updateScheduleJob(@RequestBody ScheduleJobDTO scheduleJobDTO) throws URISyntaxException {
        log.debug("REST request to update ScheduleJob : {}", scheduleJobDTO);
        if (scheduleJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleJobDTO result = scheduleJobService.update(scheduleJobDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheduleJobDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /schedule-jobs : get all the scheduleJobs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of scheduleJobs in body
     */
    @GetMapping("/schedule-jobs")
    @Timed
    public ResponseEntity<List<ScheduleJobDTO>> getAllScheduleJobs(Pageable pageable) {
        log.debug("REST request to get a page of ScheduleJobs");
        Page<ScheduleJobDTO> page = scheduleJobService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schedule-jobs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schedule-jobs/:id : get the "id" scheduleJob.
     *
     * @param id the id of the scheduleJobDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduleJobDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schedule-jobs/{id}")
    @Timed
    public ResponseEntity<ScheduleJobDTO> getScheduleJob(@PathVariable Long id) {
        log.debug("REST request to get ScheduleJob : {}", id);
        Optional<ScheduleJobDTO> scheduleJobDTO = scheduleJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleJobDTO);
    }

    /**
     * DELETE  /schedule-jobs/:id : delete the "id" scheduleJob.
     *
     * @param id the id of the scheduleJobDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedule-jobs/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheduleJob(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleJob : {}", id);
        scheduleJobService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * 立即执行
     *
     * @param id
     * @return
     */
    @DeleteMapping("/schedule-jobs/run/{id}")
    @Timed
    public ResponseEntity<Void> runScheduleJob(@PathVariable Long id) {
        log.debug("REST request to run ScheduleJob : {}", id);
        scheduleJobService.run(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("job run", id.toString())).build();
    }

    /**
     * 暂停
     *
     * @param id
     * @return
     */
    @DeleteMapping("/schedule-jobs/pause/{id}")
    @Timed
    public ResponseEntity<Void> pauseScheduleJob(@PathVariable Long id) {
        log.debug("REST request to pause ScheduleJob : {}", id);
        scheduleJobService.pause(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("job pause", id.toString())).build();
    }

    /**
     * 恢复
     *
     * @param id
     * @return
     */
    @DeleteMapping("/schedule-jobs/resume/{id}")
    @Timed
    public ResponseEntity<Void> resumeScheduleJob(@PathVariable Long id) {
        log.debug("REST request to resume ScheduleJob : {}", id);
        scheduleJobService.resume(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("job resume", id.toString())).build();
    }


}
