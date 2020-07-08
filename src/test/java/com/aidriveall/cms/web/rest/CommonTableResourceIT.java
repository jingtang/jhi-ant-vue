package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.CommonTable;
import com.aidriveall.cms.domain.CommonTableField;
import com.aidriveall.cms.domain.CommonTableRelationship;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.domain.BusinessType;
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.service.CommonQueryQueryService;
import com.aidriveall.cms.service.CommonTableService;
import com.aidriveall.cms.service.dto.CommonTableDTO;
import com.aidriveall.cms.service.mapper.CommonTableMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.CommonTableQueryService;

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
 * Integration tests for the {@link CommonTableResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class CommonTableResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SYSTEM = false;
    private static final Boolean UPDATED_SYSTEM = true;

    private static final String DEFAULT_CLAZZ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLAZZ_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GENERATED = false;
    private static final Boolean UPDATED_GENERATED = true;

    private static final ZonedDateTime DEFAULT_CREAT_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREAT_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREAT_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_GENERATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GENERATE_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_GENERATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_GENERATE_CLASS_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_GENERATE_CLASS_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_GENERATE_CLASS_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TREE_TABLE = false;
    private static final Boolean UPDATED_TREE_TABLE = true;

    private static final Long DEFAULT_BASE_TABLE_ID = 1L;
    private static final Long UPDATED_BASE_TABLE_ID = 2L;
    private static final Long SMALLER_BASE_TABLE_ID = 1L - 1L;

    private static final Integer DEFAULT_RECORD_ACTION_WIDTH = 1;
    private static final Integer UPDATED_RECORD_ACTION_WIDTH = 2;
    private static final Integer SMALLER_RECORD_ACTION_WIDTH = 1 - 1;

    private static final String DEFAULT_LIST_CONFIG = "AAAAAAAAAA";
    private static final String UPDATED_LIST_CONFIG = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_CONFIG = "AAAAAAAAAA";
    private static final String UPDATED_FORM_CONFIG = "BBBBBBBBBB";

    @Autowired
    private CommonTableRepository commonTableRepository;

    @Autowired
    private CommonTableMapper commonTableMapper;

    @Autowired
    private CommonTableService commonTableService;

    @Autowired
    private CommonTableQueryService commonTableQueryService;

    @Autowired
    private CommonQueryQueryService commonQueryQueryService;

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

    private MockMvc restCommonTableMockMvc;

    private CommonTable commonTable;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommonTableResource commonTableResource = new CommonTableResource(commonTableService, commonTableQueryService, commonQueryQueryService);
        this.restCommonTableMockMvc = MockMvcBuilders.standaloneSetup(commonTableResource)
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
    public static CommonTable createEntity(EntityManager em) {
        CommonTable commonTable = new CommonTable()
            .name(DEFAULT_NAME)
            .entityName(DEFAULT_ENTITY_NAME)
            .tableName(DEFAULT_TABLE_NAME)
            .system(DEFAULT_SYSTEM)
            .clazzName(DEFAULT_CLAZZ_NAME)
            .generated(DEFAULT_GENERATED)
            .creatAt(DEFAULT_CREAT_AT)
            .generateAt(DEFAULT_GENERATE_AT)
            .generateClassAt(DEFAULT_GENERATE_CLASS_AT)
            .description(DEFAULT_DESCRIPTION)
            .treeTable(DEFAULT_TREE_TABLE)
            .baseTableId(DEFAULT_BASE_TABLE_ID)
            .recordActionWidth(DEFAULT_RECORD_ACTION_WIDTH)
            .listConfig(DEFAULT_LIST_CONFIG)
            .formConfig(DEFAULT_FORM_CONFIG);
        return commonTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommonTable createUpdatedEntity(EntityManager em) {
        CommonTable commonTable = new CommonTable()
            .name(UPDATED_NAME)
            .entityName(UPDATED_ENTITY_NAME)
            .tableName(UPDATED_TABLE_NAME)
            .system(UPDATED_SYSTEM)
            .clazzName(UPDATED_CLAZZ_NAME)
            .generated(UPDATED_GENERATED)
            .creatAt(UPDATED_CREAT_AT)
            .generateAt(UPDATED_GENERATE_AT)
            .generateClassAt(UPDATED_GENERATE_CLASS_AT)
            .description(UPDATED_DESCRIPTION)
            .treeTable(UPDATED_TREE_TABLE)
            .baseTableId(UPDATED_BASE_TABLE_ID)
            .recordActionWidth(UPDATED_RECORD_ACTION_WIDTH)
            .listConfig(UPDATED_LIST_CONFIG)
            .formConfig(UPDATED_FORM_CONFIG);
        return commonTable;
    }

    @BeforeEach
    public void initTest() {
        commonTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommonTable() throws Exception {
        int databaseSizeBeforeCreate = commonTableRepository.findAll().size();

        // Create the CommonTable
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(commonTable);
        restCommonTableMockMvc.perform(post("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isCreated());

        // Validate the CommonTable in the database
        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeCreate + 1);
        CommonTable testCommonTable = commonTableList.get(commonTableList.size() - 1);
        assertThat(testCommonTable.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommonTable.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testCommonTable.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testCommonTable.isSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testCommonTable.getClazzName()).isEqualTo(DEFAULT_CLAZZ_NAME);
        assertThat(testCommonTable.isGenerated()).isEqualTo(DEFAULT_GENERATED);
        assertThat(testCommonTable.getCreatAt()).isEqualTo(DEFAULT_CREAT_AT);
        assertThat(testCommonTable.getGenerateAt()).isEqualTo(DEFAULT_GENERATE_AT);
        assertThat(testCommonTable.getGenerateClassAt()).isEqualTo(DEFAULT_GENERATE_CLASS_AT);
        assertThat(testCommonTable.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommonTable.isTreeTable()).isEqualTo(DEFAULT_TREE_TABLE);
        assertThat(testCommonTable.getBaseTableId()).isEqualTo(DEFAULT_BASE_TABLE_ID);
        assertThat(testCommonTable.getRecordActionWidth()).isEqualTo(DEFAULT_RECORD_ACTION_WIDTH);
        assertThat(testCommonTable.getListConfig()).isEqualTo(DEFAULT_LIST_CONFIG);
        assertThat(testCommonTable.getFormConfig()).isEqualTo(DEFAULT_FORM_CONFIG);
    }

    @Test
    @Transactional
    public void createCommonTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commonTableRepository.findAll().size();

        // Create the CommonTable with an existing ID
        commonTable.setId(1L);
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(commonTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonTableMockMvc.perform(post("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommonTable in the database
        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commonTableRepository.findAll().size();
        // set the field null
        commonTable.setName(null);

        // Create the CommonTable, which fails.
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(commonTable);

        restCommonTableMockMvc.perform(post("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isBadRequest());

        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEntityNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commonTableRepository.findAll().size();
        // set the field null
        commonTable.setEntityName(null);

        // Create the CommonTable, which fails.
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(commonTable);

        restCommonTableMockMvc.perform(post("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isBadRequest());

        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTableNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commonTableRepository.findAll().size();
        // set the field null
        commonTable.setTableName(null);

        // Create the CommonTable, which fails.
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(commonTable);

        restCommonTableMockMvc.perform(post("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isBadRequest());

        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClazzNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commonTableRepository.findAll().size();
        // set the field null
        commonTable.setClazzName(null);

        // Create the CommonTable, which fails.
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(commonTable);

        restCommonTableMockMvc.perform(post("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isBadRequest());

        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommonTables() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList
        restCommonTableMockMvc.perform(get("/api/common-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.booleanValue())))
            .andExpect(jsonPath("$.[*].clazzName").value(hasItem(DEFAULT_CLAZZ_NAME)))
            .andExpect(jsonPath("$.[*].generated").value(hasItem(DEFAULT_GENERATED.booleanValue())))
            .andExpect(jsonPath("$.[*].creatAt").value(hasItem(sameInstant(DEFAULT_CREAT_AT))))
            .andExpect(jsonPath("$.[*].generateAt").value(hasItem(sameInstant(DEFAULT_GENERATE_AT))))
            .andExpect(jsonPath("$.[*].generateClassAt").value(hasItem(sameInstant(DEFAULT_GENERATE_CLASS_AT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].treeTable").value(hasItem(DEFAULT_TREE_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].baseTableId").value(hasItem(DEFAULT_BASE_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].recordActionWidth").value(hasItem(DEFAULT_RECORD_ACTION_WIDTH)))
            .andExpect(jsonPath("$.[*].listConfig").value(hasItem(DEFAULT_LIST_CONFIG.toString())))
            .andExpect(jsonPath("$.[*].formConfig").value(hasItem(DEFAULT_FORM_CONFIG.toString())));
    }

    @Test
    @Transactional
    public void getCommonTable() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get the commonTable
        restCommonTableMockMvc.perform(get("/api/common-tables/{id}", commonTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commonTable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM.booleanValue()))
            .andExpect(jsonPath("$.clazzName").value(DEFAULT_CLAZZ_NAME))
            .andExpect(jsonPath("$.generated").value(DEFAULT_GENERATED.booleanValue()))
            .andExpect(jsonPath("$.creatAt").value(sameInstant(DEFAULT_CREAT_AT)))
            .andExpect(jsonPath("$.generateAt").value(sameInstant(DEFAULT_GENERATE_AT)))
            .andExpect(jsonPath("$.generateClassAt").value(sameInstant(DEFAULT_GENERATE_CLASS_AT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.treeTable").value(DEFAULT_TREE_TABLE.booleanValue()))
            .andExpect(jsonPath("$.baseTableId").value(DEFAULT_BASE_TABLE_ID.intValue()))
            .andExpect(jsonPath("$.recordActionWidth").value(DEFAULT_RECORD_ACTION_WIDTH))
            .andExpect(jsonPath("$.listConfig").value(DEFAULT_LIST_CONFIG.toString()))
            .andExpect(jsonPath("$.formConfig").value(DEFAULT_FORM_CONFIG.toString()));
    }


    @Test
    @Transactional
    public void getCommonTablesByIdFiltering() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        Long id = commonTable.getId();

        defaultCommonTableShouldBeFound("id.equals=" + id);
        defaultCommonTableShouldNotBeFound("id.notEquals=" + id);

        defaultCommonTableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommonTableShouldNotBeFound("id.greaterThan=" + id);

        defaultCommonTableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommonTableShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where name equals to DEFAULT_NAME
        defaultCommonTableShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the commonTableList where name equals to UPDATED_NAME
        defaultCommonTableShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where name not equals to DEFAULT_NAME
        defaultCommonTableShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the commonTableList where name not equals to UPDATED_NAME
        defaultCommonTableShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCommonTableShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the commonTableList where name equals to UPDATED_NAME
        defaultCommonTableShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where name is not null
        defaultCommonTableShouldBeFound("name.specified=true");

        // Get all the commonTableList where name is null
        defaultCommonTableShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonTablesByNameContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where name contains DEFAULT_NAME
        defaultCommonTableShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the commonTableList where name contains UPDATED_NAME
        defaultCommonTableShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where name does not contain DEFAULT_NAME
        defaultCommonTableShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the commonTableList where name does not contain UPDATED_NAME
        defaultCommonTableShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where entityName equals to DEFAULT_ENTITY_NAME
        defaultCommonTableShouldBeFound("entityName.equals=" + DEFAULT_ENTITY_NAME);

        // Get all the commonTableList where entityName equals to UPDATED_ENTITY_NAME
        defaultCommonTableShouldNotBeFound("entityName.equals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByEntityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where entityName not equals to DEFAULT_ENTITY_NAME
        defaultCommonTableShouldNotBeFound("entityName.notEquals=" + DEFAULT_ENTITY_NAME);

        // Get all the commonTableList where entityName not equals to UPDATED_ENTITY_NAME
        defaultCommonTableShouldBeFound("entityName.notEquals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where entityName in DEFAULT_ENTITY_NAME or UPDATED_ENTITY_NAME
        defaultCommonTableShouldBeFound("entityName.in=" + DEFAULT_ENTITY_NAME + "," + UPDATED_ENTITY_NAME);

        // Get all the commonTableList where entityName equals to UPDATED_ENTITY_NAME
        defaultCommonTableShouldNotBeFound("entityName.in=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where entityName is not null
        defaultCommonTableShouldBeFound("entityName.specified=true");

        // Get all the commonTableList where entityName is null
        defaultCommonTableShouldNotBeFound("entityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonTablesByEntityNameContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where entityName contains DEFAULT_ENTITY_NAME
        defaultCommonTableShouldBeFound("entityName.contains=" + DEFAULT_ENTITY_NAME);

        // Get all the commonTableList where entityName contains UPDATED_ENTITY_NAME
        defaultCommonTableShouldNotBeFound("entityName.contains=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByEntityNameNotContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where entityName does not contain DEFAULT_ENTITY_NAME
        defaultCommonTableShouldNotBeFound("entityName.doesNotContain=" + DEFAULT_ENTITY_NAME);

        // Get all the commonTableList where entityName does not contain UPDATED_ENTITY_NAME
        defaultCommonTableShouldBeFound("entityName.doesNotContain=" + UPDATED_ENTITY_NAME);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByTableNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where tableName equals to DEFAULT_TABLE_NAME
        defaultCommonTableShouldBeFound("tableName.equals=" + DEFAULT_TABLE_NAME);

        // Get all the commonTableList where tableName equals to UPDATED_TABLE_NAME
        defaultCommonTableShouldNotBeFound("tableName.equals=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByTableNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where tableName not equals to DEFAULT_TABLE_NAME
        defaultCommonTableShouldNotBeFound("tableName.notEquals=" + DEFAULT_TABLE_NAME);

        // Get all the commonTableList where tableName not equals to UPDATED_TABLE_NAME
        defaultCommonTableShouldBeFound("tableName.notEquals=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByTableNameIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where tableName in DEFAULT_TABLE_NAME or UPDATED_TABLE_NAME
        defaultCommonTableShouldBeFound("tableName.in=" + DEFAULT_TABLE_NAME + "," + UPDATED_TABLE_NAME);

        // Get all the commonTableList where tableName equals to UPDATED_TABLE_NAME
        defaultCommonTableShouldNotBeFound("tableName.in=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByTableNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where tableName is not null
        defaultCommonTableShouldBeFound("tableName.specified=true");

        // Get all the commonTableList where tableName is null
        defaultCommonTableShouldNotBeFound("tableName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonTablesByTableNameContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where tableName contains DEFAULT_TABLE_NAME
        defaultCommonTableShouldBeFound("tableName.contains=" + DEFAULT_TABLE_NAME);

        // Get all the commonTableList where tableName contains UPDATED_TABLE_NAME
        defaultCommonTableShouldNotBeFound("tableName.contains=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByTableNameNotContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where tableName does not contain DEFAULT_TABLE_NAME
        defaultCommonTableShouldNotBeFound("tableName.doesNotContain=" + DEFAULT_TABLE_NAME);

        // Get all the commonTableList where tableName does not contain UPDATED_TABLE_NAME
        defaultCommonTableShouldBeFound("tableName.doesNotContain=" + UPDATED_TABLE_NAME);
    }


    @Test
    @Transactional
    public void getAllCommonTablesBySystemIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where system equals to DEFAULT_SYSTEM
        defaultCommonTableShouldBeFound("system.equals=" + DEFAULT_SYSTEM);

        // Get all the commonTableList where system equals to UPDATED_SYSTEM
        defaultCommonTableShouldNotBeFound("system.equals=" + UPDATED_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllCommonTablesBySystemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where system not equals to DEFAULT_SYSTEM
        defaultCommonTableShouldNotBeFound("system.notEquals=" + DEFAULT_SYSTEM);

        // Get all the commonTableList where system not equals to UPDATED_SYSTEM
        defaultCommonTableShouldBeFound("system.notEquals=" + UPDATED_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllCommonTablesBySystemIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where system in DEFAULT_SYSTEM or UPDATED_SYSTEM
        defaultCommonTableShouldBeFound("system.in=" + DEFAULT_SYSTEM + "," + UPDATED_SYSTEM);

        // Get all the commonTableList where system equals to UPDATED_SYSTEM
        defaultCommonTableShouldNotBeFound("system.in=" + UPDATED_SYSTEM);
    }

    @Test
    @Transactional
    public void getAllCommonTablesBySystemIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where system is not null
        defaultCommonTableShouldBeFound("system.specified=true");

        // Get all the commonTableList where system is null
        defaultCommonTableShouldNotBeFound("system.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByClazzNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where clazzName equals to DEFAULT_CLAZZ_NAME
        defaultCommonTableShouldBeFound("clazzName.equals=" + DEFAULT_CLAZZ_NAME);

        // Get all the commonTableList where clazzName equals to UPDATED_CLAZZ_NAME
        defaultCommonTableShouldNotBeFound("clazzName.equals=" + UPDATED_CLAZZ_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByClazzNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where clazzName not equals to DEFAULT_CLAZZ_NAME
        defaultCommonTableShouldNotBeFound("clazzName.notEquals=" + DEFAULT_CLAZZ_NAME);

        // Get all the commonTableList where clazzName not equals to UPDATED_CLAZZ_NAME
        defaultCommonTableShouldBeFound("clazzName.notEquals=" + UPDATED_CLAZZ_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByClazzNameIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where clazzName in DEFAULT_CLAZZ_NAME or UPDATED_CLAZZ_NAME
        defaultCommonTableShouldBeFound("clazzName.in=" + DEFAULT_CLAZZ_NAME + "," + UPDATED_CLAZZ_NAME);

        // Get all the commonTableList where clazzName equals to UPDATED_CLAZZ_NAME
        defaultCommonTableShouldNotBeFound("clazzName.in=" + UPDATED_CLAZZ_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByClazzNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where clazzName is not null
        defaultCommonTableShouldBeFound("clazzName.specified=true");

        // Get all the commonTableList where clazzName is null
        defaultCommonTableShouldNotBeFound("clazzName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonTablesByClazzNameContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where clazzName contains DEFAULT_CLAZZ_NAME
        defaultCommonTableShouldBeFound("clazzName.contains=" + DEFAULT_CLAZZ_NAME);

        // Get all the commonTableList where clazzName contains UPDATED_CLAZZ_NAME
        defaultCommonTableShouldNotBeFound("clazzName.contains=" + UPDATED_CLAZZ_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByClazzNameNotContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where clazzName does not contain DEFAULT_CLAZZ_NAME
        defaultCommonTableShouldNotBeFound("clazzName.doesNotContain=" + DEFAULT_CLAZZ_NAME);

        // Get all the commonTableList where clazzName does not contain UPDATED_CLAZZ_NAME
        defaultCommonTableShouldBeFound("clazzName.doesNotContain=" + UPDATED_CLAZZ_NAME);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByGeneratedIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generated equals to DEFAULT_GENERATED
        defaultCommonTableShouldBeFound("generated.equals=" + DEFAULT_GENERATED);

        // Get all the commonTableList where generated equals to UPDATED_GENERATED
        defaultCommonTableShouldNotBeFound("generated.equals=" + UPDATED_GENERATED);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGeneratedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generated not equals to DEFAULT_GENERATED
        defaultCommonTableShouldNotBeFound("generated.notEquals=" + DEFAULT_GENERATED);

        // Get all the commonTableList where generated not equals to UPDATED_GENERATED
        defaultCommonTableShouldBeFound("generated.notEquals=" + UPDATED_GENERATED);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGeneratedIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generated in DEFAULT_GENERATED or UPDATED_GENERATED
        defaultCommonTableShouldBeFound("generated.in=" + DEFAULT_GENERATED + "," + UPDATED_GENERATED);

        // Get all the commonTableList where generated equals to UPDATED_GENERATED
        defaultCommonTableShouldNotBeFound("generated.in=" + UPDATED_GENERATED);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGeneratedIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generated is not null
        defaultCommonTableShouldBeFound("generated.specified=true");

        // Get all the commonTableList where generated is null
        defaultCommonTableShouldNotBeFound("generated.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt equals to DEFAULT_CREAT_AT
        defaultCommonTableShouldBeFound("creatAt.equals=" + DEFAULT_CREAT_AT);

        // Get all the commonTableList where creatAt equals to UPDATED_CREAT_AT
        defaultCommonTableShouldNotBeFound("creatAt.equals=" + UPDATED_CREAT_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt not equals to DEFAULT_CREAT_AT
        defaultCommonTableShouldNotBeFound("creatAt.notEquals=" + DEFAULT_CREAT_AT);

        // Get all the commonTableList where creatAt not equals to UPDATED_CREAT_AT
        defaultCommonTableShouldBeFound("creatAt.notEquals=" + UPDATED_CREAT_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt in DEFAULT_CREAT_AT or UPDATED_CREAT_AT
        defaultCommonTableShouldBeFound("creatAt.in=" + DEFAULT_CREAT_AT + "," + UPDATED_CREAT_AT);

        // Get all the commonTableList where creatAt equals to UPDATED_CREAT_AT
        defaultCommonTableShouldNotBeFound("creatAt.in=" + UPDATED_CREAT_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt is not null
        defaultCommonTableShouldBeFound("creatAt.specified=true");

        // Get all the commonTableList where creatAt is null
        defaultCommonTableShouldNotBeFound("creatAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt is greater than or equal to DEFAULT_CREAT_AT
        defaultCommonTableShouldBeFound("creatAt.greaterThanOrEqual=" + DEFAULT_CREAT_AT);

        // Get all the commonTableList where creatAt is greater than or equal to UPDATED_CREAT_AT
        defaultCommonTableShouldNotBeFound("creatAt.greaterThanOrEqual=" + UPDATED_CREAT_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt is less than or equal to DEFAULT_CREAT_AT
        defaultCommonTableShouldBeFound("creatAt.lessThanOrEqual=" + DEFAULT_CREAT_AT);

        // Get all the commonTableList where creatAt is less than or equal to SMALLER_CREAT_AT
        defaultCommonTableShouldNotBeFound("creatAt.lessThanOrEqual=" + SMALLER_CREAT_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsLessThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt is less than DEFAULT_CREAT_AT
        defaultCommonTableShouldNotBeFound("creatAt.lessThan=" + DEFAULT_CREAT_AT);

        // Get all the commonTableList where creatAt is less than UPDATED_CREAT_AT
        defaultCommonTableShouldBeFound("creatAt.lessThan=" + UPDATED_CREAT_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByCreatAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where creatAt is greater than DEFAULT_CREAT_AT
        defaultCommonTableShouldNotBeFound("creatAt.greaterThan=" + DEFAULT_CREAT_AT);

        // Get all the commonTableList where creatAt is greater than SMALLER_CREAT_AT
        defaultCommonTableShouldBeFound("creatAt.greaterThan=" + SMALLER_CREAT_AT);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt equals to DEFAULT_GENERATE_AT
        defaultCommonTableShouldBeFound("generateAt.equals=" + DEFAULT_GENERATE_AT);

        // Get all the commonTableList where generateAt equals to UPDATED_GENERATE_AT
        defaultCommonTableShouldNotBeFound("generateAt.equals=" + UPDATED_GENERATE_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt not equals to DEFAULT_GENERATE_AT
        defaultCommonTableShouldNotBeFound("generateAt.notEquals=" + DEFAULT_GENERATE_AT);

        // Get all the commonTableList where generateAt not equals to UPDATED_GENERATE_AT
        defaultCommonTableShouldBeFound("generateAt.notEquals=" + UPDATED_GENERATE_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt in DEFAULT_GENERATE_AT or UPDATED_GENERATE_AT
        defaultCommonTableShouldBeFound("generateAt.in=" + DEFAULT_GENERATE_AT + "," + UPDATED_GENERATE_AT);

        // Get all the commonTableList where generateAt equals to UPDATED_GENERATE_AT
        defaultCommonTableShouldNotBeFound("generateAt.in=" + UPDATED_GENERATE_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt is not null
        defaultCommonTableShouldBeFound("generateAt.specified=true");

        // Get all the commonTableList where generateAt is null
        defaultCommonTableShouldNotBeFound("generateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt is greater than or equal to DEFAULT_GENERATE_AT
        defaultCommonTableShouldBeFound("generateAt.greaterThanOrEqual=" + DEFAULT_GENERATE_AT);

        // Get all the commonTableList where generateAt is greater than or equal to UPDATED_GENERATE_AT
        defaultCommonTableShouldNotBeFound("generateAt.greaterThanOrEqual=" + UPDATED_GENERATE_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt is less than or equal to DEFAULT_GENERATE_AT
        defaultCommonTableShouldBeFound("generateAt.lessThanOrEqual=" + DEFAULT_GENERATE_AT);

        // Get all the commonTableList where generateAt is less than or equal to SMALLER_GENERATE_AT
        defaultCommonTableShouldNotBeFound("generateAt.lessThanOrEqual=" + SMALLER_GENERATE_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt is less than DEFAULT_GENERATE_AT
        defaultCommonTableShouldNotBeFound("generateAt.lessThan=" + DEFAULT_GENERATE_AT);

        // Get all the commonTableList where generateAt is less than UPDATED_GENERATE_AT
        defaultCommonTableShouldBeFound("generateAt.lessThan=" + UPDATED_GENERATE_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateAt is greater than DEFAULT_GENERATE_AT
        defaultCommonTableShouldNotBeFound("generateAt.greaterThan=" + DEFAULT_GENERATE_AT);

        // Get all the commonTableList where generateAt is greater than SMALLER_GENERATE_AT
        defaultCommonTableShouldBeFound("generateAt.greaterThan=" + SMALLER_GENERATE_AT);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt equals to DEFAULT_GENERATE_CLASS_AT
        defaultCommonTableShouldBeFound("generateClassAt.equals=" + DEFAULT_GENERATE_CLASS_AT);

        // Get all the commonTableList where generateClassAt equals to UPDATED_GENERATE_CLASS_AT
        defaultCommonTableShouldNotBeFound("generateClassAt.equals=" + UPDATED_GENERATE_CLASS_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt not equals to DEFAULT_GENERATE_CLASS_AT
        defaultCommonTableShouldNotBeFound("generateClassAt.notEquals=" + DEFAULT_GENERATE_CLASS_AT);

        // Get all the commonTableList where generateClassAt not equals to UPDATED_GENERATE_CLASS_AT
        defaultCommonTableShouldBeFound("generateClassAt.notEquals=" + UPDATED_GENERATE_CLASS_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt in DEFAULT_GENERATE_CLASS_AT or UPDATED_GENERATE_CLASS_AT
        defaultCommonTableShouldBeFound("generateClassAt.in=" + DEFAULT_GENERATE_CLASS_AT + "," + UPDATED_GENERATE_CLASS_AT);

        // Get all the commonTableList where generateClassAt equals to UPDATED_GENERATE_CLASS_AT
        defaultCommonTableShouldNotBeFound("generateClassAt.in=" + UPDATED_GENERATE_CLASS_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt is not null
        defaultCommonTableShouldBeFound("generateClassAt.specified=true");

        // Get all the commonTableList where generateClassAt is null
        defaultCommonTableShouldNotBeFound("generateClassAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt is greater than or equal to DEFAULT_GENERATE_CLASS_AT
        defaultCommonTableShouldBeFound("generateClassAt.greaterThanOrEqual=" + DEFAULT_GENERATE_CLASS_AT);

        // Get all the commonTableList where generateClassAt is greater than or equal to UPDATED_GENERATE_CLASS_AT
        defaultCommonTableShouldNotBeFound("generateClassAt.greaterThanOrEqual=" + UPDATED_GENERATE_CLASS_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt is less than or equal to DEFAULT_GENERATE_CLASS_AT
        defaultCommonTableShouldBeFound("generateClassAt.lessThanOrEqual=" + DEFAULT_GENERATE_CLASS_AT);

        // Get all the commonTableList where generateClassAt is less than or equal to SMALLER_GENERATE_CLASS_AT
        defaultCommonTableShouldNotBeFound("generateClassAt.lessThanOrEqual=" + SMALLER_GENERATE_CLASS_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsLessThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt is less than DEFAULT_GENERATE_CLASS_AT
        defaultCommonTableShouldNotBeFound("generateClassAt.lessThan=" + DEFAULT_GENERATE_CLASS_AT);

        // Get all the commonTableList where generateClassAt is less than UPDATED_GENERATE_CLASS_AT
        defaultCommonTableShouldBeFound("generateClassAt.lessThan=" + UPDATED_GENERATE_CLASS_AT);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByGenerateClassAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where generateClassAt is greater than DEFAULT_GENERATE_CLASS_AT
        defaultCommonTableShouldNotBeFound("generateClassAt.greaterThan=" + DEFAULT_GENERATE_CLASS_AT);

        // Get all the commonTableList where generateClassAt is greater than SMALLER_GENERATE_CLASS_AT
        defaultCommonTableShouldBeFound("generateClassAt.greaterThan=" + SMALLER_GENERATE_CLASS_AT);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where description equals to DEFAULT_DESCRIPTION
        defaultCommonTableShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the commonTableList where description equals to UPDATED_DESCRIPTION
        defaultCommonTableShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where description not equals to DEFAULT_DESCRIPTION
        defaultCommonTableShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the commonTableList where description not equals to UPDATED_DESCRIPTION
        defaultCommonTableShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCommonTableShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the commonTableList where description equals to UPDATED_DESCRIPTION
        defaultCommonTableShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where description is not null
        defaultCommonTableShouldBeFound("description.specified=true");

        // Get all the commonTableList where description is null
        defaultCommonTableShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonTablesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where description contains DEFAULT_DESCRIPTION
        defaultCommonTableShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the commonTableList where description contains UPDATED_DESCRIPTION
        defaultCommonTableShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where description does not contain DEFAULT_DESCRIPTION
        defaultCommonTableShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the commonTableList where description does not contain UPDATED_DESCRIPTION
        defaultCommonTableShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByTreeTableIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where treeTable equals to DEFAULT_TREE_TABLE
        defaultCommonTableShouldBeFound("treeTable.equals=" + DEFAULT_TREE_TABLE);

        // Get all the commonTableList where treeTable equals to UPDATED_TREE_TABLE
        defaultCommonTableShouldNotBeFound("treeTable.equals=" + UPDATED_TREE_TABLE);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByTreeTableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where treeTable not equals to DEFAULT_TREE_TABLE
        defaultCommonTableShouldNotBeFound("treeTable.notEquals=" + DEFAULT_TREE_TABLE);

        // Get all the commonTableList where treeTable not equals to UPDATED_TREE_TABLE
        defaultCommonTableShouldBeFound("treeTable.notEquals=" + UPDATED_TREE_TABLE);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByTreeTableIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where treeTable in DEFAULT_TREE_TABLE or UPDATED_TREE_TABLE
        defaultCommonTableShouldBeFound("treeTable.in=" + DEFAULT_TREE_TABLE + "," + UPDATED_TREE_TABLE);

        // Get all the commonTableList where treeTable equals to UPDATED_TREE_TABLE
        defaultCommonTableShouldNotBeFound("treeTable.in=" + UPDATED_TREE_TABLE);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByTreeTableIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where treeTable is not null
        defaultCommonTableShouldBeFound("treeTable.specified=true");

        // Get all the commonTableList where treeTable is null
        defaultCommonTableShouldNotBeFound("treeTable.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId equals to DEFAULT_BASE_TABLE_ID
        defaultCommonTableShouldBeFound("baseTableId.equals=" + DEFAULT_BASE_TABLE_ID);

        // Get all the commonTableList where baseTableId equals to UPDATED_BASE_TABLE_ID
        defaultCommonTableShouldNotBeFound("baseTableId.equals=" + UPDATED_BASE_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId not equals to DEFAULT_BASE_TABLE_ID
        defaultCommonTableShouldNotBeFound("baseTableId.notEquals=" + DEFAULT_BASE_TABLE_ID);

        // Get all the commonTableList where baseTableId not equals to UPDATED_BASE_TABLE_ID
        defaultCommonTableShouldBeFound("baseTableId.notEquals=" + UPDATED_BASE_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId in DEFAULT_BASE_TABLE_ID or UPDATED_BASE_TABLE_ID
        defaultCommonTableShouldBeFound("baseTableId.in=" + DEFAULT_BASE_TABLE_ID + "," + UPDATED_BASE_TABLE_ID);

        // Get all the commonTableList where baseTableId equals to UPDATED_BASE_TABLE_ID
        defaultCommonTableShouldNotBeFound("baseTableId.in=" + UPDATED_BASE_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId is not null
        defaultCommonTableShouldBeFound("baseTableId.specified=true");

        // Get all the commonTableList where baseTableId is null
        defaultCommonTableShouldNotBeFound("baseTableId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId is greater than or equal to DEFAULT_BASE_TABLE_ID
        defaultCommonTableShouldBeFound("baseTableId.greaterThanOrEqual=" + DEFAULT_BASE_TABLE_ID);

        // Get all the commonTableList where baseTableId is greater than or equal to UPDATED_BASE_TABLE_ID
        defaultCommonTableShouldNotBeFound("baseTableId.greaterThanOrEqual=" + UPDATED_BASE_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId is less than or equal to DEFAULT_BASE_TABLE_ID
        defaultCommonTableShouldBeFound("baseTableId.lessThanOrEqual=" + DEFAULT_BASE_TABLE_ID);

        // Get all the commonTableList where baseTableId is less than or equal to SMALLER_BASE_TABLE_ID
        defaultCommonTableShouldNotBeFound("baseTableId.lessThanOrEqual=" + SMALLER_BASE_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsLessThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId is less than DEFAULT_BASE_TABLE_ID
        defaultCommonTableShouldNotBeFound("baseTableId.lessThan=" + DEFAULT_BASE_TABLE_ID);

        // Get all the commonTableList where baseTableId is less than UPDATED_BASE_TABLE_ID
        defaultCommonTableShouldBeFound("baseTableId.lessThan=" + UPDATED_BASE_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByBaseTableIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where baseTableId is greater than DEFAULT_BASE_TABLE_ID
        defaultCommonTableShouldNotBeFound("baseTableId.greaterThan=" + DEFAULT_BASE_TABLE_ID);

        // Get all the commonTableList where baseTableId is greater than SMALLER_BASE_TABLE_ID
        defaultCommonTableShouldBeFound("baseTableId.greaterThan=" + SMALLER_BASE_TABLE_ID);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth equals to DEFAULT_RECORD_ACTION_WIDTH
        defaultCommonTableShouldBeFound("recordActionWidth.equals=" + DEFAULT_RECORD_ACTION_WIDTH);

        // Get all the commonTableList where recordActionWidth equals to UPDATED_RECORD_ACTION_WIDTH
        defaultCommonTableShouldNotBeFound("recordActionWidth.equals=" + UPDATED_RECORD_ACTION_WIDTH);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth not equals to DEFAULT_RECORD_ACTION_WIDTH
        defaultCommonTableShouldNotBeFound("recordActionWidth.notEquals=" + DEFAULT_RECORD_ACTION_WIDTH);

        // Get all the commonTableList where recordActionWidth not equals to UPDATED_RECORD_ACTION_WIDTH
        defaultCommonTableShouldBeFound("recordActionWidth.notEquals=" + UPDATED_RECORD_ACTION_WIDTH);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsInShouldWork() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth in DEFAULT_RECORD_ACTION_WIDTH or UPDATED_RECORD_ACTION_WIDTH
        defaultCommonTableShouldBeFound("recordActionWidth.in=" + DEFAULT_RECORD_ACTION_WIDTH + "," + UPDATED_RECORD_ACTION_WIDTH);

        // Get all the commonTableList where recordActionWidth equals to UPDATED_RECORD_ACTION_WIDTH
        defaultCommonTableShouldNotBeFound("recordActionWidth.in=" + UPDATED_RECORD_ACTION_WIDTH);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth is not null
        defaultCommonTableShouldBeFound("recordActionWidth.specified=true");

        // Get all the commonTableList where recordActionWidth is null
        defaultCommonTableShouldNotBeFound("recordActionWidth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth is greater than or equal to DEFAULT_RECORD_ACTION_WIDTH
        defaultCommonTableShouldBeFound("recordActionWidth.greaterThanOrEqual=" + DEFAULT_RECORD_ACTION_WIDTH);

        // Get all the commonTableList where recordActionWidth is greater than or equal to UPDATED_RECORD_ACTION_WIDTH
        defaultCommonTableShouldNotBeFound("recordActionWidth.greaterThanOrEqual=" + UPDATED_RECORD_ACTION_WIDTH);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth is less than or equal to DEFAULT_RECORD_ACTION_WIDTH
        defaultCommonTableShouldBeFound("recordActionWidth.lessThanOrEqual=" + DEFAULT_RECORD_ACTION_WIDTH);

        // Get all the commonTableList where recordActionWidth is less than or equal to SMALLER_RECORD_ACTION_WIDTH
        defaultCommonTableShouldNotBeFound("recordActionWidth.lessThanOrEqual=" + SMALLER_RECORD_ACTION_WIDTH);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth is less than DEFAULT_RECORD_ACTION_WIDTH
        defaultCommonTableShouldNotBeFound("recordActionWidth.lessThan=" + DEFAULT_RECORD_ACTION_WIDTH);

        // Get all the commonTableList where recordActionWidth is less than UPDATED_RECORD_ACTION_WIDTH
        defaultCommonTableShouldBeFound("recordActionWidth.lessThan=" + UPDATED_RECORD_ACTION_WIDTH);
    }

    @Test
    @Transactional
    public void getAllCommonTablesByRecordActionWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        // Get all the commonTableList where recordActionWidth is greater than DEFAULT_RECORD_ACTION_WIDTH
        defaultCommonTableShouldNotBeFound("recordActionWidth.greaterThan=" + DEFAULT_RECORD_ACTION_WIDTH);

        // Get all the commonTableList where recordActionWidth is greater than SMALLER_RECORD_ACTION_WIDTH
        defaultCommonTableShouldBeFound("recordActionWidth.greaterThan=" + SMALLER_RECORD_ACTION_WIDTH);
    }


    @Test
    @Transactional
    public void getAllCommonTablesByCommonTableFieldsIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);
        CommonTableField commonTableFields = CommonTableFieldResourceIT.createEntity(em);
        em.persist(commonTableFields);
        em.flush();
        commonTable.addCommonTableFields(commonTableFields);
        commonTableRepository.saveAndFlush(commonTable);
        Long commonTableFieldsId = commonTableFields.getId();

        // Get all the commonTableList where commonTableFields equals to commonTableFieldsId
        defaultCommonTableShouldBeFound("commonTableFieldsId.equals=" + commonTableFieldsId);

        // Get all the commonTableList where commonTableFields equals to commonTableFieldsId + 1
        defaultCommonTableShouldNotBeFound("commonTableFieldsId.equals=" + (commonTableFieldsId + 1));
    }


    @Test
    @Transactional
    public void getAllCommonTablesByRelationshipsIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);
        CommonTableRelationship relationships = CommonTableRelationshipResourceIT.createEntity(em);
        em.persist(relationships);
        em.flush();
        commonTable.addRelationships(relationships);
        commonTableRepository.saveAndFlush(commonTable);
        Long relationshipsId = relationships.getId();

        // Get all the commonTableList where relationships equals to relationshipsId
        defaultCommonTableShouldBeFound("relationshipsId.equals=" + relationshipsId);

        // Get all the commonTableList where relationships equals to relationshipsId + 1
        defaultCommonTableShouldNotBeFound("relationshipsId.equals=" + (relationshipsId + 1));
    }


    @Test
    @Transactional
    public void getAllCommonTablesByCreatorIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);
        User creator = UserResourceIT.createEntity(em);
        em.persist(creator);
        em.flush();
        commonTable.setCreator(creator);
        commonTableRepository.saveAndFlush(commonTable);
        Long creatorId = creator.getId();

        // Get all the commonTableList where creator equals to creatorId
        defaultCommonTableShouldBeFound("creatorId.equals=" + creatorId);

        // Get all the commonTableList where creator equals to creatorId + 1
        defaultCommonTableShouldNotBeFound("creatorId.equals=" + (creatorId + 1));
    }


    @Test
    @Transactional
    public void getAllCommonTablesByBusinessTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);
        BusinessType businessType = BusinessTypeResourceIT.createEntity(em);
        em.persist(businessType);
        em.flush();
        commonTable.setBusinessType(businessType);
        commonTableRepository.saveAndFlush(commonTable);
        Long businessTypeId = businessType.getId();

        // Get all the commonTableList where businessType equals to businessTypeId
        defaultCommonTableShouldBeFound("businessTypeId.equals=" + businessTypeId);

        // Get all the commonTableList where businessType equals to businessTypeId + 1
        defaultCommonTableShouldNotBeFound("businessTypeId.equals=" + (businessTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommonTableShouldBeFound(String filter) throws Exception {
        restCommonTableMockMvc.perform(get("/api/common-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM.booleanValue())))
            .andExpect(jsonPath("$.[*].clazzName").value(hasItem(DEFAULT_CLAZZ_NAME)))
            .andExpect(jsonPath("$.[*].generated").value(hasItem(DEFAULT_GENERATED.booleanValue())))
            .andExpect(jsonPath("$.[*].creatAt").value(hasItem(sameInstant(DEFAULT_CREAT_AT))))
            .andExpect(jsonPath("$.[*].generateAt").value(hasItem(sameInstant(DEFAULT_GENERATE_AT))))
            .andExpect(jsonPath("$.[*].generateClassAt").value(hasItem(sameInstant(DEFAULT_GENERATE_CLASS_AT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].treeTable").value(hasItem(DEFAULT_TREE_TABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].baseTableId").value(hasItem(DEFAULT_BASE_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].recordActionWidth").value(hasItem(DEFAULT_RECORD_ACTION_WIDTH)))
            .andExpect(jsonPath("$.[*].listConfig").value(hasItem(DEFAULT_LIST_CONFIG.toString())))
            .andExpect(jsonPath("$.[*].formConfig").value(hasItem(DEFAULT_FORM_CONFIG.toString())));

        // Check, that the count call also returns 1
        restCommonTableMockMvc.perform(get("/api/common-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommonTableShouldNotBeFound(String filter) throws Exception {
        restCommonTableMockMvc.perform(get("/api/common-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommonTableMockMvc.perform(get("/api/common-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommonTable() throws Exception {
        // Get the commonTable
        restCommonTableMockMvc.perform(get("/api/common-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommonTable() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        int databaseSizeBeforeUpdate = commonTableRepository.findAll().size();

        // Update the commonTable
        CommonTable updatedCommonTable = commonTableRepository.findById(commonTable.getId()).get();
        // Disconnect from session so that the updates on updatedCommonTable are not directly saved in db
        em.detach(updatedCommonTable);
        updatedCommonTable
            .name(UPDATED_NAME)
            .entityName(UPDATED_ENTITY_NAME)
            .tableName(UPDATED_TABLE_NAME)
            .system(UPDATED_SYSTEM)
            .clazzName(UPDATED_CLAZZ_NAME)
            .generated(UPDATED_GENERATED)
            .creatAt(UPDATED_CREAT_AT)
            .generateAt(UPDATED_GENERATE_AT)
            .generateClassAt(UPDATED_GENERATE_CLASS_AT)
            .description(UPDATED_DESCRIPTION)
            .treeTable(UPDATED_TREE_TABLE)
            .baseTableId(UPDATED_BASE_TABLE_ID)
            .recordActionWidth(UPDATED_RECORD_ACTION_WIDTH)
            .listConfig(UPDATED_LIST_CONFIG)
            .formConfig(UPDATED_FORM_CONFIG);
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(updatedCommonTable);

        restCommonTableMockMvc.perform(put("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isOk());

        // Validate the CommonTable in the database
        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeUpdate);
        CommonTable testCommonTable = commonTableList.get(commonTableList.size() - 1);
        assertThat(testCommonTable.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommonTable.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testCommonTable.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testCommonTable.isSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testCommonTable.getClazzName()).isEqualTo(UPDATED_CLAZZ_NAME);
        assertThat(testCommonTable.isGenerated()).isEqualTo(UPDATED_GENERATED);
        assertThat(testCommonTable.getCreatAt()).isEqualTo(UPDATED_CREAT_AT);
        assertThat(testCommonTable.getGenerateAt()).isEqualTo(UPDATED_GENERATE_AT);
        assertThat(testCommonTable.getGenerateClassAt()).isEqualTo(UPDATED_GENERATE_CLASS_AT);
        assertThat(testCommonTable.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommonTable.isTreeTable()).isEqualTo(UPDATED_TREE_TABLE);
        assertThat(testCommonTable.getBaseTableId()).isEqualTo(UPDATED_BASE_TABLE_ID);
        assertThat(testCommonTable.getRecordActionWidth()).isEqualTo(UPDATED_RECORD_ACTION_WIDTH);
        assertThat(testCommonTable.getListConfig()).isEqualTo(UPDATED_LIST_CONFIG);
        assertThat(testCommonTable.getFormConfig()).isEqualTo(UPDATED_FORM_CONFIG);
    }

    @Test
    @Transactional
    public void updateNonExistingCommonTable() throws Exception {
        int databaseSizeBeforeUpdate = commonTableRepository.findAll().size();

        // Create the CommonTable
        CommonTableDTO commonTableDTO = commonTableMapper.toDto(commonTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonTableMockMvc.perform(put("/api/common-tables")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommonTable in the database
        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommonTable() throws Exception {
        // Initialize the database
        commonTableRepository.saveAndFlush(commonTable);

        int databaseSizeBeforeDelete = commonTableRepository.findAll().size();

        // Delete the commonTable
        restCommonTableMockMvc.perform(delete("/api/common-tables/{id}", commonTable.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommonTable> commonTableList = commonTableRepository.findAll();
        assertThat(commonTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
