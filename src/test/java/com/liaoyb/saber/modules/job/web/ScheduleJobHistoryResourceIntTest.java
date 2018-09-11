package com.liaoyb.saber.modules.job.web;

import com.liaoyb.saber.SaberApp;

import com.liaoyb.saber.modules.job.domain.ScheduleJobHistory;
import com.liaoyb.saber.modules.job.web.ScheduleJobHistoryResource;
import com.liaoyb.saber.modules.job.repository.ScheduleJobHistoryRepository;
import com.liaoyb.saber.modules.job.service.ScheduleJobHistoryService;
import com.liaoyb.saber.modules.job.dto.ScheduleJobHistoryDTO;
import com.liaoyb.saber.modules.job.mapper.ScheduleJobHistoryMapper;
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
 * Test class for the ScheduleJobHistoryResource REST controller.
 *
 * @see ScheduleJobHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaberApp.class)
public class ScheduleJobHistoryResourceIntTest {

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

    private static final String DEFAULT_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_ERROR = "BBBBBBBBBB";

    private static final Long DEFAULT_ELAPSED_TIME = 1L;
    private static final Long UPDATED_ELAPSED_TIME = 2L;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ScheduleJobHistoryRepository scheduleJobHistoryRepository;


    @Autowired
    private ScheduleJobHistoryMapper scheduleJobHistoryMapper;


    @Autowired
    private ScheduleJobHistoryService scheduleJobHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScheduleJobHistoryMockMvc;

    private ScheduleJobHistory scheduleJobHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScheduleJobHistoryResource scheduleJobHistoryResource = new ScheduleJobHistoryResource(scheduleJobHistoryService);
        this.restScheduleJobHistoryMockMvc = MockMvcBuilders.standaloneSetup(scheduleJobHistoryResource)
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
    public static ScheduleJobHistory createEntity(EntityManager em) {
        ScheduleJobHistory scheduleJobHistory = new ScheduleJobHistory()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .beanName(DEFAULT_BEAN_NAME)
            .methodName(DEFAULT_METHOD_NAME)
            .params(DEFAULT_PARAMS)
            .cronExpression(DEFAULT_CRON_EXPRESSION)
            .status(DEFAULT_STATUS)
            .error(DEFAULT_ERROR)
            .elapsedTime(DEFAULT_ELAPSED_TIME)
            .createTime(DEFAULT_CREATE_TIME)
            .endTime(DEFAULT_END_TIME);
        return scheduleJobHistory;
    }

    @Before
    public void initTest() {
        scheduleJobHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleJobHistory() throws Exception {
        int databaseSizeBeforeCreate = scheduleJobHistoryRepository.findAll().size();

        // Create the ScheduleJobHistory
        ScheduleJobHistoryDTO scheduleJobHistoryDTO = scheduleJobHistoryMapper.toDto(scheduleJobHistory);
        restScheduleJobHistoryMockMvc.perform(post("/api/schedule-job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ScheduleJobHistory in the database
        List<ScheduleJobHistory> scheduleJobHistoryList = scheduleJobHistoryRepository.findAll();
        assertThat(scheduleJobHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleJobHistory testScheduleJobHistory = scheduleJobHistoryList.get(scheduleJobHistoryList.size() - 1);
        assertThat(testScheduleJobHistory.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testScheduleJobHistory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testScheduleJobHistory.getBeanName()).isEqualTo(DEFAULT_BEAN_NAME);
        assertThat(testScheduleJobHistory.getMethodName()).isEqualTo(DEFAULT_METHOD_NAME);
        assertThat(testScheduleJobHistory.getParams()).isEqualTo(DEFAULT_PARAMS);
        assertThat(testScheduleJobHistory.getCronExpression()).isEqualTo(DEFAULT_CRON_EXPRESSION);
        assertThat(testScheduleJobHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testScheduleJobHistory.getError()).isEqualTo(DEFAULT_ERROR);
        assertThat(testScheduleJobHistory.getElapsedTime()).isEqualTo(DEFAULT_ELAPSED_TIME);
        assertThat(testScheduleJobHistory.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testScheduleJobHistory.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createScheduleJobHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleJobHistoryRepository.findAll().size();

        // Create the ScheduleJobHistory with an existing ID
        scheduleJobHistory.setId(1L);
        ScheduleJobHistoryDTO scheduleJobHistoryDTO = scheduleJobHistoryMapper.toDto(scheduleJobHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleJobHistoryMockMvc.perform(post("/api/schedule-job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleJobHistory in the database
        List<ScheduleJobHistory> scheduleJobHistoryList = scheduleJobHistoryRepository.findAll();
        assertThat(scheduleJobHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllScheduleJobHistories() throws Exception {
        // Initialize the database
        scheduleJobHistoryRepository.saveAndFlush(scheduleJobHistory);

        // Get all the scheduleJobHistoryList
        restScheduleJobHistoryMockMvc.perform(get("/api/schedule-job-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleJobHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].beanName").value(hasItem(DEFAULT_BEAN_NAME.toString())))
            .andExpect(jsonPath("$.[*].methodName").value(hasItem(DEFAULT_METHOD_NAME.toString())))
            .andExpect(jsonPath("$.[*].params").value(hasItem(DEFAULT_PARAMS.toString())))
            .andExpect(jsonPath("$.[*].cronExpression").value(hasItem(DEFAULT_CRON_EXPRESSION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].error").value(hasItem(DEFAULT_ERROR.toString())))
            .andExpect(jsonPath("$.[*].elapsedTime").value(hasItem(DEFAULT_ELAPSED_TIME.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }


    @Test
    @Transactional
    public void getScheduleJobHistory() throws Exception {
        // Initialize the database
        scheduleJobHistoryRepository.saveAndFlush(scheduleJobHistory);

        // Get the scheduleJobHistory
        restScheduleJobHistoryMockMvc.perform(get("/api/schedule-job-histories/{id}", scheduleJobHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleJobHistory.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.beanName").value(DEFAULT_BEAN_NAME.toString()))
            .andExpect(jsonPath("$.methodName").value(DEFAULT_METHOD_NAME.toString()))
            .andExpect(jsonPath("$.params").value(DEFAULT_PARAMS.toString()))
            .andExpect(jsonPath("$.cronExpression").value(DEFAULT_CRON_EXPRESSION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.error").value(DEFAULT_ERROR.toString()))
            .andExpect(jsonPath("$.elapsedTime").value(DEFAULT_ELAPSED_TIME.intValue()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingScheduleJobHistory() throws Exception {
        // Get the scheduleJobHistory
        restScheduleJobHistoryMockMvc.perform(get("/api/schedule-job-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleJobHistory() throws Exception {
        // Initialize the database
        scheduleJobHistoryRepository.saveAndFlush(scheduleJobHistory);

        int databaseSizeBeforeUpdate = scheduleJobHistoryRepository.findAll().size();

        // Update the scheduleJobHistory
        ScheduleJobHistory updatedScheduleJobHistory = scheduleJobHistoryRepository.findById(scheduleJobHistory.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleJobHistory are not directly saved in db
        em.detach(updatedScheduleJobHistory);
        updatedScheduleJobHistory
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME)
            .methodName(UPDATED_METHOD_NAME)
            .params(UPDATED_PARAMS)
            .cronExpression(UPDATED_CRON_EXPRESSION)
            .status(UPDATED_STATUS)
            .error(UPDATED_ERROR)
            .elapsedTime(UPDATED_ELAPSED_TIME)
            .createTime(UPDATED_CREATE_TIME)
            .endTime(UPDATED_END_TIME);
        ScheduleJobHistoryDTO scheduleJobHistoryDTO = scheduleJobHistoryMapper.toDto(updatedScheduleJobHistory);

        restScheduleJobHistoryMockMvc.perform(put("/api/schedule-job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the ScheduleJobHistory in the database
        List<ScheduleJobHistory> scheduleJobHistoryList = scheduleJobHistoryRepository.findAll();
        assertThat(scheduleJobHistoryList).hasSize(databaseSizeBeforeUpdate);
        ScheduleJobHistory testScheduleJobHistory = scheduleJobHistoryList.get(scheduleJobHistoryList.size() - 1);
        assertThat(testScheduleJobHistory.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testScheduleJobHistory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScheduleJobHistory.getBeanName()).isEqualTo(UPDATED_BEAN_NAME);
        assertThat(testScheduleJobHistory.getMethodName()).isEqualTo(UPDATED_METHOD_NAME);
        assertThat(testScheduleJobHistory.getParams()).isEqualTo(UPDATED_PARAMS);
        assertThat(testScheduleJobHistory.getCronExpression()).isEqualTo(UPDATED_CRON_EXPRESSION);
        assertThat(testScheduleJobHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testScheduleJobHistory.getError()).isEqualTo(UPDATED_ERROR);
        assertThat(testScheduleJobHistory.getElapsedTime()).isEqualTo(UPDATED_ELAPSED_TIME);
        assertThat(testScheduleJobHistory.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testScheduleJobHistory.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleJobHistory() throws Exception {
        int databaseSizeBeforeUpdate = scheduleJobHistoryRepository.findAll().size();

        // Create the ScheduleJobHistory
        ScheduleJobHistoryDTO scheduleJobHistoryDTO = scheduleJobHistoryMapper.toDto(scheduleJobHistory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScheduleJobHistoryMockMvc.perform(put("/api/schedule-job-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scheduleJobHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleJobHistory in the database
        List<ScheduleJobHistory> scheduleJobHistoryList = scheduleJobHistoryRepository.findAll();
        assertThat(scheduleJobHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduleJobHistory() throws Exception {
        // Initialize the database
        scheduleJobHistoryRepository.saveAndFlush(scheduleJobHistory);

        int databaseSizeBeforeDelete = scheduleJobHistoryRepository.findAll().size();

        // Get the scheduleJobHistory
        restScheduleJobHistoryMockMvc.perform(delete("/api/schedule-job-histories/{id}", scheduleJobHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ScheduleJobHistory> scheduleJobHistoryList = scheduleJobHistoryRepository.findAll();
        assertThat(scheduleJobHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleJobHistory.class);
        ScheduleJobHistory scheduleJobHistory1 = new ScheduleJobHistory();
        scheduleJobHistory1.setId(1L);
        ScheduleJobHistory scheduleJobHistory2 = new ScheduleJobHistory();
        scheduleJobHistory2.setId(scheduleJobHistory1.getId());
        assertThat(scheduleJobHistory1).isEqualTo(scheduleJobHistory2);
        scheduleJobHistory2.setId(2L);
        assertThat(scheduleJobHistory1).isNotEqualTo(scheduleJobHistory2);
        scheduleJobHistory1.setId(null);
        assertThat(scheduleJobHistory1).isNotEqualTo(scheduleJobHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleJobHistoryDTO.class);
        ScheduleJobHistoryDTO scheduleJobHistoryDTO1 = new ScheduleJobHistoryDTO();
        scheduleJobHistoryDTO1.setId(1L);
        ScheduleJobHistoryDTO scheduleJobHistoryDTO2 = new ScheduleJobHistoryDTO();
        assertThat(scheduleJobHistoryDTO1).isNotEqualTo(scheduleJobHistoryDTO2);
        scheduleJobHistoryDTO2.setId(scheduleJobHistoryDTO1.getId());
        assertThat(scheduleJobHistoryDTO1).isEqualTo(scheduleJobHistoryDTO2);
        scheduleJobHistoryDTO2.setId(2L);
        assertThat(scheduleJobHistoryDTO1).isNotEqualTo(scheduleJobHistoryDTO2);
        scheduleJobHistoryDTO1.setId(null);
        assertThat(scheduleJobHistoryDTO1).isNotEqualTo(scheduleJobHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scheduleJobHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scheduleJobHistoryMapper.fromId(null)).isNull();
    }
}
