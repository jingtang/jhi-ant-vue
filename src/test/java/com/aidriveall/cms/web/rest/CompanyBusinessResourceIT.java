package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.CompanyBusiness;
import com.aidriveall.cms.domain.BusinessType;
import com.aidriveall.cms.domain.CompanyCustomer;
import com.aidriveall.cms.repository.CompanyBusinessRepository;
import com.aidriveall.cms.service.CompanyBusinessService;
import com.aidriveall.cms.service.dto.CompanyBusinessDTO;
import com.aidriveall.cms.service.mapper.CompanyBusinessMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.CompanyBusinessCriteria;
import com.aidriveall.cms.service.CompanyBusinessQueryService;

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

import com.aidriveall.cms.domain.enumeration.CompanyBusinessStatus;
/**
 * Integration tests for the {@link CompanyBusinessResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class CompanyBusinessResourceIT {

    private static final CompanyBusinessStatus DEFAULT_STATUS = CompanyBusinessStatus.OPEN;
    private static final CompanyBusinessStatus UPDATED_STATUS = CompanyBusinessStatus.TRIAL;

    private static final ZonedDateTime DEFAULT_EXPIRATION_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRATION_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EXPIRATION_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_OPERATE_USER_ID = 1L;
    private static final Long UPDATED_OPERATE_USER_ID = 2L;
    private static final Long SMALLER_OPERATE_USER_ID = 1L - 1L;

    private static final String DEFAULT_GROUP_ID = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_ID = "BBBBBBBBBB";

    @Autowired
    private CompanyBusinessRepository companyBusinessRepository;

    @Autowired
    private CompanyBusinessMapper companyBusinessMapper;

    @Autowired
    private CompanyBusinessService companyBusinessService;

    @Autowired
    private CompanyBusinessQueryService companyBusinessQueryService;

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

    private MockMvc restCompanyBusinessMockMvc;

    private CompanyBusiness companyBusiness;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyBusinessResource companyBusinessResource = new CompanyBusinessResource(companyBusinessService, companyBusinessQueryService);
        this.restCompanyBusinessMockMvc = MockMvcBuilders.standaloneSetup(companyBusinessResource)
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
    public static CompanyBusiness createEntity(EntityManager em) {
        CompanyBusiness companyBusiness = new CompanyBusiness()
            .status(DEFAULT_STATUS)
            .expirationTime(DEFAULT_EXPIRATION_TIME)
            .startTime(DEFAULT_START_TIME)
            .operateUserId(DEFAULT_OPERATE_USER_ID)
            .groupId(DEFAULT_GROUP_ID);
        return companyBusiness;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyBusiness createUpdatedEntity(EntityManager em) {
        CompanyBusiness companyBusiness = new CompanyBusiness()
            .status(UPDATED_STATUS)
            .expirationTime(UPDATED_EXPIRATION_TIME)
            .startTime(UPDATED_START_TIME)
            .operateUserId(UPDATED_OPERATE_USER_ID)
            .groupId(UPDATED_GROUP_ID);
        return companyBusiness;
    }

    @BeforeEach
    public void initTest() {
        companyBusiness = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyBusiness() throws Exception {
        int databaseSizeBeforeCreate = companyBusinessRepository.findAll().size();

        // Create the CompanyBusiness
        CompanyBusinessDTO companyBusinessDTO = companyBusinessMapper.toDto(companyBusiness);
        restCompanyBusinessMockMvc.perform(post("/api/company-businesses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBusinessDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyBusiness in the database
        List<CompanyBusiness> companyBusinessList = companyBusinessRepository.findAll();
        assertThat(companyBusinessList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyBusiness testCompanyBusiness = companyBusinessList.get(companyBusinessList.size() - 1);
        assertThat(testCompanyBusiness.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompanyBusiness.getExpirationTime()).isEqualTo(DEFAULT_EXPIRATION_TIME);
        assertThat(testCompanyBusiness.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCompanyBusiness.getOperateUserId()).isEqualTo(DEFAULT_OPERATE_USER_ID);
        assertThat(testCompanyBusiness.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
    }

    @Test
    @Transactional
    public void createCompanyBusinessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyBusinessRepository.findAll().size();

        // Create the CompanyBusiness with an existing ID
        companyBusiness.setId(1L);
        CompanyBusinessDTO companyBusinessDTO = companyBusinessMapper.toDto(companyBusiness);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyBusinessMockMvc.perform(post("/api/company-businesses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBusinessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBusiness in the database
        List<CompanyBusiness> companyBusinessList = companyBusinessRepository.findAll();
        assertThat(companyBusinessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyBusinesses() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList
        restCompanyBusinessMockMvc.perform(get("/api/company-businesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyBusiness.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].expirationTime").value(hasItem(sameInstant(DEFAULT_EXPIRATION_TIME))))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].operateUserId").value(hasItem(DEFAULT_OPERATE_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID)));
    }
    
    @Test
    @Transactional
    public void getCompanyBusiness() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get the companyBusiness
        restCompanyBusinessMockMvc.perform(get("/api/company-businesses/{id}", companyBusiness.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyBusiness.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.expirationTime").value(sameInstant(DEFAULT_EXPIRATION_TIME)))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.operateUserId").value(DEFAULT_OPERATE_USER_ID.intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID));
    }


    @Test
    @Transactional
    public void getCompanyBusinessesByIdFiltering() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        Long id = companyBusiness.getId();

        defaultCompanyBusinessShouldBeFound("id.equals=" + id);
        defaultCompanyBusinessShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyBusinessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyBusinessShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyBusinessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyBusinessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompanyBusinessesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where status equals to DEFAULT_STATUS
        defaultCompanyBusinessShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the companyBusinessList where status equals to UPDATED_STATUS
        defaultCompanyBusinessShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where status not equals to DEFAULT_STATUS
        defaultCompanyBusinessShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the companyBusinessList where status not equals to UPDATED_STATUS
        defaultCompanyBusinessShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCompanyBusinessShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the companyBusinessList where status equals to UPDATED_STATUS
        defaultCompanyBusinessShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where status is not null
        defaultCompanyBusinessShouldBeFound("status.specified=true");

        // Get all the companyBusinessList where status is null
        defaultCompanyBusinessShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime equals to DEFAULT_EXPIRATION_TIME
        defaultCompanyBusinessShouldBeFound("expirationTime.equals=" + DEFAULT_EXPIRATION_TIME);

        // Get all the companyBusinessList where expirationTime equals to UPDATED_EXPIRATION_TIME
        defaultCompanyBusinessShouldNotBeFound("expirationTime.equals=" + UPDATED_EXPIRATION_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime not equals to DEFAULT_EXPIRATION_TIME
        defaultCompanyBusinessShouldNotBeFound("expirationTime.notEquals=" + DEFAULT_EXPIRATION_TIME);

        // Get all the companyBusinessList where expirationTime not equals to UPDATED_EXPIRATION_TIME
        defaultCompanyBusinessShouldBeFound("expirationTime.notEquals=" + UPDATED_EXPIRATION_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsInShouldWork() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime in DEFAULT_EXPIRATION_TIME or UPDATED_EXPIRATION_TIME
        defaultCompanyBusinessShouldBeFound("expirationTime.in=" + DEFAULT_EXPIRATION_TIME + "," + UPDATED_EXPIRATION_TIME);

        // Get all the companyBusinessList where expirationTime equals to UPDATED_EXPIRATION_TIME
        defaultCompanyBusinessShouldNotBeFound("expirationTime.in=" + UPDATED_EXPIRATION_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime is not null
        defaultCompanyBusinessShouldBeFound("expirationTime.specified=true");

        // Get all the companyBusinessList where expirationTime is null
        defaultCompanyBusinessShouldNotBeFound("expirationTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime is greater than or equal to DEFAULT_EXPIRATION_TIME
        defaultCompanyBusinessShouldBeFound("expirationTime.greaterThanOrEqual=" + DEFAULT_EXPIRATION_TIME);

        // Get all the companyBusinessList where expirationTime is greater than or equal to UPDATED_EXPIRATION_TIME
        defaultCompanyBusinessShouldNotBeFound("expirationTime.greaterThanOrEqual=" + UPDATED_EXPIRATION_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime is less than or equal to DEFAULT_EXPIRATION_TIME
        defaultCompanyBusinessShouldBeFound("expirationTime.lessThanOrEqual=" + DEFAULT_EXPIRATION_TIME);

        // Get all the companyBusinessList where expirationTime is less than or equal to SMALLER_EXPIRATION_TIME
        defaultCompanyBusinessShouldNotBeFound("expirationTime.lessThanOrEqual=" + SMALLER_EXPIRATION_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime is less than DEFAULT_EXPIRATION_TIME
        defaultCompanyBusinessShouldNotBeFound("expirationTime.lessThan=" + DEFAULT_EXPIRATION_TIME);

        // Get all the companyBusinessList where expirationTime is less than UPDATED_EXPIRATION_TIME
        defaultCompanyBusinessShouldBeFound("expirationTime.lessThan=" + UPDATED_EXPIRATION_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByExpirationTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where expirationTime is greater than DEFAULT_EXPIRATION_TIME
        defaultCompanyBusinessShouldNotBeFound("expirationTime.greaterThan=" + DEFAULT_EXPIRATION_TIME);

        // Get all the companyBusinessList where expirationTime is greater than SMALLER_EXPIRATION_TIME
        defaultCompanyBusinessShouldBeFound("expirationTime.greaterThan=" + SMALLER_EXPIRATION_TIME);
    }


    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime equals to DEFAULT_START_TIME
        defaultCompanyBusinessShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the companyBusinessList where startTime equals to UPDATED_START_TIME
        defaultCompanyBusinessShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime not equals to DEFAULT_START_TIME
        defaultCompanyBusinessShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the companyBusinessList where startTime not equals to UPDATED_START_TIME
        defaultCompanyBusinessShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultCompanyBusinessShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the companyBusinessList where startTime equals to UPDATED_START_TIME
        defaultCompanyBusinessShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime is not null
        defaultCompanyBusinessShouldBeFound("startTime.specified=true");

        // Get all the companyBusinessList where startTime is null
        defaultCompanyBusinessShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime is greater than or equal to DEFAULT_START_TIME
        defaultCompanyBusinessShouldBeFound("startTime.greaterThanOrEqual=" + DEFAULT_START_TIME);

        // Get all the companyBusinessList where startTime is greater than or equal to UPDATED_START_TIME
        defaultCompanyBusinessShouldNotBeFound("startTime.greaterThanOrEqual=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime is less than or equal to DEFAULT_START_TIME
        defaultCompanyBusinessShouldBeFound("startTime.lessThanOrEqual=" + DEFAULT_START_TIME);

        // Get all the companyBusinessList where startTime is less than or equal to SMALLER_START_TIME
        defaultCompanyBusinessShouldNotBeFound("startTime.lessThanOrEqual=" + SMALLER_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime is less than DEFAULT_START_TIME
        defaultCompanyBusinessShouldNotBeFound("startTime.lessThan=" + DEFAULT_START_TIME);

        // Get all the companyBusinessList where startTime is less than UPDATED_START_TIME
        defaultCompanyBusinessShouldBeFound("startTime.lessThan=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByStartTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where startTime is greater than DEFAULT_START_TIME
        defaultCompanyBusinessShouldNotBeFound("startTime.greaterThan=" + DEFAULT_START_TIME);

        // Get all the companyBusinessList where startTime is greater than SMALLER_START_TIME
        defaultCompanyBusinessShouldBeFound("startTime.greaterThan=" + SMALLER_START_TIME);
    }


    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId equals to DEFAULT_OPERATE_USER_ID
        defaultCompanyBusinessShouldBeFound("operateUserId.equals=" + DEFAULT_OPERATE_USER_ID);

        // Get all the companyBusinessList where operateUserId equals to UPDATED_OPERATE_USER_ID
        defaultCompanyBusinessShouldNotBeFound("operateUserId.equals=" + UPDATED_OPERATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId not equals to DEFAULT_OPERATE_USER_ID
        defaultCompanyBusinessShouldNotBeFound("operateUserId.notEquals=" + DEFAULT_OPERATE_USER_ID);

        // Get all the companyBusinessList where operateUserId not equals to UPDATED_OPERATE_USER_ID
        defaultCompanyBusinessShouldBeFound("operateUserId.notEquals=" + UPDATED_OPERATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId in DEFAULT_OPERATE_USER_ID or UPDATED_OPERATE_USER_ID
        defaultCompanyBusinessShouldBeFound("operateUserId.in=" + DEFAULT_OPERATE_USER_ID + "," + UPDATED_OPERATE_USER_ID);

        // Get all the companyBusinessList where operateUserId equals to UPDATED_OPERATE_USER_ID
        defaultCompanyBusinessShouldNotBeFound("operateUserId.in=" + UPDATED_OPERATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId is not null
        defaultCompanyBusinessShouldBeFound("operateUserId.specified=true");

        // Get all the companyBusinessList where operateUserId is null
        defaultCompanyBusinessShouldNotBeFound("operateUserId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId is greater than or equal to DEFAULT_OPERATE_USER_ID
        defaultCompanyBusinessShouldBeFound("operateUserId.greaterThanOrEqual=" + DEFAULT_OPERATE_USER_ID);

        // Get all the companyBusinessList where operateUserId is greater than or equal to UPDATED_OPERATE_USER_ID
        defaultCompanyBusinessShouldNotBeFound("operateUserId.greaterThanOrEqual=" + UPDATED_OPERATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId is less than or equal to DEFAULT_OPERATE_USER_ID
        defaultCompanyBusinessShouldBeFound("operateUserId.lessThanOrEqual=" + DEFAULT_OPERATE_USER_ID);

        // Get all the companyBusinessList where operateUserId is less than or equal to SMALLER_OPERATE_USER_ID
        defaultCompanyBusinessShouldNotBeFound("operateUserId.lessThanOrEqual=" + SMALLER_OPERATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId is less than DEFAULT_OPERATE_USER_ID
        defaultCompanyBusinessShouldNotBeFound("operateUserId.lessThan=" + DEFAULT_OPERATE_USER_ID);

        // Get all the companyBusinessList where operateUserId is less than UPDATED_OPERATE_USER_ID
        defaultCompanyBusinessShouldBeFound("operateUserId.lessThan=" + UPDATED_OPERATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByOperateUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where operateUserId is greater than DEFAULT_OPERATE_USER_ID
        defaultCompanyBusinessShouldNotBeFound("operateUserId.greaterThan=" + DEFAULT_OPERATE_USER_ID);

        // Get all the companyBusinessList where operateUserId is greater than SMALLER_OPERATE_USER_ID
        defaultCompanyBusinessShouldBeFound("operateUserId.greaterThan=" + SMALLER_OPERATE_USER_ID);
    }


    @Test
    @Transactional
    public void getAllCompanyBusinessesByGroupIdIsEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where groupId equals to DEFAULT_GROUP_ID
        defaultCompanyBusinessShouldBeFound("groupId.equals=" + DEFAULT_GROUP_ID);

        // Get all the companyBusinessList where groupId equals to UPDATED_GROUP_ID
        defaultCompanyBusinessShouldNotBeFound("groupId.equals=" + UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByGroupIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where groupId not equals to DEFAULT_GROUP_ID
        defaultCompanyBusinessShouldNotBeFound("groupId.notEquals=" + DEFAULT_GROUP_ID);

        // Get all the companyBusinessList where groupId not equals to UPDATED_GROUP_ID
        defaultCompanyBusinessShouldBeFound("groupId.notEquals=" + UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByGroupIdIsInShouldWork() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where groupId in DEFAULT_GROUP_ID or UPDATED_GROUP_ID
        defaultCompanyBusinessShouldBeFound("groupId.in=" + DEFAULT_GROUP_ID + "," + UPDATED_GROUP_ID);

        // Get all the companyBusinessList where groupId equals to UPDATED_GROUP_ID
        defaultCompanyBusinessShouldNotBeFound("groupId.in=" + UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByGroupIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where groupId is not null
        defaultCompanyBusinessShouldBeFound("groupId.specified=true");

        // Get all the companyBusinessList where groupId is null
        defaultCompanyBusinessShouldNotBeFound("groupId.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyBusinessesByGroupIdContainsSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where groupId contains DEFAULT_GROUP_ID
        defaultCompanyBusinessShouldBeFound("groupId.contains=" + DEFAULT_GROUP_ID);

        // Get all the companyBusinessList where groupId contains UPDATED_GROUP_ID
        defaultCompanyBusinessShouldNotBeFound("groupId.contains=" + UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyBusinessesByGroupIdNotContainsSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        // Get all the companyBusinessList where groupId does not contain DEFAULT_GROUP_ID
        defaultCompanyBusinessShouldNotBeFound("groupId.doesNotContain=" + DEFAULT_GROUP_ID);

        // Get all the companyBusinessList where groupId does not contain UPDATED_GROUP_ID
        defaultCompanyBusinessShouldBeFound("groupId.doesNotContain=" + UPDATED_GROUP_ID);
    }


    @Test
    @Transactional
    public void getAllCompanyBusinessesByBusinessTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);
        BusinessType businessType = BusinessTypeResourceIT.createEntity(em);
        em.persist(businessType);
        em.flush();
        companyBusiness.setBusinessType(businessType);
        companyBusinessRepository.saveAndFlush(companyBusiness);
        Long businessTypeId = businessType.getId();

        // Get all the companyBusinessList where businessType equals to businessTypeId
        defaultCompanyBusinessShouldBeFound("businessTypeId.equals=" + businessTypeId);

        // Get all the companyBusinessList where businessType equals to businessTypeId + 1
        defaultCompanyBusinessShouldNotBeFound("businessTypeId.equals=" + (businessTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyBusinessesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);
        CompanyCustomer company = CompanyCustomerResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        companyBusiness.setCompany(company);
        companyBusinessRepository.saveAndFlush(companyBusiness);
        Long companyId = company.getId();

        // Get all the companyBusinessList where company equals to companyId
        defaultCompanyBusinessShouldBeFound("companyId.equals=" + companyId);

        // Get all the companyBusinessList where company equals to companyId + 1
        defaultCompanyBusinessShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyBusinessShouldBeFound(String filter) throws Exception {
        restCompanyBusinessMockMvc.perform(get("/api/company-businesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyBusiness.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].expirationTime").value(hasItem(sameInstant(DEFAULT_EXPIRATION_TIME))))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].operateUserId").value(hasItem(DEFAULT_OPERATE_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID)));

        // Check, that the count call also returns 1
        restCompanyBusinessMockMvc.perform(get("/api/company-businesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyBusinessShouldNotBeFound(String filter) throws Exception {
        restCompanyBusinessMockMvc.perform(get("/api/company-businesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyBusinessMockMvc.perform(get("/api/company-businesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompanyBusiness() throws Exception {
        // Get the companyBusiness
        restCompanyBusinessMockMvc.perform(get("/api/company-businesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyBusiness() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        int databaseSizeBeforeUpdate = companyBusinessRepository.findAll().size();

        // Update the companyBusiness
        CompanyBusiness updatedCompanyBusiness = companyBusinessRepository.findById(companyBusiness.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyBusiness are not directly saved in db
        em.detach(updatedCompanyBusiness);
        updatedCompanyBusiness
            .status(UPDATED_STATUS)
            .expirationTime(UPDATED_EXPIRATION_TIME)
            .startTime(UPDATED_START_TIME)
            .operateUserId(UPDATED_OPERATE_USER_ID)
            .groupId(UPDATED_GROUP_ID);
        CompanyBusinessDTO companyBusinessDTO = companyBusinessMapper.toDto(updatedCompanyBusiness);

        restCompanyBusinessMockMvc.perform(put("/api/company-businesses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBusinessDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyBusiness in the database
        List<CompanyBusiness> companyBusinessList = companyBusinessRepository.findAll();
        assertThat(companyBusinessList).hasSize(databaseSizeBeforeUpdate);
        CompanyBusiness testCompanyBusiness = companyBusinessList.get(companyBusinessList.size() - 1);
        assertThat(testCompanyBusiness.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompanyBusiness.getExpirationTime()).isEqualTo(UPDATED_EXPIRATION_TIME);
        assertThat(testCompanyBusiness.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCompanyBusiness.getOperateUserId()).isEqualTo(UPDATED_OPERATE_USER_ID);
        assertThat(testCompanyBusiness.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyBusiness() throws Exception {
        int databaseSizeBeforeUpdate = companyBusinessRepository.findAll().size();

        // Create the CompanyBusiness
        CompanyBusinessDTO companyBusinessDTO = companyBusinessMapper.toDto(companyBusiness);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyBusinessMockMvc.perform(put("/api/company-businesses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyBusinessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBusiness in the database
        List<CompanyBusiness> companyBusinessList = companyBusinessRepository.findAll();
        assertThat(companyBusinessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyBusiness() throws Exception {
        // Initialize the database
        companyBusinessRepository.saveAndFlush(companyBusiness);

        int databaseSizeBeforeDelete = companyBusinessRepository.findAll().size();

        // Delete the companyBusiness
        restCompanyBusinessMockMvc.perform(delete("/api/company-businesses/{id}", companyBusiness.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyBusiness> companyBusinessList = companyBusinessRepository.findAll();
        assertThat(companyBusinessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
