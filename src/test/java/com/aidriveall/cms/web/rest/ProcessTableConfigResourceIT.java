package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.ProcessTableConfig;
import com.aidriveall.cms.domain.CommonTable;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.repository.ProcessTableConfigRepository;
import com.aidriveall.cms.service.ProcessTableConfigService;
import com.aidriveall.cms.service.dto.ProcessTableConfigDTO;
import com.aidriveall.cms.service.mapper.ProcessTableConfigMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.ProcessTableConfigCriteria;
import com.aidriveall.cms.service.ProcessTableConfigQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.aidriveall.cms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProcessTableConfigResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class ProcessTableConfigResourceIT {

    private static final String DEFAULT_PROCESS_DEFINITION_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_DEFINITION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_DEFINITION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_DEFINITION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_DEFINITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_DEFINITION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESS_BPMN_DATA = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_BPMN_DATA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEPLOIED = false;
    private static final Boolean UPDATED_DEPLOIED = true;

    @Autowired
    private ProcessTableConfigRepository processTableConfigRepository;

    @Autowired
    private ProcessTableConfigMapper processTableConfigMapper;

    @Autowired
    private ProcessTableConfigService processTableConfigService;

    @Autowired
    private ProcessTableConfigQueryService processTableConfigQueryService;

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

    private MockMvc restProcessTableConfigMockMvc;

    private ProcessTableConfig processTableConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessTableConfigResource processTableConfigResource = new ProcessTableConfigResource(processTableConfigService, processTableConfigQueryService);
        this.restProcessTableConfigMockMvc = MockMvcBuilders.standaloneSetup(processTableConfigResource)
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
    public static ProcessTableConfig createEntity(EntityManager em) {
        ProcessTableConfig processTableConfig = new ProcessTableConfig()
            .processDefinitionId(DEFAULT_PROCESS_DEFINITION_ID)
            .processDefinitionKey(DEFAULT_PROCESS_DEFINITION_KEY)
            .processDefinitionName(DEFAULT_PROCESS_DEFINITION_NAME)
            .description(DEFAULT_DESCRIPTION)
            .processBpmnData(DEFAULT_PROCESS_BPMN_DATA)
            .deploied(DEFAULT_DEPLOIED);
        return processTableConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessTableConfig createUpdatedEntity(EntityManager em) {
        ProcessTableConfig processTableConfig = new ProcessTableConfig()
            .processDefinitionId(UPDATED_PROCESS_DEFINITION_ID)
            .processDefinitionKey(UPDATED_PROCESS_DEFINITION_KEY)
            .processDefinitionName(UPDATED_PROCESS_DEFINITION_NAME)
            .description(UPDATED_DESCRIPTION)
            .processBpmnData(UPDATED_PROCESS_BPMN_DATA)
            .deploied(UPDATED_DEPLOIED);
        return processTableConfig;
    }

    @BeforeEach
    public void initTest() {
        processTableConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessTableConfig() throws Exception {
        int databaseSizeBeforeCreate = processTableConfigRepository.findAll().size();

        // Create the ProcessTableConfig
        ProcessTableConfigDTO processTableConfigDTO = processTableConfigMapper.toDto(processTableConfig);
        restProcessTableConfigMockMvc.perform(post("/api/process-table-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processTableConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcessTableConfig in the database
        List<ProcessTableConfig> processTableConfigList = processTableConfigRepository.findAll();
        assertThat(processTableConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessTableConfig testProcessTableConfig = processTableConfigList.get(processTableConfigList.size() - 1);
        assertThat(testProcessTableConfig.getProcessDefinitionId()).isEqualTo(DEFAULT_PROCESS_DEFINITION_ID);
        assertThat(testProcessTableConfig.getProcessDefinitionKey()).isEqualTo(DEFAULT_PROCESS_DEFINITION_KEY);
        assertThat(testProcessTableConfig.getProcessDefinitionName()).isEqualTo(DEFAULT_PROCESS_DEFINITION_NAME);
        assertThat(testProcessTableConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProcessTableConfig.getProcessBpmnData()).isEqualTo(DEFAULT_PROCESS_BPMN_DATA);
        assertThat(testProcessTableConfig.isDeploied()).isEqualTo(DEFAULT_DEPLOIED);
    }

    @Test
    @Transactional
    public void createProcessTableConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processTableConfigRepository.findAll().size();

        // Create the ProcessTableConfig with an existing ID
        processTableConfig.setId(1L);
        ProcessTableConfigDTO processTableConfigDTO = processTableConfigMapper.toDto(processTableConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessTableConfigMockMvc.perform(post("/api/process-table-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processTableConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessTableConfig in the database
        List<ProcessTableConfig> processTableConfigList = processTableConfigRepository.findAll();
        assertThat(processTableConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessTableConfigs() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList
        restProcessTableConfigMockMvc.perform(get("/api/process-table-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processTableConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].processDefinitionId").value(hasItem(DEFAULT_PROCESS_DEFINITION_ID)))
            .andExpect(jsonPath("$.[*].processDefinitionKey").value(hasItem(DEFAULT_PROCESS_DEFINITION_KEY)))
            .andExpect(jsonPath("$.[*].processDefinitionName").value(hasItem(DEFAULT_PROCESS_DEFINITION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].processBpmnData").value(hasItem(DEFAULT_PROCESS_BPMN_DATA.toString())))
            .andExpect(jsonPath("$.[*].deploied").value(hasItem(DEFAULT_DEPLOIED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProcessTableConfig() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get the processTableConfig
        restProcessTableConfigMockMvc.perform(get("/api/process-table-configs/{id}", processTableConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processTableConfig.getId().intValue()))
            .andExpect(jsonPath("$.processDefinitionId").value(DEFAULT_PROCESS_DEFINITION_ID))
            .andExpect(jsonPath("$.processDefinitionKey").value(DEFAULT_PROCESS_DEFINITION_KEY))
            .andExpect(jsonPath("$.processDefinitionName").value(DEFAULT_PROCESS_DEFINITION_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.processBpmnData").value(DEFAULT_PROCESS_BPMN_DATA.toString()))
            .andExpect(jsonPath("$.deploied").value(DEFAULT_DEPLOIED.booleanValue()));
    }


    @Test
    @Transactional
    public void getProcessTableConfigsByIdFiltering() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        Long id = processTableConfig.getId();

        defaultProcessTableConfigShouldBeFound("id.equals=" + id);
        defaultProcessTableConfigShouldNotBeFound("id.notEquals=" + id);

        defaultProcessTableConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessTableConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessTableConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessTableConfigShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionId equals to DEFAULT_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldBeFound("processDefinitionId.equals=" + DEFAULT_PROCESS_DEFINITION_ID);

        // Get all the processTableConfigList where processDefinitionId equals to UPDATED_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldNotBeFound("processDefinitionId.equals=" + UPDATED_PROCESS_DEFINITION_ID);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionId not equals to DEFAULT_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldNotBeFound("processDefinitionId.notEquals=" + DEFAULT_PROCESS_DEFINITION_ID);

        // Get all the processTableConfigList where processDefinitionId not equals to UPDATED_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldBeFound("processDefinitionId.notEquals=" + UPDATED_PROCESS_DEFINITION_ID);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionIdIsInShouldWork() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionId in DEFAULT_PROCESS_DEFINITION_ID or UPDATED_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldBeFound("processDefinitionId.in=" + DEFAULT_PROCESS_DEFINITION_ID + "," + UPDATED_PROCESS_DEFINITION_ID);

        // Get all the processTableConfigList where processDefinitionId equals to UPDATED_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldNotBeFound("processDefinitionId.in=" + UPDATED_PROCESS_DEFINITION_ID);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionId is not null
        defaultProcessTableConfigShouldBeFound("processDefinitionId.specified=true");

        // Get all the processTableConfigList where processDefinitionId is null
        defaultProcessTableConfigShouldNotBeFound("processDefinitionId.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionIdContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionId contains DEFAULT_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldBeFound("processDefinitionId.contains=" + DEFAULT_PROCESS_DEFINITION_ID);

        // Get all the processTableConfigList where processDefinitionId contains UPDATED_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldNotBeFound("processDefinitionId.contains=" + UPDATED_PROCESS_DEFINITION_ID);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionIdNotContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionId does not contain DEFAULT_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldNotBeFound("processDefinitionId.doesNotContain=" + DEFAULT_PROCESS_DEFINITION_ID);

        // Get all the processTableConfigList where processDefinitionId does not contain UPDATED_PROCESS_DEFINITION_ID
        defaultProcessTableConfigShouldBeFound("processDefinitionId.doesNotContain=" + UPDATED_PROCESS_DEFINITION_ID);
    }


    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionKey equals to DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldBeFound("processDefinitionKey.equals=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processTableConfigList where processDefinitionKey equals to UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldNotBeFound("processDefinitionKey.equals=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionKey not equals to DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldNotBeFound("processDefinitionKey.notEquals=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processTableConfigList where processDefinitionKey not equals to UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldBeFound("processDefinitionKey.notEquals=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionKeyIsInShouldWork() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionKey in DEFAULT_PROCESS_DEFINITION_KEY or UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldBeFound("processDefinitionKey.in=" + DEFAULT_PROCESS_DEFINITION_KEY + "," + UPDATED_PROCESS_DEFINITION_KEY);

        // Get all the processTableConfigList where processDefinitionKey equals to UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldNotBeFound("processDefinitionKey.in=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionKey is not null
        defaultProcessTableConfigShouldBeFound("processDefinitionKey.specified=true");

        // Get all the processTableConfigList where processDefinitionKey is null
        defaultProcessTableConfigShouldNotBeFound("processDefinitionKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionKeyContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionKey contains DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldBeFound("processDefinitionKey.contains=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processTableConfigList where processDefinitionKey contains UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldNotBeFound("processDefinitionKey.contains=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionKeyNotContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionKey does not contain DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldNotBeFound("processDefinitionKey.doesNotContain=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processTableConfigList where processDefinitionKey does not contain UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessTableConfigShouldBeFound("processDefinitionKey.doesNotContain=" + UPDATED_PROCESS_DEFINITION_KEY);
    }


    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionName equals to DEFAULT_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldBeFound("processDefinitionName.equals=" + DEFAULT_PROCESS_DEFINITION_NAME);

        // Get all the processTableConfigList where processDefinitionName equals to UPDATED_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldNotBeFound("processDefinitionName.equals=" + UPDATED_PROCESS_DEFINITION_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionName not equals to DEFAULT_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldNotBeFound("processDefinitionName.notEquals=" + DEFAULT_PROCESS_DEFINITION_NAME);

        // Get all the processTableConfigList where processDefinitionName not equals to UPDATED_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldBeFound("processDefinitionName.notEquals=" + UPDATED_PROCESS_DEFINITION_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionNameIsInShouldWork() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionName in DEFAULT_PROCESS_DEFINITION_NAME or UPDATED_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldBeFound("processDefinitionName.in=" + DEFAULT_PROCESS_DEFINITION_NAME + "," + UPDATED_PROCESS_DEFINITION_NAME);

        // Get all the processTableConfigList where processDefinitionName equals to UPDATED_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldNotBeFound("processDefinitionName.in=" + UPDATED_PROCESS_DEFINITION_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionName is not null
        defaultProcessTableConfigShouldBeFound("processDefinitionName.specified=true");

        // Get all the processTableConfigList where processDefinitionName is null
        defaultProcessTableConfigShouldNotBeFound("processDefinitionName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionNameContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionName contains DEFAULT_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldBeFound("processDefinitionName.contains=" + DEFAULT_PROCESS_DEFINITION_NAME);

        // Get all the processTableConfigList where processDefinitionName contains UPDATED_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldNotBeFound("processDefinitionName.contains=" + UPDATED_PROCESS_DEFINITION_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByProcessDefinitionNameNotContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where processDefinitionName does not contain DEFAULT_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldNotBeFound("processDefinitionName.doesNotContain=" + DEFAULT_PROCESS_DEFINITION_NAME);

        // Get all the processTableConfigList where processDefinitionName does not contain UPDATED_PROCESS_DEFINITION_NAME
        defaultProcessTableConfigShouldBeFound("processDefinitionName.doesNotContain=" + UPDATED_PROCESS_DEFINITION_NAME);
    }


    @Test
    @Transactional
    public void getAllProcessTableConfigsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where description equals to DEFAULT_DESCRIPTION
        defaultProcessTableConfigShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the processTableConfigList where description equals to UPDATED_DESCRIPTION
        defaultProcessTableConfigShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where description not equals to DEFAULT_DESCRIPTION
        defaultProcessTableConfigShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the processTableConfigList where description not equals to UPDATED_DESCRIPTION
        defaultProcessTableConfigShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProcessTableConfigShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the processTableConfigList where description equals to UPDATED_DESCRIPTION
        defaultProcessTableConfigShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where description is not null
        defaultProcessTableConfigShouldBeFound("description.specified=true");

        // Get all the processTableConfigList where description is null
        defaultProcessTableConfigShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessTableConfigsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where description contains DEFAULT_DESCRIPTION
        defaultProcessTableConfigShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the processTableConfigList where description contains UPDATED_DESCRIPTION
        defaultProcessTableConfigShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where description does not contain DEFAULT_DESCRIPTION
        defaultProcessTableConfigShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the processTableConfigList where description does not contain UPDATED_DESCRIPTION
        defaultProcessTableConfigShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProcessTableConfigsByDeploiedIsEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where deploied equals to DEFAULT_DEPLOIED
        defaultProcessTableConfigShouldBeFound("deploied.equals=" + DEFAULT_DEPLOIED);

        // Get all the processTableConfigList where deploied equals to UPDATED_DEPLOIED
        defaultProcessTableConfigShouldNotBeFound("deploied.equals=" + UPDATED_DEPLOIED);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByDeploiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where deploied not equals to DEFAULT_DEPLOIED
        defaultProcessTableConfigShouldNotBeFound("deploied.notEquals=" + DEFAULT_DEPLOIED);

        // Get all the processTableConfigList where deploied not equals to UPDATED_DEPLOIED
        defaultProcessTableConfigShouldBeFound("deploied.notEquals=" + UPDATED_DEPLOIED);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByDeploiedIsInShouldWork() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where deploied in DEFAULT_DEPLOIED or UPDATED_DEPLOIED
        defaultProcessTableConfigShouldBeFound("deploied.in=" + DEFAULT_DEPLOIED + "," + UPDATED_DEPLOIED);

        // Get all the processTableConfigList where deploied equals to UPDATED_DEPLOIED
        defaultProcessTableConfigShouldNotBeFound("deploied.in=" + UPDATED_DEPLOIED);
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByDeploiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        // Get all the processTableConfigList where deploied is not null
        defaultProcessTableConfigShouldBeFound("deploied.specified=true");

        // Get all the processTableConfigList where deploied is null
        defaultProcessTableConfigShouldNotBeFound("deploied.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessTableConfigsByCommonTableIsEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);
        CommonTable commonTable = CommonTableResourceIT.createEntity(em);
        em.persist(commonTable);
        em.flush();
        processTableConfig.setCommonTable(commonTable);
        processTableConfigRepository.saveAndFlush(processTableConfig);
        Long commonTableId = commonTable.getId();

        // Get all the processTableConfigList where commonTable equals to commonTableId
        defaultProcessTableConfigShouldBeFound("commonTableId.equals=" + commonTableId);

        // Get all the processTableConfigList where commonTable equals to commonTableId + 1
        defaultProcessTableConfigShouldNotBeFound("commonTableId.equals=" + (commonTableId + 1));
    }


    @Test
    @Transactional
    public void getAllProcessTableConfigsByCreatorIsEqualToSomething() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);
        User creator = UserResourceIT.createEntity(em);
        em.persist(creator);
        em.flush();
        processTableConfig.setCreator(creator);
        processTableConfigRepository.saveAndFlush(processTableConfig);
        Long creatorId = creator.getId();

        // Get all the processTableConfigList where creator equals to creatorId
        defaultProcessTableConfigShouldBeFound("creatorId.equals=" + creatorId);

        // Get all the processTableConfigList where creator equals to creatorId + 1
        defaultProcessTableConfigShouldNotBeFound("creatorId.equals=" + (creatorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessTableConfigShouldBeFound(String filter) throws Exception {
        restProcessTableConfigMockMvc.perform(get("/api/process-table-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processTableConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].processDefinitionId").value(hasItem(DEFAULT_PROCESS_DEFINITION_ID)))
            .andExpect(jsonPath("$.[*].processDefinitionKey").value(hasItem(DEFAULT_PROCESS_DEFINITION_KEY)))
            .andExpect(jsonPath("$.[*].processDefinitionName").value(hasItem(DEFAULT_PROCESS_DEFINITION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].processBpmnData").value(hasItem(DEFAULT_PROCESS_BPMN_DATA.toString())))
            .andExpect(jsonPath("$.[*].deploied").value(hasItem(DEFAULT_DEPLOIED.booleanValue())));

        // Check, that the count call also returns 1
        restProcessTableConfigMockMvc.perform(get("/api/process-table-configs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessTableConfigShouldNotBeFound(String filter) throws Exception {
        restProcessTableConfigMockMvc.perform(get("/api/process-table-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessTableConfigMockMvc.perform(get("/api/process-table-configs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProcessTableConfig() throws Exception {
        // Get the processTableConfig
        restProcessTableConfigMockMvc.perform(get("/api/process-table-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessTableConfig() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        int databaseSizeBeforeUpdate = processTableConfigRepository.findAll().size();

        // Update the processTableConfig
        ProcessTableConfig updatedProcessTableConfig = processTableConfigRepository.findById(processTableConfig.getId()).get();
        // Disconnect from session so that the updates on updatedProcessTableConfig are not directly saved in db
        em.detach(updatedProcessTableConfig);
        updatedProcessTableConfig
            .processDefinitionId(UPDATED_PROCESS_DEFINITION_ID)
            .processDefinitionKey(UPDATED_PROCESS_DEFINITION_KEY)
            .processDefinitionName(UPDATED_PROCESS_DEFINITION_NAME)
            .description(UPDATED_DESCRIPTION)
            .processBpmnData(UPDATED_PROCESS_BPMN_DATA)
            .deploied(UPDATED_DEPLOIED);
        ProcessTableConfigDTO processTableConfigDTO = processTableConfigMapper.toDto(updatedProcessTableConfig);

        restProcessTableConfigMockMvc.perform(put("/api/process-table-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processTableConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ProcessTableConfig in the database
        List<ProcessTableConfig> processTableConfigList = processTableConfigRepository.findAll();
        assertThat(processTableConfigList).hasSize(databaseSizeBeforeUpdate);
        ProcessTableConfig testProcessTableConfig = processTableConfigList.get(processTableConfigList.size() - 1);
        assertThat(testProcessTableConfig.getProcessDefinitionId()).isEqualTo(UPDATED_PROCESS_DEFINITION_ID);
        assertThat(testProcessTableConfig.getProcessDefinitionKey()).isEqualTo(UPDATED_PROCESS_DEFINITION_KEY);
        assertThat(testProcessTableConfig.getProcessDefinitionName()).isEqualTo(UPDATED_PROCESS_DEFINITION_NAME);
        assertThat(testProcessTableConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProcessTableConfig.getProcessBpmnData()).isEqualTo(UPDATED_PROCESS_BPMN_DATA);
        assertThat(testProcessTableConfig.isDeploied()).isEqualTo(UPDATED_DEPLOIED);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessTableConfig() throws Exception {
        int databaseSizeBeforeUpdate = processTableConfigRepository.findAll().size();

        // Create the ProcessTableConfig
        ProcessTableConfigDTO processTableConfigDTO = processTableConfigMapper.toDto(processTableConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessTableConfigMockMvc.perform(put("/api/process-table-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processTableConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessTableConfig in the database
        List<ProcessTableConfig> processTableConfigList = processTableConfigRepository.findAll();
        assertThat(processTableConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessTableConfig() throws Exception {
        // Initialize the database
        processTableConfigRepository.saveAndFlush(processTableConfig);

        int databaseSizeBeforeDelete = processTableConfigRepository.findAll().size();

        // Delete the processTableConfig
        restProcessTableConfigMockMvc.perform(delete("/api/process-table-configs/{id}", processTableConfig.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessTableConfig> processTableConfigList = processTableConfigRepository.findAll();
        assertThat(processTableConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
