package com.liaoyb.saber.modules.job.web;

import com.codahale.metrics.annotation.Timed;
import com.liaoyb.saber.modules.job.service.ScheduleJobHistoryService;
import com.liaoyb.saber.web.errors.BadRequestAlertException;
import com.liaoyb.saber.web.util.HeaderUtil;
import com.liaoyb.saber.web.util.PaginationUtil;
import com.liaoyb.saber.modules.job.dto.ScheduleJobHistoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ScheduleJobHistory.
 */
@RestController
@RequestMapping("/api")
public class ScheduleJobHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleJobHistoryResource.class);

    private static final String ENTITY_NAME = "scheduleJobHistory";

    private final ScheduleJobHistoryService scheduleJobHistoryService;

    public ScheduleJobHistoryResource(ScheduleJobHistoryService scheduleJobHistoryService) {
        this.scheduleJobHistoryService = scheduleJobHistoryService;
    }

    /**
     * POST  /schedule-job-histories : Create a new scheduleJobHistory.
     *
     * @param scheduleJobHistoryDTO the scheduleJobHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scheduleJobHistoryDTO, or with status 400 (Bad Request) if the scheduleJobHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedule-job-histories")
    @Timed
    public ResponseEntity<ScheduleJobHistoryDTO> createScheduleJobHistory(@RequestBody ScheduleJobHistoryDTO scheduleJobHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save ScheduleJobHistory : {}", scheduleJobHistoryDTO);
        if (scheduleJobHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleJobHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleJobHistoryDTO result = scheduleJobHistoryService.save(scheduleJobHistoryDTO);
        return ResponseEntity.created(new URI("/api/schedule-job-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedule-job-histories : Updates an existing scheduleJobHistory.
     *
     * @param scheduleJobHistoryDTO the scheduleJobHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scheduleJobHistoryDTO,
     * or with status 400 (Bad Request) if the scheduleJobHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the scheduleJobHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedule-job-histories")
    @Timed
    public ResponseEntity<ScheduleJobHistoryDTO> updateScheduleJobHistory(@RequestBody ScheduleJobHistoryDTO scheduleJobHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update ScheduleJobHistory : {}", scheduleJobHistoryDTO);
        if (scheduleJobHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleJobHistoryDTO result = scheduleJobHistoryService.save(scheduleJobHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scheduleJobHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedule-job-histories : get all the scheduleJobHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of scheduleJobHistories in body
     */
    @GetMapping("/schedule-job-histories")
    @Timed
    public ResponseEntity<List<ScheduleJobHistoryDTO>> getAllScheduleJobHistories(Pageable pageable) {
        log.debug("REST request to get a page of ScheduleJobHistories");
        Page<ScheduleJobHistoryDTO> page = scheduleJobHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schedule-job-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schedule-job-histories/:id : get the "id" scheduleJobHistory.
     *
     * @param id the id of the scheduleJobHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scheduleJobHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schedule-job-histories/{id}")
    @Timed
    public ResponseEntity<ScheduleJobHistoryDTO> getScheduleJobHistory(@PathVariable Long id) {
        log.debug("REST request to get ScheduleJobHistory : {}", id);
        Optional<ScheduleJobHistoryDTO> scheduleJobHistoryDTO = scheduleJobHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleJobHistoryDTO);
    }

    /**
     * DELETE  /schedule-job-histories/:id : delete the "id" scheduleJobHistory.
     *
     * @param id the id of the scheduleJobHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedule-job-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheduleJobHistory(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleJobHistory : {}", id);
        scheduleJobHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
