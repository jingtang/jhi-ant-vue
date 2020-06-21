package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.Leave;
import com.aidriveall.cms.domain.UploadImage;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.repository.LeaveRepository;
import com.aidriveall.cms.service.LeaveService;
import com.aidriveall.cms.service.dto.LeaveDTO;
import com.aidriveall.cms.service.mapper.LeaveMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.LeaveCriteria;
import com.aidriveall.cms.service.LeaveQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.aidriveall.cms.web.rest.TestUtil.sameInstant;
import static com.aidriveall.cms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LeaveResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class LeaveResourceIT {

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAYS = 1;
    private static final Integer UPDATED_DAYS = 2;
    private static final Integer SMALLER_DAYS = 1 - 1;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveQueryService leaveQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLeaveMockMvc;

    private Leave leave;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LeaveResource leaveResource = new LeaveResource(leaveService, leaveQueryService);
        this.restLeaveMockMvc = MockMvcBuilders.standaloneSetup(leaveResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leave createEntity(EntityManager em) {
        Leave leave = new Leave()
            .createTime(DEFAULT_CREATE_TIME)
            .name(DEFAULT_NAME)
            .days(DEFAULT_DAYS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .reason(DEFAULT_REASON);
        return leave;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Leave createUpdatedEntity(EntityManager em) {
        Leave leave = new Leave()
            .createTime(UPDATED_CREATE_TIME)
            .name(UPDATED_NAME)
            .days(UPDATED_DAYS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .reason(UPDATED_REASON);
        return leave;
    }

    @BeforeEach
    public void initTest() {
        leave = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeave() throws Exception {
        int databaseSizeBeforeCreate = leaveRepository.findAll().size();

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);
        restLeaveMockMvc.perform(post("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isCreated());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeCreate + 1);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testLeave.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLeave.getDays()).isEqualTo(DEFAULT_DAYS);
        assertThat(testLeave.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testLeave.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testLeave.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createLeaveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveRepository.findAll().size();

        // Create the Leave with an existing ID
        leave.setId(1L);
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveMockMvc.perform(post("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLeaves() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList
        restLeaveMockMvc.perform(get("/api/leaves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leave.getId().intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }
    
    @Test
    @Transactional
    public void getLeave() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get the leave
        restLeaveMockMvc.perform(get("/api/leaves/{id}", leave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leave.getId().intValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.days").value(DEFAULT_DAYS))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON));
    }


    @Test
    @Transactional
    public void getLeavesByIdFiltering() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        Long id = leave.getId();

        defaultLeaveShouldBeFound("id.equals=" + id);
        defaultLeaveShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime equals to DEFAULT_CREATE_TIME
        defaultLeaveShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the leaveList where createTime equals to UPDATED_CREATE_TIME
        defaultLeaveShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime not equals to DEFAULT_CREATE_TIME
        defaultLeaveShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the leaveList where createTime not equals to UPDATED_CREATE_TIME
        defaultLeaveShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultLeaveShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the leaveList where createTime equals to UPDATED_CREATE_TIME
        defaultLeaveShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime is not null
        defaultLeaveShouldBeFound("createTime.specified=true");

        // Get all the leaveList where createTime is null
        defaultLeaveShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime is greater than or equal to DEFAULT_CREATE_TIME
        defaultLeaveShouldBeFound("createTime.greaterThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the leaveList where createTime is greater than or equal to UPDATED_CREATE_TIME
        defaultLeaveShouldNotBeFound("createTime.greaterThanOrEqual=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime is less than or equal to DEFAULT_CREATE_TIME
        defaultLeaveShouldBeFound("createTime.lessThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the leaveList where createTime is less than or equal to SMALLER_CREATE_TIME
        defaultLeaveShouldNotBeFound("createTime.lessThanOrEqual=" + SMALLER_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime is less than DEFAULT_CREATE_TIME
        defaultLeaveShouldNotBeFound("createTime.lessThan=" + DEFAULT_CREATE_TIME);

        // Get all the leaveList where createTime is less than UPDATED_CREATE_TIME
        defaultLeaveShouldBeFound("createTime.lessThan=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByCreateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where createTime is greater than DEFAULT_CREATE_TIME
        defaultLeaveShouldNotBeFound("createTime.greaterThan=" + DEFAULT_CREATE_TIME);

        // Get all the leaveList where createTime is greater than SMALLER_CREATE_TIME
        defaultLeaveShouldBeFound("createTime.greaterThan=" + SMALLER_CREATE_TIME);
    }


    @Test
    @Transactional
    public void getAllLeavesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where name equals to DEFAULT_NAME
        defaultLeaveShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the leaveList where name equals to UPDATED_NAME
        defaultLeaveShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeavesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where name not equals to DEFAULT_NAME
        defaultLeaveShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the leaveList where name not equals to UPDATED_NAME
        defaultLeaveShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeavesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLeaveShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the leaveList where name equals to UPDATED_NAME
        defaultLeaveShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeavesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where name is not null
        defaultLeaveShouldBeFound("name.specified=true");

        // Get all the leaveList where name is null
        defaultLeaveShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeavesByNameContainsSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where name contains DEFAULT_NAME
        defaultLeaveShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the leaveList where name contains UPDATED_NAME
        defaultLeaveShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLeavesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where name does not contain DEFAULT_NAME
        defaultLeaveShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the leaveList where name does not contain UPDATED_NAME
        defaultLeaveShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllLeavesByDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days equals to DEFAULT_DAYS
        defaultLeaveShouldBeFound("days.equals=" + DEFAULT_DAYS);

        // Get all the leaveList where days equals to UPDATED_DAYS
        defaultLeaveShouldNotBeFound("days.equals=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeavesByDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days not equals to DEFAULT_DAYS
        defaultLeaveShouldNotBeFound("days.notEquals=" + DEFAULT_DAYS);

        // Get all the leaveList where days not equals to UPDATED_DAYS
        defaultLeaveShouldBeFound("days.notEquals=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeavesByDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days in DEFAULT_DAYS or UPDATED_DAYS
        defaultLeaveShouldBeFound("days.in=" + DEFAULT_DAYS + "," + UPDATED_DAYS);

        // Get all the leaveList where days equals to UPDATED_DAYS
        defaultLeaveShouldNotBeFound("days.in=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeavesByDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days is not null
        defaultLeaveShouldBeFound("days.specified=true");

        // Get all the leaveList where days is null
        defaultLeaveShouldNotBeFound("days.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeavesByDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days is greater than or equal to DEFAULT_DAYS
        defaultLeaveShouldBeFound("days.greaterThanOrEqual=" + DEFAULT_DAYS);

        // Get all the leaveList where days is greater than or equal to UPDATED_DAYS
        defaultLeaveShouldNotBeFound("days.greaterThanOrEqual=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeavesByDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days is less than or equal to DEFAULT_DAYS
        defaultLeaveShouldBeFound("days.lessThanOrEqual=" + DEFAULT_DAYS);

        // Get all the leaveList where days is less than or equal to SMALLER_DAYS
        defaultLeaveShouldNotBeFound("days.lessThanOrEqual=" + SMALLER_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeavesByDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days is less than DEFAULT_DAYS
        defaultLeaveShouldNotBeFound("days.lessThan=" + DEFAULT_DAYS);

        // Get all the leaveList where days is less than UPDATED_DAYS
        defaultLeaveShouldBeFound("days.lessThan=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeavesByDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where days is greater than DEFAULT_DAYS
        defaultLeaveShouldNotBeFound("days.greaterThan=" + DEFAULT_DAYS);

        // Get all the leaveList where days is greater than SMALLER_DAYS
        defaultLeaveShouldBeFound("days.greaterThan=" + SMALLER_DAYS);
    }


    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime equals to DEFAULT_START_TIME
        defaultLeaveShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the leaveList where startTime equals to UPDATED_START_TIME
        defaultLeaveShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime not equals to DEFAULT_START_TIME
        defaultLeaveShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the leaveList where startTime not equals to UPDATED_START_TIME
        defaultLeaveShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultLeaveShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the leaveList where startTime equals to UPDATED_START_TIME
        defaultLeaveShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime is not null
        defaultLeaveShouldBeFound("startTime.specified=true");

        // Get all the leaveList where startTime is null
        defaultLeaveShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime is greater than or equal to DEFAULT_START_TIME
        defaultLeaveShouldBeFound("startTime.greaterThanOrEqual=" + DEFAULT_START_TIME);

        // Get all the leaveList where startTime is greater than or equal to UPDATED_START_TIME
        defaultLeaveShouldNotBeFound("startTime.greaterThanOrEqual=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime is less than or equal to DEFAULT_START_TIME
        defaultLeaveShouldBeFound("startTime.lessThanOrEqual=" + DEFAULT_START_TIME);

        // Get all the leaveList where startTime is less than or equal to SMALLER_START_TIME
        defaultLeaveShouldNotBeFound("startTime.lessThanOrEqual=" + SMALLER_START_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime is less than DEFAULT_START_TIME
        defaultLeaveShouldNotBeFound("startTime.lessThan=" + DEFAULT_START_TIME);

        // Get all the leaveList where startTime is less than UPDATED_START_TIME
        defaultLeaveShouldBeFound("startTime.lessThan=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByStartTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where startTime is greater than DEFAULT_START_TIME
        defaultLeaveShouldNotBeFound("startTime.greaterThan=" + DEFAULT_START_TIME);

        // Get all the leaveList where startTime is greater than SMALLER_START_TIME
        defaultLeaveShouldBeFound("startTime.greaterThan=" + SMALLER_START_TIME);
    }


    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime equals to DEFAULT_END_TIME
        defaultLeaveShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the leaveList where endTime equals to UPDATED_END_TIME
        defaultLeaveShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime not equals to DEFAULT_END_TIME
        defaultLeaveShouldNotBeFound("endTime.notEquals=" + DEFAULT_END_TIME);

        // Get all the leaveList where endTime not equals to UPDATED_END_TIME
        defaultLeaveShouldBeFound("endTime.notEquals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultLeaveShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the leaveList where endTime equals to UPDATED_END_TIME
        defaultLeaveShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime is not null
        defaultLeaveShouldBeFound("endTime.specified=true");

        // Get all the leaveList where endTime is null
        defaultLeaveShouldNotBeFound("endTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime is greater than or equal to DEFAULT_END_TIME
        defaultLeaveShouldBeFound("endTime.greaterThanOrEqual=" + DEFAULT_END_TIME);

        // Get all the leaveList where endTime is greater than or equal to UPDATED_END_TIME
        defaultLeaveShouldNotBeFound("endTime.greaterThanOrEqual=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime is less than or equal to DEFAULT_END_TIME
        defaultLeaveShouldBeFound("endTime.lessThanOrEqual=" + DEFAULT_END_TIME);

        // Get all the leaveList where endTime is less than or equal to SMALLER_END_TIME
        defaultLeaveShouldNotBeFound("endTime.lessThanOrEqual=" + SMALLER_END_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime is less than DEFAULT_END_TIME
        defaultLeaveShouldNotBeFound("endTime.lessThan=" + DEFAULT_END_TIME);

        // Get all the leaveList where endTime is less than UPDATED_END_TIME
        defaultLeaveShouldBeFound("endTime.lessThan=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllLeavesByEndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where endTime is greater than DEFAULT_END_TIME
        defaultLeaveShouldNotBeFound("endTime.greaterThan=" + DEFAULT_END_TIME);

        // Get all the leaveList where endTime is greater than SMALLER_END_TIME
        defaultLeaveShouldBeFound("endTime.greaterThan=" + SMALLER_END_TIME);
    }


    @Test
    @Transactional
    public void getAllLeavesByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where reason equals to DEFAULT_REASON
        defaultLeaveShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the leaveList where reason equals to UPDATED_REASON
        defaultLeaveShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeavesByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where reason not equals to DEFAULT_REASON
        defaultLeaveShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the leaveList where reason not equals to UPDATED_REASON
        defaultLeaveShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeavesByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultLeaveShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the leaveList where reason equals to UPDATED_REASON
        defaultLeaveShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeavesByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where reason is not null
        defaultLeaveShouldBeFound("reason.specified=true");

        // Get all the leaveList where reason is null
        defaultLeaveShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeavesByReasonContainsSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where reason contains DEFAULT_REASON
        defaultLeaveShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the leaveList where reason contains UPDATED_REASON
        defaultLeaveShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllLeavesByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        // Get all the leaveList where reason does not contain DEFAULT_REASON
        defaultLeaveShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the leaveList where reason does not contain UPDATED_REASON
        defaultLeaveShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllLeavesByImagesIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);
        UploadImage images = UploadImageResourceIT.createEntity(em);
        em.persist(images);
        em.flush();
        leave.addImages(images);
        leaveRepository.saveAndFlush(leave);
        Long imagesId = images.getId();

        // Get all the leaveList where images equals to imagesId
        defaultLeaveShouldBeFound("imagesId.equals=" + imagesId);

        // Get all the leaveList where images equals to imagesId + 1
        defaultLeaveShouldNotBeFound("imagesId.equals=" + (imagesId + 1));
    }


    @Test
    @Transactional
    public void getAllLeavesByCreatorIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);
        User creator = UserResourceIT.createEntity(em);
        em.persist(creator);
        em.flush();
        leave.setCreator(creator);
        leaveRepository.saveAndFlush(leave);
        Long creatorId = creator.getId();

        // Get all the leaveList where creator equals to creatorId
        defaultLeaveShouldBeFound("creatorId.equals=" + creatorId);

        // Get all the leaveList where creator equals to creatorId + 1
        defaultLeaveShouldNotBeFound("creatorId.equals=" + (creatorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveShouldBeFound(String filter) throws Exception {
        restLeaveMockMvc.perform(get("/api/leaves?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leave.getId().intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));

        // Check, that the count call also returns 1
        restLeaveMockMvc.perform(get("/api/leaves/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveShouldNotBeFound(String filter) throws Exception {
        restLeaveMockMvc.perform(get("/api/leaves?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveMockMvc.perform(get("/api/leaves/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLeave() throws Exception {
        // Get the leave
        restLeaveMockMvc.perform(get("/api/leaves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeave() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();

        // Update the leave
        Leave updatedLeave = leaveRepository.findById(leave.getId()).get();
        // Disconnect from session so that the updates on updatedLeave are not directly saved in db
        em.detach(updatedLeave);
        updatedLeave
            .createTime(UPDATED_CREATE_TIME)
            .name(UPDATED_NAME)
            .days(UPDATED_DAYS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .reason(UPDATED_REASON);
        LeaveDTO leaveDTO = leaveMapper.toDto(updatedLeave);

        restLeaveMockMvc.perform(put("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isOk());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
        Leave testLeave = leaveList.get(leaveList.size() - 1);
        assertThat(testLeave.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testLeave.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLeave.getDays()).isEqualTo(UPDATED_DAYS);
        assertThat(testLeave.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testLeave.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testLeave.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingLeave() throws Exception {
        int databaseSizeBeforeUpdate = leaveRepository.findAll().size();

        // Create the Leave
        LeaveDTO leaveDTO = leaveMapper.toDto(leave);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveMockMvc.perform(put("/api/leaves")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Leave in the database
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeave() throws Exception {
        // Initialize the database
        leaveRepository.saveAndFlush(leave);

        int databaseSizeBeforeDelete = leaveRepository.findAll().size();

        // Delete the leave
        restLeaveMockMvc.perform(delete("/api/leaves/{id}", leave.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Leave> leaveList = leaveRepository.findAll();
        assertThat(leaveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
