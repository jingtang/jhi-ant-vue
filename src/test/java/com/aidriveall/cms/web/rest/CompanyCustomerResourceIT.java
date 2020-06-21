package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.CompanyCustomer;
import com.aidriveall.cms.domain.CompanyCustomer;
import com.aidriveall.cms.domain.CompanyUser;
import com.aidriveall.cms.domain.CompanyBusiness;
import com.aidriveall.cms.repository.CompanyCustomerRepository;
import com.aidriveall.cms.service.CompanyCustomerService;
import com.aidriveall.cms.service.dto.CompanyCustomerDTO;
import com.aidriveall.cms.service.mapper.CompanyCustomerMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.CompanyCustomerCriteria;
import com.aidriveall.cms.service.CompanyCustomerQueryService;

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
 * Integration tests for the {@link CompanyCustomerResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class CompanyCustomerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATE_USER_ID = 1L;
    private static final Long UPDATED_CREATE_USER_ID = 2L;
    private static final Long SMALLER_CREATE_USER_ID = 1L - 1L;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private CompanyCustomerRepository companyCustomerRepository;

    @Autowired
    private CompanyCustomerMapper companyCustomerMapper;

    @Autowired
    private CompanyCustomerService companyCustomerService;

    @Autowired
    private CompanyCustomerQueryService companyCustomerQueryService;

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

    private MockMvc restCompanyCustomerMockMvc;

    private CompanyCustomer companyCustomer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyCustomerResource companyCustomerResource = new CompanyCustomerResource(companyCustomerService, companyCustomerQueryService);
        this.restCompanyCustomerMockMvc = MockMvcBuilders.standaloneSetup(companyCustomerResource)
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
    public static CompanyCustomer createEntity(EntityManager em) {
        CompanyCustomer companyCustomer = new CompanyCustomer()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .address(DEFAULT_ADDRESS)
            .phoneNum(DEFAULT_PHONE_NUM)
            .logo(DEFAULT_LOGO)
            .contact(DEFAULT_CONTACT)
            .createUserId(DEFAULT_CREATE_USER_ID)
            .createTime(DEFAULT_CREATE_TIME);
        return companyCustomer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyCustomer createUpdatedEntity(EntityManager em) {
        CompanyCustomer companyCustomer = new CompanyCustomer()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .address(UPDATED_ADDRESS)
            .phoneNum(UPDATED_PHONE_NUM)
            .logo(UPDATED_LOGO)
            .contact(UPDATED_CONTACT)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createTime(UPDATED_CREATE_TIME);
        return companyCustomer;
    }

    @BeforeEach
    public void initTest() {
        companyCustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyCustomer() throws Exception {
        int databaseSizeBeforeCreate = companyCustomerRepository.findAll().size();

        // Create the CompanyCustomer
        CompanyCustomerDTO companyCustomerDTO = companyCustomerMapper.toDto(companyCustomer);
        restCompanyCustomerMockMvc.perform(post("/api/company-customers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyCustomerDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyCustomer in the database
        List<CompanyCustomer> companyCustomerList = companyCustomerRepository.findAll();
        assertThat(companyCustomerList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyCustomer testCompanyCustomer = companyCustomerList.get(companyCustomerList.size() - 1);
        assertThat(testCompanyCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyCustomer.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCompanyCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCompanyCustomer.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
        assertThat(testCompanyCustomer.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCompanyCustomer.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testCompanyCustomer.getCreateUserId()).isEqualTo(DEFAULT_CREATE_USER_ID);
        assertThat(testCompanyCustomer.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createCompanyCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyCustomerRepository.findAll().size();

        // Create the CompanyCustomer with an existing ID
        companyCustomer.setId(1L);
        CompanyCustomerDTO companyCustomerDTO = companyCustomerMapper.toDto(companyCustomer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyCustomerMockMvc.perform(post("/api/company-customers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyCustomerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyCustomer in the database
        List<CompanyCustomer> companyCustomerList = companyCustomerRepository.findAll();
        assertThat(companyCustomerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomers() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList
        restCompanyCustomerMockMvc.perform(get("/api/company-customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getCompanyCustomer() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get the companyCustomer
        restCompanyCustomerMockMvc.perform(get("/api/company-customers/{id}", companyCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyCustomer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.createUserId").value(DEFAULT_CREATE_USER_ID.intValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }


    @Test
    @Transactional
    public void getCompanyCustomersByIdFiltering() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        Long id = companyCustomer.getId();

        defaultCompanyCustomerShouldBeFound("id.equals=" + id);
        defaultCompanyCustomerShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyCustomerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyCustomerShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyCustomerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyCustomerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where name equals to DEFAULT_NAME
        defaultCompanyCustomerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyCustomerList where name equals to UPDATED_NAME
        defaultCompanyCustomerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where name not equals to DEFAULT_NAME
        defaultCompanyCustomerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyCustomerList where name not equals to UPDATED_NAME
        defaultCompanyCustomerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyCustomerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyCustomerList where name equals to UPDATED_NAME
        defaultCompanyCustomerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where name is not null
        defaultCompanyCustomerShouldBeFound("name.specified=true");

        // Get all the companyCustomerList where name is null
        defaultCompanyCustomerShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyCustomersByNameContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where name contains DEFAULT_NAME
        defaultCompanyCustomerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyCustomerList where name contains UPDATED_NAME
        defaultCompanyCustomerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where name does not contain DEFAULT_NAME
        defaultCompanyCustomerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyCustomerList where name does not contain UPDATED_NAME
        defaultCompanyCustomerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where code equals to DEFAULT_CODE
        defaultCompanyCustomerShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the companyCustomerList where code equals to UPDATED_CODE
        defaultCompanyCustomerShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where code not equals to DEFAULT_CODE
        defaultCompanyCustomerShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the companyCustomerList where code not equals to UPDATED_CODE
        defaultCompanyCustomerShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCompanyCustomerShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the companyCustomerList where code equals to UPDATED_CODE
        defaultCompanyCustomerShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where code is not null
        defaultCompanyCustomerShouldBeFound("code.specified=true");

        // Get all the companyCustomerList where code is null
        defaultCompanyCustomerShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyCustomersByCodeContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where code contains DEFAULT_CODE
        defaultCompanyCustomerShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the companyCustomerList where code contains UPDATED_CODE
        defaultCompanyCustomerShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where code does not contain DEFAULT_CODE
        defaultCompanyCustomerShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the companyCustomerList where code does not contain UPDATED_CODE
        defaultCompanyCustomerShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where address equals to DEFAULT_ADDRESS
        defaultCompanyCustomerShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the companyCustomerList where address equals to UPDATED_ADDRESS
        defaultCompanyCustomerShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where address not equals to DEFAULT_ADDRESS
        defaultCompanyCustomerShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the companyCustomerList where address not equals to UPDATED_ADDRESS
        defaultCompanyCustomerShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCompanyCustomerShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the companyCustomerList where address equals to UPDATED_ADDRESS
        defaultCompanyCustomerShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where address is not null
        defaultCompanyCustomerShouldBeFound("address.specified=true");

        // Get all the companyCustomerList where address is null
        defaultCompanyCustomerShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyCustomersByAddressContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where address contains DEFAULT_ADDRESS
        defaultCompanyCustomerShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the companyCustomerList where address contains UPDATED_ADDRESS
        defaultCompanyCustomerShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where address does not contain DEFAULT_ADDRESS
        defaultCompanyCustomerShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the companyCustomerList where address does not contain UPDATED_ADDRESS
        defaultCompanyCustomerShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByPhoneNumIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where phoneNum equals to DEFAULT_PHONE_NUM
        defaultCompanyCustomerShouldBeFound("phoneNum.equals=" + DEFAULT_PHONE_NUM);

        // Get all the companyCustomerList where phoneNum equals to UPDATED_PHONE_NUM
        defaultCompanyCustomerShouldNotBeFound("phoneNum.equals=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByPhoneNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where phoneNum not equals to DEFAULT_PHONE_NUM
        defaultCompanyCustomerShouldNotBeFound("phoneNum.notEquals=" + DEFAULT_PHONE_NUM);

        // Get all the companyCustomerList where phoneNum not equals to UPDATED_PHONE_NUM
        defaultCompanyCustomerShouldBeFound("phoneNum.notEquals=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByPhoneNumIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where phoneNum in DEFAULT_PHONE_NUM or UPDATED_PHONE_NUM
        defaultCompanyCustomerShouldBeFound("phoneNum.in=" + DEFAULT_PHONE_NUM + "," + UPDATED_PHONE_NUM);

        // Get all the companyCustomerList where phoneNum equals to UPDATED_PHONE_NUM
        defaultCompanyCustomerShouldNotBeFound("phoneNum.in=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByPhoneNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where phoneNum is not null
        defaultCompanyCustomerShouldBeFound("phoneNum.specified=true");

        // Get all the companyCustomerList where phoneNum is null
        defaultCompanyCustomerShouldNotBeFound("phoneNum.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyCustomersByPhoneNumContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where phoneNum contains DEFAULT_PHONE_NUM
        defaultCompanyCustomerShouldBeFound("phoneNum.contains=" + DEFAULT_PHONE_NUM);

        // Get all the companyCustomerList where phoneNum contains UPDATED_PHONE_NUM
        defaultCompanyCustomerShouldNotBeFound("phoneNum.contains=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByPhoneNumNotContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where phoneNum does not contain DEFAULT_PHONE_NUM
        defaultCompanyCustomerShouldNotBeFound("phoneNum.doesNotContain=" + DEFAULT_PHONE_NUM);

        // Get all the companyCustomerList where phoneNum does not contain UPDATED_PHONE_NUM
        defaultCompanyCustomerShouldBeFound("phoneNum.doesNotContain=" + UPDATED_PHONE_NUM);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByLogoIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where logo equals to DEFAULT_LOGO
        defaultCompanyCustomerShouldBeFound("logo.equals=" + DEFAULT_LOGO);

        // Get all the companyCustomerList where logo equals to UPDATED_LOGO
        defaultCompanyCustomerShouldNotBeFound("logo.equals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByLogoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where logo not equals to DEFAULT_LOGO
        defaultCompanyCustomerShouldNotBeFound("logo.notEquals=" + DEFAULT_LOGO);

        // Get all the companyCustomerList where logo not equals to UPDATED_LOGO
        defaultCompanyCustomerShouldBeFound("logo.notEquals=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByLogoIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where logo in DEFAULT_LOGO or UPDATED_LOGO
        defaultCompanyCustomerShouldBeFound("logo.in=" + DEFAULT_LOGO + "," + UPDATED_LOGO);

        // Get all the companyCustomerList where logo equals to UPDATED_LOGO
        defaultCompanyCustomerShouldNotBeFound("logo.in=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByLogoIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where logo is not null
        defaultCompanyCustomerShouldBeFound("logo.specified=true");

        // Get all the companyCustomerList where logo is null
        defaultCompanyCustomerShouldNotBeFound("logo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyCustomersByLogoContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where logo contains DEFAULT_LOGO
        defaultCompanyCustomerShouldBeFound("logo.contains=" + DEFAULT_LOGO);

        // Get all the companyCustomerList where logo contains UPDATED_LOGO
        defaultCompanyCustomerShouldNotBeFound("logo.contains=" + UPDATED_LOGO);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByLogoNotContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where logo does not contain DEFAULT_LOGO
        defaultCompanyCustomerShouldNotBeFound("logo.doesNotContain=" + DEFAULT_LOGO);

        // Get all the companyCustomerList where logo does not contain UPDATED_LOGO
        defaultCompanyCustomerShouldBeFound("logo.doesNotContain=" + UPDATED_LOGO);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where contact equals to DEFAULT_CONTACT
        defaultCompanyCustomerShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the companyCustomerList where contact equals to UPDATED_CONTACT
        defaultCompanyCustomerShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where contact not equals to DEFAULT_CONTACT
        defaultCompanyCustomerShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the companyCustomerList where contact not equals to UPDATED_CONTACT
        defaultCompanyCustomerShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByContactIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultCompanyCustomerShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the companyCustomerList where contact equals to UPDATED_CONTACT
        defaultCompanyCustomerShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where contact is not null
        defaultCompanyCustomerShouldBeFound("contact.specified=true");

        // Get all the companyCustomerList where contact is null
        defaultCompanyCustomerShouldNotBeFound("contact.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompanyCustomersByContactContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where contact contains DEFAULT_CONTACT
        defaultCompanyCustomerShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the companyCustomerList where contact contains UPDATED_CONTACT
        defaultCompanyCustomerShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByContactNotContainsSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where contact does not contain DEFAULT_CONTACT
        defaultCompanyCustomerShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the companyCustomerList where contact does not contain UPDATED_CONTACT
        defaultCompanyCustomerShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId equals to DEFAULT_CREATE_USER_ID
        defaultCompanyCustomerShouldBeFound("createUserId.equals=" + DEFAULT_CREATE_USER_ID);

        // Get all the companyCustomerList where createUserId equals to UPDATED_CREATE_USER_ID
        defaultCompanyCustomerShouldNotBeFound("createUserId.equals=" + UPDATED_CREATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId not equals to DEFAULT_CREATE_USER_ID
        defaultCompanyCustomerShouldNotBeFound("createUserId.notEquals=" + DEFAULT_CREATE_USER_ID);

        // Get all the companyCustomerList where createUserId not equals to UPDATED_CREATE_USER_ID
        defaultCompanyCustomerShouldBeFound("createUserId.notEquals=" + UPDATED_CREATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId in DEFAULT_CREATE_USER_ID or UPDATED_CREATE_USER_ID
        defaultCompanyCustomerShouldBeFound("createUserId.in=" + DEFAULT_CREATE_USER_ID + "," + UPDATED_CREATE_USER_ID);

        // Get all the companyCustomerList where createUserId equals to UPDATED_CREATE_USER_ID
        defaultCompanyCustomerShouldNotBeFound("createUserId.in=" + UPDATED_CREATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId is not null
        defaultCompanyCustomerShouldBeFound("createUserId.specified=true");

        // Get all the companyCustomerList where createUserId is null
        defaultCompanyCustomerShouldNotBeFound("createUserId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId is greater than or equal to DEFAULT_CREATE_USER_ID
        defaultCompanyCustomerShouldBeFound("createUserId.greaterThanOrEqual=" + DEFAULT_CREATE_USER_ID);

        // Get all the companyCustomerList where createUserId is greater than or equal to UPDATED_CREATE_USER_ID
        defaultCompanyCustomerShouldNotBeFound("createUserId.greaterThanOrEqual=" + UPDATED_CREATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId is less than or equal to DEFAULT_CREATE_USER_ID
        defaultCompanyCustomerShouldBeFound("createUserId.lessThanOrEqual=" + DEFAULT_CREATE_USER_ID);

        // Get all the companyCustomerList where createUserId is less than or equal to SMALLER_CREATE_USER_ID
        defaultCompanyCustomerShouldNotBeFound("createUserId.lessThanOrEqual=" + SMALLER_CREATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId is less than DEFAULT_CREATE_USER_ID
        defaultCompanyCustomerShouldNotBeFound("createUserId.lessThan=" + DEFAULT_CREATE_USER_ID);

        // Get all the companyCustomerList where createUserId is less than UPDATED_CREATE_USER_ID
        defaultCompanyCustomerShouldBeFound("createUserId.lessThan=" + UPDATED_CREATE_USER_ID);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createUserId is greater than DEFAULT_CREATE_USER_ID
        defaultCompanyCustomerShouldNotBeFound("createUserId.greaterThan=" + DEFAULT_CREATE_USER_ID);

        // Get all the companyCustomerList where createUserId is greater than SMALLER_CREATE_USER_ID
        defaultCompanyCustomerShouldBeFound("createUserId.greaterThan=" + SMALLER_CREATE_USER_ID);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime equals to DEFAULT_CREATE_TIME
        defaultCompanyCustomerShouldBeFound("createTime.equals=" + DEFAULT_CREATE_TIME);

        // Get all the companyCustomerList where createTime equals to UPDATED_CREATE_TIME
        defaultCompanyCustomerShouldNotBeFound("createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime not equals to DEFAULT_CREATE_TIME
        defaultCompanyCustomerShouldNotBeFound("createTime.notEquals=" + DEFAULT_CREATE_TIME);

        // Get all the companyCustomerList where createTime not equals to UPDATED_CREATE_TIME
        defaultCompanyCustomerShouldBeFound("createTime.notEquals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime in DEFAULT_CREATE_TIME or UPDATED_CREATE_TIME
        defaultCompanyCustomerShouldBeFound("createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME);

        // Get all the companyCustomerList where createTime equals to UPDATED_CREATE_TIME
        defaultCompanyCustomerShouldNotBeFound("createTime.in=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime is not null
        defaultCompanyCustomerShouldBeFound("createTime.specified=true");

        // Get all the companyCustomerList where createTime is null
        defaultCompanyCustomerShouldNotBeFound("createTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime is greater than or equal to DEFAULT_CREATE_TIME
        defaultCompanyCustomerShouldBeFound("createTime.greaterThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the companyCustomerList where createTime is greater than or equal to UPDATED_CREATE_TIME
        defaultCompanyCustomerShouldNotBeFound("createTime.greaterThanOrEqual=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime is less than or equal to DEFAULT_CREATE_TIME
        defaultCompanyCustomerShouldBeFound("createTime.lessThanOrEqual=" + DEFAULT_CREATE_TIME);

        // Get all the companyCustomerList where createTime is less than or equal to SMALLER_CREATE_TIME
        defaultCompanyCustomerShouldNotBeFound("createTime.lessThanOrEqual=" + SMALLER_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime is less than DEFAULT_CREATE_TIME
        defaultCompanyCustomerShouldNotBeFound("createTime.lessThan=" + DEFAULT_CREATE_TIME);

        // Get all the companyCustomerList where createTime is less than UPDATED_CREATE_TIME
        defaultCompanyCustomerShouldBeFound("createTime.lessThan=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCompanyCustomersByCreateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        // Get all the companyCustomerList where createTime is greater than DEFAULT_CREATE_TIME
        defaultCompanyCustomerShouldNotBeFound("createTime.greaterThan=" + DEFAULT_CREATE_TIME);

        // Get all the companyCustomerList where createTime is greater than SMALLER_CREATE_TIME
        defaultCompanyCustomerShouldBeFound("createTime.greaterThan=" + SMALLER_CREATE_TIME);
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);
        CompanyCustomer children = CompanyCustomerResourceIT.createEntity(em);
        em.persist(children);
        em.flush();
        companyCustomer.addChildren(children);
        companyCustomerRepository.saveAndFlush(companyCustomer);
        Long childrenId = children.getId();

        // Get all the companyCustomerList where children equals to childrenId
        defaultCompanyCustomerShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the companyCustomerList where children equals to childrenId + 1
        defaultCompanyCustomerShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByCompanyUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);
        CompanyUser companyUsers = CompanyUserResourceIT.createEntity(em);
        em.persist(companyUsers);
        em.flush();
        companyCustomer.addCompanyUsers(companyUsers);
        companyCustomerRepository.saveAndFlush(companyCustomer);
        Long companyUsersId = companyUsers.getId();

        // Get all the companyCustomerList where companyUsers equals to companyUsersId
        defaultCompanyCustomerShouldBeFound("companyUsersId.equals=" + companyUsersId);

        // Get all the companyCustomerList where companyUsers equals to companyUsersId + 1
        defaultCompanyCustomerShouldNotBeFound("companyUsersId.equals=" + (companyUsersId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByCompanyBusinessesIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);
        CompanyBusiness companyBusinesses = CompanyBusinessResourceIT.createEntity(em);
        em.persist(companyBusinesses);
        em.flush();
        companyCustomer.addCompanyBusinesses(companyBusinesses);
        companyCustomerRepository.saveAndFlush(companyCustomer);
        Long companyBusinessesId = companyBusinesses.getId();

        // Get all the companyCustomerList where companyBusinesses equals to companyBusinessesId
        defaultCompanyCustomerShouldBeFound("companyBusinessesId.equals=" + companyBusinessesId);

        // Get all the companyCustomerList where companyBusinesses equals to companyBusinessesId + 1
        defaultCompanyCustomerShouldNotBeFound("companyBusinessesId.equals=" + (companyBusinessesId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyCustomersByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);
        CompanyCustomer parent = CompanyCustomerResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        companyCustomer.setParent(parent);
        companyCustomerRepository.saveAndFlush(companyCustomer);
        Long parentId = parent.getId();

        // Get all the companyCustomerList where parent equals to parentId
        defaultCompanyCustomerShouldBeFound("parentId.equals=" + parentId);

        // Get all the companyCustomerList where parent equals to parentId + 1
        defaultCompanyCustomerShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyCustomerShouldBeFound(String filter) throws Exception {
        restCompanyCustomerMockMvc.perform(get("/api/company-customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].createUserId").value(hasItem(DEFAULT_CREATE_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));

        // Check, that the count call also returns 1
        restCompanyCustomerMockMvc.perform(get("/api/company-customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyCustomerShouldNotBeFound(String filter) throws Exception {
        restCompanyCustomerMockMvc.perform(get("/api/company-customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyCustomerMockMvc.perform(get("/api/company-customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompanyCustomer() throws Exception {
        // Get the companyCustomer
        restCompanyCustomerMockMvc.perform(get("/api/company-customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyCustomer() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        int databaseSizeBeforeUpdate = companyCustomerRepository.findAll().size();

        // Update the companyCustomer
        CompanyCustomer updatedCompanyCustomer = companyCustomerRepository.findById(companyCustomer.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyCustomer are not directly saved in db
        em.detach(updatedCompanyCustomer);
        updatedCompanyCustomer
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .address(UPDATED_ADDRESS)
            .phoneNum(UPDATED_PHONE_NUM)
            .logo(UPDATED_LOGO)
            .contact(UPDATED_CONTACT)
            .createUserId(UPDATED_CREATE_USER_ID)
            .createTime(UPDATED_CREATE_TIME);
        CompanyCustomerDTO companyCustomerDTO = companyCustomerMapper.toDto(updatedCompanyCustomer);

        restCompanyCustomerMockMvc.perform(put("/api/company-customers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyCustomerDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyCustomer in the database
        List<CompanyCustomer> companyCustomerList = companyCustomerRepository.findAll();
        assertThat(companyCustomerList).hasSize(databaseSizeBeforeUpdate);
        CompanyCustomer testCompanyCustomer = companyCustomerList.get(companyCustomerList.size() - 1);
        assertThat(testCompanyCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyCustomer.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCompanyCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompanyCustomer.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
        assertThat(testCompanyCustomer.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCompanyCustomer.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testCompanyCustomer.getCreateUserId()).isEqualTo(UPDATED_CREATE_USER_ID);
        assertThat(testCompanyCustomer.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyCustomer() throws Exception {
        int databaseSizeBeforeUpdate = companyCustomerRepository.findAll().size();

        // Create the CompanyCustomer
        CompanyCustomerDTO companyCustomerDTO = companyCustomerMapper.toDto(companyCustomer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyCustomerMockMvc.perform(put("/api/company-customers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyCustomerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyCustomer in the database
        List<CompanyCustomer> companyCustomerList = companyCustomerRepository.findAll();
        assertThat(companyCustomerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyCustomer() throws Exception {
        // Initialize the database
        companyCustomerRepository.saveAndFlush(companyCustomer);

        int databaseSizeBeforeDelete = companyCustomerRepository.findAll().size();

        // Delete the companyCustomer
        restCompanyCustomerMockMvc.perform(delete("/api/company-customers/{id}", companyCustomer.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyCustomer> companyCustomerList = companyCustomerRepository.findAll();
        assertThat(companyCustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
