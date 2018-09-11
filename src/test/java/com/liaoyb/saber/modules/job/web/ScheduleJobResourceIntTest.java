package com.liaoyb.saber.modules.job.web;

import com.liaoyb.saber.SaberApp;

import com.liaoyb.saber.modules.job.domain.ScheduleJob;
import com.liaoyb.saber.modules.job.web.ScheduleJobResource;
import com.liaoyb.saber.modules.job.repository.ScheduleJobRepository;
import com.liaoyb.saber.modules.job.service.ScheduleJobService;
import com.liaoyb.saber.modules.job.dto.ScheduleJobDTO;
import com.liaoyb.saber.modules.job.mapper.ScheduleJobMapper;
import com.liaoyb.saber.modules.sys.web.TestUtil;
import com.liaoyb.saber.web.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.liaoyb.saber.modules.sys.web.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ScheduleJobResource REST controller.
 *
 * @see ScheduleJobResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaberApp.class)
public class ScheduleJobResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BEAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BEAN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METHOD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_CRON_EXPRESSION = "AAAAAAAAAA";
    private static final String UPDATED_CRON_EXPRESSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ScheduleJobRepository scheduleJobRepository;


    @Autowired
    private ScheduleJobMapper scheduleJobMapper;


    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScheduleJobMockMvc;

    private ScheduleJob scheduleJob;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScheduleJobResource scheduleJobResource = new ScheduleJobResource(scheduleJobService);
        this.restScheduleJobMockMvc = MockMvcBuilders.standaloneSetup(scheduleJobResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleJob createEntity(EntityManager em) {
        ScheduleJob scheduleJob = new ScheduleJob()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .beanName(DEFAULT_BEAN_NAME)
            .methodName(DEFAULT_METHOD_NAME)
            .params(DEFAULT_PARAMS)
            .cronExpression(DEFAULT_CRON_EXPRESSION)
            .status(DEFAULT_STATUS)
            .remark(DEFAULT_REMARK)
            .createTime(DEFAULT_CREATE_TIME);
        return scheduleJob;
    }

    @Before
    public void initTest() {
        scheduleJob = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleJob() throws Exception {
        int databaseSizeBeforeCreate = scheduleJobRepository.findAll().size();

        // Create the ScheduleJob
        ScheduleJobDTO scheduleJobDTO = scheduleJobMapper.toDto(scheduleJob);
        restScheduleJobMockMvc.perform(post("/api/schedule-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobDTO)))
            .andExpect(status().isCreated());

        // Validate the ScheduleJob in the database
        List<ScheduleJob> scheduleJobList = scheduleJobRepository.findAll();
        assertThat(scheduleJobList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleJob testScheduleJob = scheduleJobList.get(scheduleJobList.size() - 1);
        assertThat(testScheduleJob.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testScheduleJob.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testScheduleJob.getBeanName()).isEqualTo(DEFAULT_BEAN_NAME);
        assertThat(testScheduleJob.getMethodName()).isEqualTo(DEFAULT_METHOD_NAME);
        assertThat(testScheduleJob.getParams()).isEqualTo(DEFAULT_PARAMS);
        assertThat(testScheduleJob.getCronExpression()).isEqualTo(DEFAULT_CRON_EXPRESSION);
        assertThat(testScheduleJob.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testScheduleJob.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testScheduleJob.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createScheduleJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleJobRepository.findAll().size();

        // Create the ScheduleJob with an existing ID
        scheduleJob.setId(1L);
        ScheduleJobDTO scheduleJobDTO = scheduleJobMapper.toDto(scheduleJob);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleJobMockMvc.perform(post("/api/schedule-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleJob in the database
        List<ScheduleJob> scheduleJobList = scheduleJobRepository.findAll();
        assertThat(scheduleJobList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllScheduleJobs() throws Exception {
        // Initialize the database
        scheduleJobRepository.saveAndFlush(scheduleJob);

        // Get all the scheduleJobList
        restScheduleJobMockMvc.perform(get("/api/schedule-jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].beanName").value(hasItem(DEFAULT_BEAN_NAME.toString())))
            .andExpect(jsonPath("$.[*].methodName").value(hasItem(DEFAULT_METHOD_NAME.toString())))
            .andExpect(jsonPath("$.[*].params").value(hasItem(DEFAULT_PARAMS.toString())))
            .andExpect(jsonPath("$.[*].cronExpression").value(hasItem(DEFAULT_CRON_EXPRESSION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }


    @Test
    @Transactional
    public void getScheduleJob() throws Exception {
        // Initialize the database
        scheduleJobRepository.saveAndFlush(scheduleJob);

        // Get the scheduleJob
        restScheduleJobMockMvc.perform(get("/api/schedule-jobs/{id}", scheduleJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleJob.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.beanName").value(DEFAULT_BEAN_NAME.toString()))
            .andExpect(jsonPath("$.methodName").value(DEFAULT_METHOD_NAME.toString()))
            .andExpect(jsonPath("$.params").value(DEFAULT_PARAMS.toString()))
            .andExpect(jsonPath("$.cronExpression").value(DEFAULT_CRON_EXPRESSION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingScheduleJob() throws Exception {
        // Get the scheduleJob
        restScheduleJobMockMvc.perform(get("/api/schedule-jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleJob() throws Exception {
        // Initialize the database
        scheduleJobRepository.saveAndFlush(scheduleJob);

        int databaseSizeBeforeUpdate = scheduleJobRepository.findAll().size();

        // Update the scheduleJob
        ScheduleJob updatedScheduleJob = scheduleJobRepository.findById(scheduleJob.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleJob are not directly saved in db
        em.detach(updatedScheduleJob);
        updatedScheduleJob
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME)
            .methodName(UPDATED_METHOD_NAME)
            .params(UPDATED_PARAMS)
            .cronExpression(UPDATED_CRON_EXPRESSION)
            .status(UPDATED_STATUS)
            .remark(UPDATED_REMARK)
            .createTime(UPDATED_CREATE_TIME);
        ScheduleJobDTO scheduleJobDTO = scheduleJobMapper.toDto(updatedScheduleJob);

        restScheduleJobMockMvc.perform(put("/api/schedule-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobDTO)))
            .andExpect(status().isOk());

        // Validate the ScheduleJob in the database
        List<ScheduleJob> scheduleJobList = scheduleJobRepository.findAll();
        assertThat(scheduleJobList).hasSize(databaseSizeBeforeUpdate);
        ScheduleJob testScheduleJob = scheduleJobList.get(scheduleJobList.size() - 1);
        assertThat(testScheduleJob.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testScheduleJob.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScheduleJob.getBeanName()).isEqualTo(UPDATED_BEAN_NAME);
        assertThat(testScheduleJob.getMethodName()).isEqualTo(UPDATED_METHOD_NAME);
        assertThat(testScheduleJob.getParams()).isEqualTo(UPDATED_PARAMS);
        assertThat(testScheduleJob.getCronExpression()).isEqualTo(UPDATED_CRON_EXPRESSION);
        assertThat(testScheduleJob.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testScheduleJob.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testScheduleJob.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleJob() throws Exception {
        int databaseSizeBeforeUpdate = scheduleJobRepository.findAll().size();

        // Create the ScheduleJob
        ScheduleJobDTO scheduleJobDTO = scheduleJobMapper.toDto(scheduleJob);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScheduleJobMockMvc.perform(put("/api/schedule-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleJob in the database
        List<ScheduleJob> scheduleJobList = scheduleJobRepository.findAll();
        assertThat(scheduleJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduleJob() throws Exception {
        // Initialize the database
        scheduleJobRepository.saveAndFlush(scheduleJob);

        int databaseSizeBeforeDelete = scheduleJobRepository.findAll().size();

        // Get the scheduleJob
        restScheduleJobMockMvc.perform(delete("/api/schedule-jobs/{id}", scheduleJob.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ScheduleJob> scheduleJobList = scheduleJobRepository.findAll();
        assertThat(scheduleJobList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleJob.class);
        ScheduleJob scheduleJob1 = new ScheduleJob();
        scheduleJob1.setId(1L);
        ScheduleJob scheduleJob2 = new ScheduleJob();
        scheduleJob2.setId(scheduleJob1.getId());
        assertThat(scheduleJob1).isEqualTo(scheduleJob2);
        scheduleJob2.setId(2L);
        assertThat(scheduleJob1).isNotEqualTo(scheduleJob2);
        scheduleJob1.setId(null);
        assertThat(scheduleJob1).isNotEqualTo(scheduleJob2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleJobDTO.class);
        ScheduleJobDTO scheduleJobDTO1 = new ScheduleJobDTO();
        scheduleJobDTO1.setId(1L);
        ScheduleJobDTO scheduleJobDTO2 = new ScheduleJobDTO();
        assertThat(scheduleJobDTO1).isNotEqualTo(scheduleJobDTO2);
        scheduleJobDTO2.setId(scheduleJobDTO1.getId());
        assertThat(scheduleJobDTO1).isEqualTo(scheduleJobDTO2);
        scheduleJobDTO2.setId(2L);
        assertThat(scheduleJobDTO1).isNotEqualTo(scheduleJobDTO2);
        scheduleJobDTO1.setId(null);
        assertThat(scheduleJobDTO1).isNotEqualTo(scheduleJobDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scheduleJobMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scheduleJobMapper.fromId(null)).isNull();
    }
}
