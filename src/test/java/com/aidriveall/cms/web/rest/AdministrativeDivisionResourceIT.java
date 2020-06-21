package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.AdministrativeDivision;
import com.aidriveall.cms.domain.AdministrativeDivision;
import com.aidriveall.cms.repository.AdministrativeDivisionRepository;
import com.aidriveall.cms.service.AdministrativeDivisionService;
import com.aidriveall.cms.service.dto.AdministrativeDivisionDTO;
import com.aidriveall.cms.service.mapper.AdministrativeDivisionMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.AdministrativeDivisionCriteria;
import com.aidriveall.cms.service.AdministrativeDivisionQueryService;

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
import java.util.List;

import static com.aidriveall.cms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AdministrativeDivisionResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class AdministrativeDivisionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AREA_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MERGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MERGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;
    private static final Integer SMALLER_LEVEL = 1 - 1;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;
    private static final Double SMALLER_LNG = 1D - 1D;

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;
    private static final Double SMALLER_LAT = 1D - 1D;

    @Autowired
    private AdministrativeDivisionRepository administrativeDivisionRepository;

    @Autowired
    private AdministrativeDivisionMapper administrativeDivisionMapper;

    @Autowired
    private AdministrativeDivisionService administrativeDivisionService;

    @Autowired
    private AdministrativeDivisionQueryService administrativeDivisionQueryService;

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

    private MockMvc restAdministrativeDivisionMockMvc;

    private AdministrativeDivision administrativeDivision;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdministrativeDivisionResource administrativeDivisionResource = new AdministrativeDivisionResource(administrativeDivisionService, administrativeDivisionQueryService);
        this.restAdministrativeDivisionMockMvc = MockMvcBuilders.standaloneSetup(administrativeDivisionResource)
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
    public static AdministrativeDivision createEntity(EntityManager em) {
        AdministrativeDivision administrativeDivision = new AdministrativeDivision()
            .name(DEFAULT_NAME)
            .areaCode(DEFAULT_AREA_CODE)
            .cityCode(DEFAULT_CITY_CODE)
            .mergerName(DEFAULT_MERGER_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .zipCode(DEFAULT_ZIP_CODE)
            .level(DEFAULT_LEVEL)
            .lng(DEFAULT_LNG)
            .lat(DEFAULT_LAT);
        return administrativeDivision;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdministrativeDivision createUpdatedEntity(EntityManager em) {
        AdministrativeDivision administrativeDivision = new AdministrativeDivision()
            .name(UPDATED_NAME)
            .areaCode(UPDATED_AREA_CODE)
            .cityCode(UPDATED_CITY_CODE)
            .mergerName(UPDATED_MERGER_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .level(UPDATED_LEVEL)
            .lng(UPDATED_LNG)
            .lat(UPDATED_LAT);
        return administrativeDivision;
    }

    @BeforeEach
    public void initTest() {
        administrativeDivision = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdministrativeDivision() throws Exception {
        int databaseSizeBeforeCreate = administrativeDivisionRepository.findAll().size();

        // Create the AdministrativeDivision
        AdministrativeDivisionDTO administrativeDivisionDTO = administrativeDivisionMapper.toDto(administrativeDivision);
        restAdministrativeDivisionMockMvc.perform(post("/api/administrative-divisions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(administrativeDivisionDTO)))
            .andExpect(status().isCreated());

        // Validate the AdministrativeDivision in the database
        List<AdministrativeDivision> administrativeDivisionList = administrativeDivisionRepository.findAll();
        assertThat(administrativeDivisionList).hasSize(databaseSizeBeforeCreate + 1);
        AdministrativeDivision testAdministrativeDivision = administrativeDivisionList.get(administrativeDivisionList.size() - 1);
        assertThat(testAdministrativeDivision.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdministrativeDivision.getAreaCode()).isEqualTo(DEFAULT_AREA_CODE);
        assertThat(testAdministrativeDivision.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testAdministrativeDivision.getMergerName()).isEqualTo(DEFAULT_MERGER_NAME);
        assertThat(testAdministrativeDivision.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testAdministrativeDivision.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testAdministrativeDivision.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testAdministrativeDivision.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testAdministrativeDivision.getLat()).isEqualTo(DEFAULT_LAT);
    }

    @Test
    @Transactional
    public void createAdministrativeDivisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = administrativeDivisionRepository.findAll().size();

        // Create the AdministrativeDivision with an existing ID
        administrativeDivision.setId(1L);
        AdministrativeDivisionDTO administrativeDivisionDTO = administrativeDivisionMapper.toDto(administrativeDivision);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministrativeDivisionMockMvc.perform(post("/api/administrative-divisions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(administrativeDivisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeDivision in the database
        List<AdministrativeDivision> administrativeDivisionList = administrativeDivisionRepository.findAll();
        assertThat(administrativeDivisionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisions() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList
        restAdministrativeDivisionMockMvc.perform(get("/api/administrative-divisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrativeDivision.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].areaCode").value(hasItem(DEFAULT_AREA_CODE)))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].mergerName").value(hasItem(DEFAULT_MERGER_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAdministrativeDivision() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get the administrativeDivision
        restAdministrativeDivisionMockMvc.perform(get("/api/administrative-divisions/{id}", administrativeDivision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administrativeDivision.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.areaCode").value(DEFAULT_AREA_CODE))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE))
            .andExpect(jsonPath("$.mergerName").value(DEFAULT_MERGER_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()));
    }


    @Test
    @Transactional
    public void getAdministrativeDivisionsByIdFiltering() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        Long id = administrativeDivision.getId();

        defaultAdministrativeDivisionShouldBeFound("id.equals=" + id);
        defaultAdministrativeDivisionShouldNotBeFound("id.notEquals=" + id);

        defaultAdministrativeDivisionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdministrativeDivisionShouldNotBeFound("id.greaterThan=" + id);

        defaultAdministrativeDivisionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdministrativeDivisionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where name equals to DEFAULT_NAME
        defaultAdministrativeDivisionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the administrativeDivisionList where name equals to UPDATED_NAME
        defaultAdministrativeDivisionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where name not equals to DEFAULT_NAME
        defaultAdministrativeDivisionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the administrativeDivisionList where name not equals to UPDATED_NAME
        defaultAdministrativeDivisionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdministrativeDivisionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the administrativeDivisionList where name equals to UPDATED_NAME
        defaultAdministrativeDivisionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where name is not null
        defaultAdministrativeDivisionShouldBeFound("name.specified=true");

        // Get all the administrativeDivisionList where name is null
        defaultAdministrativeDivisionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdministrativeDivisionsByNameContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where name contains DEFAULT_NAME
        defaultAdministrativeDivisionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the administrativeDivisionList where name contains UPDATED_NAME
        defaultAdministrativeDivisionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where name does not contain DEFAULT_NAME
        defaultAdministrativeDivisionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the administrativeDivisionList where name does not contain UPDATED_NAME
        defaultAdministrativeDivisionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByAreaCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where areaCode equals to DEFAULT_AREA_CODE
        defaultAdministrativeDivisionShouldBeFound("areaCode.equals=" + DEFAULT_AREA_CODE);

        // Get all the administrativeDivisionList where areaCode equals to UPDATED_AREA_CODE
        defaultAdministrativeDivisionShouldNotBeFound("areaCode.equals=" + UPDATED_AREA_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByAreaCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where areaCode not equals to DEFAULT_AREA_CODE
        defaultAdministrativeDivisionShouldNotBeFound("areaCode.notEquals=" + DEFAULT_AREA_CODE);

        // Get all the administrativeDivisionList where areaCode not equals to UPDATED_AREA_CODE
        defaultAdministrativeDivisionShouldBeFound("areaCode.notEquals=" + UPDATED_AREA_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByAreaCodeIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where areaCode in DEFAULT_AREA_CODE or UPDATED_AREA_CODE
        defaultAdministrativeDivisionShouldBeFound("areaCode.in=" + DEFAULT_AREA_CODE + "," + UPDATED_AREA_CODE);

        // Get all the administrativeDivisionList where areaCode equals to UPDATED_AREA_CODE
        defaultAdministrativeDivisionShouldNotBeFound("areaCode.in=" + UPDATED_AREA_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByAreaCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where areaCode is not null
        defaultAdministrativeDivisionShouldBeFound("areaCode.specified=true");

        // Get all the administrativeDivisionList where areaCode is null
        defaultAdministrativeDivisionShouldNotBeFound("areaCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdministrativeDivisionsByAreaCodeContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where areaCode contains DEFAULT_AREA_CODE
        defaultAdministrativeDivisionShouldBeFound("areaCode.contains=" + DEFAULT_AREA_CODE);

        // Get all the administrativeDivisionList where areaCode contains UPDATED_AREA_CODE
        defaultAdministrativeDivisionShouldNotBeFound("areaCode.contains=" + UPDATED_AREA_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByAreaCodeNotContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where areaCode does not contain DEFAULT_AREA_CODE
        defaultAdministrativeDivisionShouldNotBeFound("areaCode.doesNotContain=" + DEFAULT_AREA_CODE);

        // Get all the administrativeDivisionList where areaCode does not contain UPDATED_AREA_CODE
        defaultAdministrativeDivisionShouldBeFound("areaCode.doesNotContain=" + UPDATED_AREA_CODE);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByCityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where cityCode equals to DEFAULT_CITY_CODE
        defaultAdministrativeDivisionShouldBeFound("cityCode.equals=" + DEFAULT_CITY_CODE);

        // Get all the administrativeDivisionList where cityCode equals to UPDATED_CITY_CODE
        defaultAdministrativeDivisionShouldNotBeFound("cityCode.equals=" + UPDATED_CITY_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByCityCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where cityCode not equals to DEFAULT_CITY_CODE
        defaultAdministrativeDivisionShouldNotBeFound("cityCode.notEquals=" + DEFAULT_CITY_CODE);

        // Get all the administrativeDivisionList where cityCode not equals to UPDATED_CITY_CODE
        defaultAdministrativeDivisionShouldBeFound("cityCode.notEquals=" + UPDATED_CITY_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByCityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where cityCode in DEFAULT_CITY_CODE or UPDATED_CITY_CODE
        defaultAdministrativeDivisionShouldBeFound("cityCode.in=" + DEFAULT_CITY_CODE + "," + UPDATED_CITY_CODE);

        // Get all the administrativeDivisionList where cityCode equals to UPDATED_CITY_CODE
        defaultAdministrativeDivisionShouldNotBeFound("cityCode.in=" + UPDATED_CITY_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByCityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where cityCode is not null
        defaultAdministrativeDivisionShouldBeFound("cityCode.specified=true");

        // Get all the administrativeDivisionList where cityCode is null
        defaultAdministrativeDivisionShouldNotBeFound("cityCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdministrativeDivisionsByCityCodeContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where cityCode contains DEFAULT_CITY_CODE
        defaultAdministrativeDivisionShouldBeFound("cityCode.contains=" + DEFAULT_CITY_CODE);

        // Get all the administrativeDivisionList where cityCode contains UPDATED_CITY_CODE
        defaultAdministrativeDivisionShouldNotBeFound("cityCode.contains=" + UPDATED_CITY_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByCityCodeNotContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where cityCode does not contain DEFAULT_CITY_CODE
        defaultAdministrativeDivisionShouldNotBeFound("cityCode.doesNotContain=" + DEFAULT_CITY_CODE);

        // Get all the administrativeDivisionList where cityCode does not contain UPDATED_CITY_CODE
        defaultAdministrativeDivisionShouldBeFound("cityCode.doesNotContain=" + UPDATED_CITY_CODE);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByMergerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where mergerName equals to DEFAULT_MERGER_NAME
        defaultAdministrativeDivisionShouldBeFound("mergerName.equals=" + DEFAULT_MERGER_NAME);

        // Get all the administrativeDivisionList where mergerName equals to UPDATED_MERGER_NAME
        defaultAdministrativeDivisionShouldNotBeFound("mergerName.equals=" + UPDATED_MERGER_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByMergerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where mergerName not equals to DEFAULT_MERGER_NAME
        defaultAdministrativeDivisionShouldNotBeFound("mergerName.notEquals=" + DEFAULT_MERGER_NAME);

        // Get all the administrativeDivisionList where mergerName not equals to UPDATED_MERGER_NAME
        defaultAdministrativeDivisionShouldBeFound("mergerName.notEquals=" + UPDATED_MERGER_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByMergerNameIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where mergerName in DEFAULT_MERGER_NAME or UPDATED_MERGER_NAME
        defaultAdministrativeDivisionShouldBeFound("mergerName.in=" + DEFAULT_MERGER_NAME + "," + UPDATED_MERGER_NAME);

        // Get all the administrativeDivisionList where mergerName equals to UPDATED_MERGER_NAME
        defaultAdministrativeDivisionShouldNotBeFound("mergerName.in=" + UPDATED_MERGER_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByMergerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where mergerName is not null
        defaultAdministrativeDivisionShouldBeFound("mergerName.specified=true");

        // Get all the administrativeDivisionList where mergerName is null
        defaultAdministrativeDivisionShouldNotBeFound("mergerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdministrativeDivisionsByMergerNameContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where mergerName contains DEFAULT_MERGER_NAME
        defaultAdministrativeDivisionShouldBeFound("mergerName.contains=" + DEFAULT_MERGER_NAME);

        // Get all the administrativeDivisionList where mergerName contains UPDATED_MERGER_NAME
        defaultAdministrativeDivisionShouldNotBeFound("mergerName.contains=" + UPDATED_MERGER_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByMergerNameNotContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where mergerName does not contain DEFAULT_MERGER_NAME
        defaultAdministrativeDivisionShouldNotBeFound("mergerName.doesNotContain=" + DEFAULT_MERGER_NAME);

        // Get all the administrativeDivisionList where mergerName does not contain UPDATED_MERGER_NAME
        defaultAdministrativeDivisionShouldBeFound("mergerName.doesNotContain=" + UPDATED_MERGER_NAME);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where shortName equals to DEFAULT_SHORT_NAME
        defaultAdministrativeDivisionShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the administrativeDivisionList where shortName equals to UPDATED_SHORT_NAME
        defaultAdministrativeDivisionShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByShortNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where shortName not equals to DEFAULT_SHORT_NAME
        defaultAdministrativeDivisionShouldNotBeFound("shortName.notEquals=" + DEFAULT_SHORT_NAME);

        // Get all the administrativeDivisionList where shortName not equals to UPDATED_SHORT_NAME
        defaultAdministrativeDivisionShouldBeFound("shortName.notEquals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultAdministrativeDivisionShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the administrativeDivisionList where shortName equals to UPDATED_SHORT_NAME
        defaultAdministrativeDivisionShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where shortName is not null
        defaultAdministrativeDivisionShouldBeFound("shortName.specified=true");

        // Get all the administrativeDivisionList where shortName is null
        defaultAdministrativeDivisionShouldNotBeFound("shortName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdministrativeDivisionsByShortNameContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where shortName contains DEFAULT_SHORT_NAME
        defaultAdministrativeDivisionShouldBeFound("shortName.contains=" + DEFAULT_SHORT_NAME);

        // Get all the administrativeDivisionList where shortName contains UPDATED_SHORT_NAME
        defaultAdministrativeDivisionShouldNotBeFound("shortName.contains=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByShortNameNotContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where shortName does not contain DEFAULT_SHORT_NAME
        defaultAdministrativeDivisionShouldNotBeFound("shortName.doesNotContain=" + DEFAULT_SHORT_NAME);

        // Get all the administrativeDivisionList where shortName does not contain UPDATED_SHORT_NAME
        defaultAdministrativeDivisionShouldBeFound("shortName.doesNotContain=" + UPDATED_SHORT_NAME);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByZipCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where zipCode equals to DEFAULT_ZIP_CODE
        defaultAdministrativeDivisionShouldBeFound("zipCode.equals=" + DEFAULT_ZIP_CODE);

        // Get all the administrativeDivisionList where zipCode equals to UPDATED_ZIP_CODE
        defaultAdministrativeDivisionShouldNotBeFound("zipCode.equals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByZipCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where zipCode not equals to DEFAULT_ZIP_CODE
        defaultAdministrativeDivisionShouldNotBeFound("zipCode.notEquals=" + DEFAULT_ZIP_CODE);

        // Get all the administrativeDivisionList where zipCode not equals to UPDATED_ZIP_CODE
        defaultAdministrativeDivisionShouldBeFound("zipCode.notEquals=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByZipCodeIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where zipCode in DEFAULT_ZIP_CODE or UPDATED_ZIP_CODE
        defaultAdministrativeDivisionShouldBeFound("zipCode.in=" + DEFAULT_ZIP_CODE + "," + UPDATED_ZIP_CODE);

        // Get all the administrativeDivisionList where zipCode equals to UPDATED_ZIP_CODE
        defaultAdministrativeDivisionShouldNotBeFound("zipCode.in=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByZipCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where zipCode is not null
        defaultAdministrativeDivisionShouldBeFound("zipCode.specified=true");

        // Get all the administrativeDivisionList where zipCode is null
        defaultAdministrativeDivisionShouldNotBeFound("zipCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdministrativeDivisionsByZipCodeContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where zipCode contains DEFAULT_ZIP_CODE
        defaultAdministrativeDivisionShouldBeFound("zipCode.contains=" + DEFAULT_ZIP_CODE);

        // Get all the administrativeDivisionList where zipCode contains UPDATED_ZIP_CODE
        defaultAdministrativeDivisionShouldNotBeFound("zipCode.contains=" + UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByZipCodeNotContainsSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where zipCode does not contain DEFAULT_ZIP_CODE
        defaultAdministrativeDivisionShouldNotBeFound("zipCode.doesNotContain=" + DEFAULT_ZIP_CODE);

        // Get all the administrativeDivisionList where zipCode does not contain UPDATED_ZIP_CODE
        defaultAdministrativeDivisionShouldBeFound("zipCode.doesNotContain=" + UPDATED_ZIP_CODE);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level equals to DEFAULT_LEVEL
        defaultAdministrativeDivisionShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the administrativeDivisionList where level equals to UPDATED_LEVEL
        defaultAdministrativeDivisionShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level not equals to DEFAULT_LEVEL
        defaultAdministrativeDivisionShouldNotBeFound("level.notEquals=" + DEFAULT_LEVEL);

        // Get all the administrativeDivisionList where level not equals to UPDATED_LEVEL
        defaultAdministrativeDivisionShouldBeFound("level.notEquals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultAdministrativeDivisionShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the administrativeDivisionList where level equals to UPDATED_LEVEL
        defaultAdministrativeDivisionShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level is not null
        defaultAdministrativeDivisionShouldBeFound("level.specified=true");

        // Get all the administrativeDivisionList where level is null
        defaultAdministrativeDivisionShouldNotBeFound("level.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level is greater than or equal to DEFAULT_LEVEL
        defaultAdministrativeDivisionShouldBeFound("level.greaterThanOrEqual=" + DEFAULT_LEVEL);

        // Get all the administrativeDivisionList where level is greater than or equal to UPDATED_LEVEL
        defaultAdministrativeDivisionShouldNotBeFound("level.greaterThanOrEqual=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level is less than or equal to DEFAULT_LEVEL
        defaultAdministrativeDivisionShouldBeFound("level.lessThanOrEqual=" + DEFAULT_LEVEL);

        // Get all the administrativeDivisionList where level is less than or equal to SMALLER_LEVEL
        defaultAdministrativeDivisionShouldNotBeFound("level.lessThanOrEqual=" + SMALLER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level is less than DEFAULT_LEVEL
        defaultAdministrativeDivisionShouldNotBeFound("level.lessThan=" + DEFAULT_LEVEL);

        // Get all the administrativeDivisionList where level is less than UPDATED_LEVEL
        defaultAdministrativeDivisionShouldBeFound("level.lessThan=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where level is greater than DEFAULT_LEVEL
        defaultAdministrativeDivisionShouldNotBeFound("level.greaterThan=" + DEFAULT_LEVEL);

        // Get all the administrativeDivisionList where level is greater than SMALLER_LEVEL
        defaultAdministrativeDivisionShouldBeFound("level.greaterThan=" + SMALLER_LEVEL);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng equals to DEFAULT_LNG
        defaultAdministrativeDivisionShouldBeFound("lng.equals=" + DEFAULT_LNG);

        // Get all the administrativeDivisionList where lng equals to UPDATED_LNG
        defaultAdministrativeDivisionShouldNotBeFound("lng.equals=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng not equals to DEFAULT_LNG
        defaultAdministrativeDivisionShouldNotBeFound("lng.notEquals=" + DEFAULT_LNG);

        // Get all the administrativeDivisionList where lng not equals to UPDATED_LNG
        defaultAdministrativeDivisionShouldBeFound("lng.notEquals=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng in DEFAULT_LNG or UPDATED_LNG
        defaultAdministrativeDivisionShouldBeFound("lng.in=" + DEFAULT_LNG + "," + UPDATED_LNG);

        // Get all the administrativeDivisionList where lng equals to UPDATED_LNG
        defaultAdministrativeDivisionShouldNotBeFound("lng.in=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng is not null
        defaultAdministrativeDivisionShouldBeFound("lng.specified=true");

        // Get all the administrativeDivisionList where lng is null
        defaultAdministrativeDivisionShouldNotBeFound("lng.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng is greater than or equal to DEFAULT_LNG
        defaultAdministrativeDivisionShouldBeFound("lng.greaterThanOrEqual=" + DEFAULT_LNG);

        // Get all the administrativeDivisionList where lng is greater than or equal to UPDATED_LNG
        defaultAdministrativeDivisionShouldNotBeFound("lng.greaterThanOrEqual=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng is less than or equal to DEFAULT_LNG
        defaultAdministrativeDivisionShouldBeFound("lng.lessThanOrEqual=" + DEFAULT_LNG);

        // Get all the administrativeDivisionList where lng is less than or equal to SMALLER_LNG
        defaultAdministrativeDivisionShouldNotBeFound("lng.lessThanOrEqual=" + SMALLER_LNG);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsLessThanSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng is less than DEFAULT_LNG
        defaultAdministrativeDivisionShouldNotBeFound("lng.lessThan=" + DEFAULT_LNG);

        // Get all the administrativeDivisionList where lng is less than UPDATED_LNG
        defaultAdministrativeDivisionShouldBeFound("lng.lessThan=" + UPDATED_LNG);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLngIsGreaterThanSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lng is greater than DEFAULT_LNG
        defaultAdministrativeDivisionShouldNotBeFound("lng.greaterThan=" + DEFAULT_LNG);

        // Get all the administrativeDivisionList where lng is greater than SMALLER_LNG
        defaultAdministrativeDivisionShouldBeFound("lng.greaterThan=" + SMALLER_LNG);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat equals to DEFAULT_LAT
        defaultAdministrativeDivisionShouldBeFound("lat.equals=" + DEFAULT_LAT);

        // Get all the administrativeDivisionList where lat equals to UPDATED_LAT
        defaultAdministrativeDivisionShouldNotBeFound("lat.equals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat not equals to DEFAULT_LAT
        defaultAdministrativeDivisionShouldNotBeFound("lat.notEquals=" + DEFAULT_LAT);

        // Get all the administrativeDivisionList where lat not equals to UPDATED_LAT
        defaultAdministrativeDivisionShouldBeFound("lat.notEquals=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsInShouldWork() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat in DEFAULT_LAT or UPDATED_LAT
        defaultAdministrativeDivisionShouldBeFound("lat.in=" + DEFAULT_LAT + "," + UPDATED_LAT);

        // Get all the administrativeDivisionList where lat equals to UPDATED_LAT
        defaultAdministrativeDivisionShouldNotBeFound("lat.in=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat is not null
        defaultAdministrativeDivisionShouldBeFound("lat.specified=true");

        // Get all the administrativeDivisionList where lat is null
        defaultAdministrativeDivisionShouldNotBeFound("lat.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat is greater than or equal to DEFAULT_LAT
        defaultAdministrativeDivisionShouldBeFound("lat.greaterThanOrEqual=" + DEFAULT_LAT);

        // Get all the administrativeDivisionList where lat is greater than or equal to UPDATED_LAT
        defaultAdministrativeDivisionShouldNotBeFound("lat.greaterThanOrEqual=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat is less than or equal to DEFAULT_LAT
        defaultAdministrativeDivisionShouldBeFound("lat.lessThanOrEqual=" + DEFAULT_LAT);

        // Get all the administrativeDivisionList where lat is less than or equal to SMALLER_LAT
        defaultAdministrativeDivisionShouldNotBeFound("lat.lessThanOrEqual=" + SMALLER_LAT);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsLessThanSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat is less than DEFAULT_LAT
        defaultAdministrativeDivisionShouldNotBeFound("lat.lessThan=" + DEFAULT_LAT);

        // Get all the administrativeDivisionList where lat is less than UPDATED_LAT
        defaultAdministrativeDivisionShouldBeFound("lat.lessThan=" + UPDATED_LAT);
    }

    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        // Get all the administrativeDivisionList where lat is greater than DEFAULT_LAT
        defaultAdministrativeDivisionShouldNotBeFound("lat.greaterThan=" + DEFAULT_LAT);

        // Get all the administrativeDivisionList where lat is greater than SMALLER_LAT
        defaultAdministrativeDivisionShouldBeFound("lat.greaterThan=" + SMALLER_LAT);
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);
        AdministrativeDivision children = AdministrativeDivisionResourceIT.createEntity(em);
        em.persist(children);
        em.flush();
        administrativeDivision.addChildren(children);
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);
        Long childrenId = children.getId();

        // Get all the administrativeDivisionList where children equals to childrenId
        defaultAdministrativeDivisionShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the administrativeDivisionList where children equals to childrenId + 1
        defaultAdministrativeDivisionShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }


    @Test
    @Transactional
    public void getAllAdministrativeDivisionsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);
        AdministrativeDivision parent = AdministrativeDivisionResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        administrativeDivision.setParent(parent);
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);
        Long parentId = parent.getId();

        // Get all the administrativeDivisionList where parent equals to parentId
        defaultAdministrativeDivisionShouldBeFound("parentId.equals=" + parentId);

        // Get all the administrativeDivisionList where parent equals to parentId + 1
        defaultAdministrativeDivisionShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdministrativeDivisionShouldBeFound(String filter) throws Exception {
        restAdministrativeDivisionMockMvc.perform(get("/api/administrative-divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrativeDivision.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].areaCode").value(hasItem(DEFAULT_AREA_CODE)))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].mergerName").value(hasItem(DEFAULT_MERGER_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())));

        // Check, that the count call also returns 1
        restAdministrativeDivisionMockMvc.perform(get("/api/administrative-divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdministrativeDivisionShouldNotBeFound(String filter) throws Exception {
        restAdministrativeDivisionMockMvc.perform(get("/api/administrative-divisions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdministrativeDivisionMockMvc.perform(get("/api/administrative-divisions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdministrativeDivision() throws Exception {
        // Get the administrativeDivision
        restAdministrativeDivisionMockMvc.perform(get("/api/administrative-divisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdministrativeDivision() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        int databaseSizeBeforeUpdate = administrativeDivisionRepository.findAll().size();

        // Update the administrativeDivision
        AdministrativeDivision updatedAdministrativeDivision = administrativeDivisionRepository.findById(administrativeDivision.getId()).get();
        // Disconnect from session so that the updates on updatedAdministrativeDivision are not directly saved in db
        em.detach(updatedAdministrativeDivision);
        updatedAdministrativeDivision
            .name(UPDATED_NAME)
            .areaCode(UPDATED_AREA_CODE)
            .cityCode(UPDATED_CITY_CODE)
            .mergerName(UPDATED_MERGER_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .zipCode(UPDATED_ZIP_CODE)
            .level(UPDATED_LEVEL)
            .lng(UPDATED_LNG)
            .lat(UPDATED_LAT);
        AdministrativeDivisionDTO administrativeDivisionDTO = administrativeDivisionMapper.toDto(updatedAdministrativeDivision);

        restAdministrativeDivisionMockMvc.perform(put("/api/administrative-divisions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(administrativeDivisionDTO)))
            .andExpect(status().isOk());

        // Validate the AdministrativeDivision in the database
        List<AdministrativeDivision> administrativeDivisionList = administrativeDivisionRepository.findAll();
        assertThat(administrativeDivisionList).hasSize(databaseSizeBeforeUpdate);
        AdministrativeDivision testAdministrativeDivision = administrativeDivisionList.get(administrativeDivisionList.size() - 1);
        assertThat(testAdministrativeDivision.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdministrativeDivision.getAreaCode()).isEqualTo(UPDATED_AREA_CODE);
        assertThat(testAdministrativeDivision.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testAdministrativeDivision.getMergerName()).isEqualTo(UPDATED_MERGER_NAME);
        assertThat(testAdministrativeDivision.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testAdministrativeDivision.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testAdministrativeDivision.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testAdministrativeDivision.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testAdministrativeDivision.getLat()).isEqualTo(UPDATED_LAT);
    }

    @Test
    @Transactional
    public void updateNonExistingAdministrativeDivision() throws Exception {
        int databaseSizeBeforeUpdate = administrativeDivisionRepository.findAll().size();

        // Create the AdministrativeDivision
        AdministrativeDivisionDTO administrativeDivisionDTO = administrativeDivisionMapper.toDto(administrativeDivision);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrativeDivisionMockMvc.perform(put("/api/administrative-divisions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(administrativeDivisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdministrativeDivision in the database
        List<AdministrativeDivision> administrativeDivisionList = administrativeDivisionRepository.findAll();
        assertThat(administrativeDivisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdministrativeDivision() throws Exception {
        // Initialize the database
        administrativeDivisionRepository.saveAndFlush(administrativeDivision);

        int databaseSizeBeforeDelete = administrativeDivisionRepository.findAll().size();

        // Delete the administrativeDivision
        restAdministrativeDivisionMockMvc.perform(delete("/api/administrative-divisions/{id}", administrativeDivision.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdministrativeDivision> administrativeDivisionList = administrativeDivisionRepository.findAll();
        assertThat(administrativeDivisionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
