package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.ProcessFormConfig;
import com.aidriveall.cms.repository.ProcessFormConfigRepository;
import com.aidriveall.cms.service.ProcessFormConfigService;
import com.aidriveall.cms.service.dto.ProcessFormConfigDTO;
import com.aidriveall.cms.service.mapper.ProcessFormConfigMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.ProcessFormConfigCriteria;
import com.aidriveall.cms.service.ProcessFormConfigQueryService;

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
 * Integration tests for the {@link ProcessFormConfigResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class ProcessFormConfigResourceIT {

    private static final String DEFAULT_PROCESS_DEFINITION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_DEFINITION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_NODE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NODE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TASK_NODE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TASK_NODE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_COMMON_TABLE_ID = 1L;
    private static final Long UPDATED_COMMON_TABLE_ID = 2L;
    private static final Long SMALLER_COMMON_TABLE_ID = 1L - 1L;

    private static final String DEFAULT_FORM_DATA = "AAAAAAAAAA";
    private static final String UPDATED_FORM_DATA = "BBBBBBBBBB";

    @Autowired
    private ProcessFormConfigRepository processFormConfigRepository;

    @Autowired
    private ProcessFormConfigMapper processFormConfigMapper;

    @Autowired
    private ProcessFormConfigService processFormConfigService;

    @Autowired
    private ProcessFormConfigQueryService processFormConfigQueryService;

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

    private MockMvc restProcessFormConfigMockMvc;

    private ProcessFormConfig processFormConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessFormConfigResource processFormConfigResource = new ProcessFormConfigResource(processFormConfigService, processFormConfigQueryService);
        this.restProcessFormConfigMockMvc = MockMvcBuilders.standaloneSetup(processFormConfigResource)
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
    public static ProcessFormConfig createEntity(EntityManager em) {
        ProcessFormConfig processFormConfig = new ProcessFormConfig()
            .processDefinitionKey(DEFAULT_PROCESS_DEFINITION_KEY)
            .taskNodeId(DEFAULT_TASK_NODE_ID)
            .taskNodeName(DEFAULT_TASK_NODE_NAME)
            .commonTableId(DEFAULT_COMMON_TABLE_ID)
            .formData(DEFAULT_FORM_DATA);
        return processFormConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessFormConfig createUpdatedEntity(EntityManager em) {
        ProcessFormConfig processFormConfig = new ProcessFormConfig()
            .processDefinitionKey(UPDATED_PROCESS_DEFINITION_KEY)
            .taskNodeId(UPDATED_TASK_NODE_ID)
            .taskNodeName(UPDATED_TASK_NODE_NAME)
            .commonTableId(UPDATED_COMMON_TABLE_ID)
            .formData(UPDATED_FORM_DATA);
        return processFormConfig;
    }

    @BeforeEach
    public void initTest() {
        processFormConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessFormConfig() throws Exception {
        int databaseSizeBeforeCreate = processFormConfigRepository.findAll().size();

        // Create the ProcessFormConfig
        ProcessFormConfigDTO processFormConfigDTO = processFormConfigMapper.toDto(processFormConfig);
        restProcessFormConfigMockMvc.perform(post("/api/process-form-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processFormConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcessFormConfig in the database
        List<ProcessFormConfig> processFormConfigList = processFormConfigRepository.findAll();
        assertThat(processFormConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessFormConfig testProcessFormConfig = processFormConfigList.get(processFormConfigList.size() - 1);
        assertThat(testProcessFormConfig.getProcessDefinitionKey()).isEqualTo(DEFAULT_PROCESS_DEFINITION_KEY);
        assertThat(testProcessFormConfig.getTaskNodeId()).isEqualTo(DEFAULT_TASK_NODE_ID);
        assertThat(testProcessFormConfig.getTaskNodeName()).isEqualTo(DEFAULT_TASK_NODE_NAME);
        assertThat(testProcessFormConfig.getCommonTableId()).isEqualTo(DEFAULT_COMMON_TABLE_ID);
        assertThat(testProcessFormConfig.getFormData()).isEqualTo(DEFAULT_FORM_DATA);
    }

    @Test
    @Transactional
    public void createProcessFormConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processFormConfigRepository.findAll().size();

        // Create the ProcessFormConfig with an existing ID
        processFormConfig.setId(1L);
        ProcessFormConfigDTO processFormConfigDTO = processFormConfigMapper.toDto(processFormConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessFormConfigMockMvc.perform(post("/api/process-form-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processFormConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessFormConfig in the database
        List<ProcessFormConfig> processFormConfigList = processFormConfigRepository.findAll();
        assertThat(processFormConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessFormConfigs() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList
        restProcessFormConfigMockMvc.perform(get("/api/process-form-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processFormConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].processDefinitionKey").value(hasItem(DEFAULT_PROCESS_DEFINITION_KEY)))
            .andExpect(jsonPath("$.[*].taskNodeId").value(hasItem(DEFAULT_TASK_NODE_ID)))
            .andExpect(jsonPath("$.[*].taskNodeName").value(hasItem(DEFAULT_TASK_NODE_NAME)))
            .andExpect(jsonPath("$.[*].commonTableId").value(hasItem(DEFAULT_COMMON_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].formData").value(hasItem(DEFAULT_FORM_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getProcessFormConfig() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get the processFormConfig
        restProcessFormConfigMockMvc.perform(get("/api/process-form-configs/{id}", processFormConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processFormConfig.getId().intValue()))
            .andExpect(jsonPath("$.processDefinitionKey").value(DEFAULT_PROCESS_DEFINITION_KEY))
            .andExpect(jsonPath("$.taskNodeId").value(DEFAULT_TASK_NODE_ID))
            .andExpect(jsonPath("$.taskNodeName").value(DEFAULT_TASK_NODE_NAME))
            .andExpect(jsonPath("$.commonTableId").value(DEFAULT_COMMON_TABLE_ID.intValue()))
            .andExpect(jsonPath("$.formData").value(DEFAULT_FORM_DATA.toString()));
    }


    @Test
    @Transactional
    public void getProcessFormConfigsByIdFiltering() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        Long id = processFormConfig.getId();

        defaultProcessFormConfigShouldBeFound("id.equals=" + id);
        defaultProcessFormConfigShouldNotBeFound("id.notEquals=" + id);

        defaultProcessFormConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessFormConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessFormConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessFormConfigShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProcessFormConfigsByProcessDefinitionKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where processDefinitionKey equals to DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldBeFound("processDefinitionKey.equals=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processFormConfigList where processDefinitionKey equals to UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldNotBeFound("processDefinitionKey.equals=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByProcessDefinitionKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where processDefinitionKey not equals to DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldNotBeFound("processDefinitionKey.notEquals=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processFormConfigList where processDefinitionKey not equals to UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldBeFound("processDefinitionKey.notEquals=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByProcessDefinitionKeyIsInShouldWork() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where processDefinitionKey in DEFAULT_PROCESS_DEFINITION_KEY or UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldBeFound("processDefinitionKey.in=" + DEFAULT_PROCESS_DEFINITION_KEY + "," + UPDATED_PROCESS_DEFINITION_KEY);

        // Get all the processFormConfigList where processDefinitionKey equals to UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldNotBeFound("processDefinitionKey.in=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByProcessDefinitionKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where processDefinitionKey is not null
        defaultProcessFormConfigShouldBeFound("processDefinitionKey.specified=true");

        // Get all the processFormConfigList where processDefinitionKey is null
        defaultProcessFormConfigShouldNotBeFound("processDefinitionKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessFormConfigsByProcessDefinitionKeyContainsSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where processDefinitionKey contains DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldBeFound("processDefinitionKey.contains=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processFormConfigList where processDefinitionKey contains UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldNotBeFound("processDefinitionKey.contains=" + UPDATED_PROCESS_DEFINITION_KEY);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByProcessDefinitionKeyNotContainsSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where processDefinitionKey does not contain DEFAULT_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldNotBeFound("processDefinitionKey.doesNotContain=" + DEFAULT_PROCESS_DEFINITION_KEY);

        // Get all the processFormConfigList where processDefinitionKey does not contain UPDATED_PROCESS_DEFINITION_KEY
        defaultProcessFormConfigShouldBeFound("processDefinitionKey.doesNotContain=" + UPDATED_PROCESS_DEFINITION_KEY);
    }


    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeId equals to DEFAULT_TASK_NODE_ID
        defaultProcessFormConfigShouldBeFound("taskNodeId.equals=" + DEFAULT_TASK_NODE_ID);

        // Get all the processFormConfigList where taskNodeId equals to UPDATED_TASK_NODE_ID
        defaultProcessFormConfigShouldNotBeFound("taskNodeId.equals=" + UPDATED_TASK_NODE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeId not equals to DEFAULT_TASK_NODE_ID
        defaultProcessFormConfigShouldNotBeFound("taskNodeId.notEquals=" + DEFAULT_TASK_NODE_ID);

        // Get all the processFormConfigList where taskNodeId not equals to UPDATED_TASK_NODE_ID
        defaultProcessFormConfigShouldBeFound("taskNodeId.notEquals=" + UPDATED_TASK_NODE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeIdIsInShouldWork() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeId in DEFAULT_TASK_NODE_ID or UPDATED_TASK_NODE_ID
        defaultProcessFormConfigShouldBeFound("taskNodeId.in=" + DEFAULT_TASK_NODE_ID + "," + UPDATED_TASK_NODE_ID);

        // Get all the processFormConfigList where taskNodeId equals to UPDATED_TASK_NODE_ID
        defaultProcessFormConfigShouldNotBeFound("taskNodeId.in=" + UPDATED_TASK_NODE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeId is not null
        defaultProcessFormConfigShouldBeFound("taskNodeId.specified=true");

        // Get all the processFormConfigList where taskNodeId is null
        defaultProcessFormConfigShouldNotBeFound("taskNodeId.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeIdContainsSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeId contains DEFAULT_TASK_NODE_ID
        defaultProcessFormConfigShouldBeFound("taskNodeId.contains=" + DEFAULT_TASK_NODE_ID);

        // Get all the processFormConfigList where taskNodeId contains UPDATED_TASK_NODE_ID
        defaultProcessFormConfigShouldNotBeFound("taskNodeId.contains=" + UPDATED_TASK_NODE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeIdNotContainsSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeId does not contain DEFAULT_TASK_NODE_ID
        defaultProcessFormConfigShouldNotBeFound("taskNodeId.doesNotContain=" + DEFAULT_TASK_NODE_ID);

        // Get all the processFormConfigList where taskNodeId does not contain UPDATED_TASK_NODE_ID
        defaultProcessFormConfigShouldBeFound("taskNodeId.doesNotContain=" + UPDATED_TASK_NODE_ID);
    }


    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeName equals to DEFAULT_TASK_NODE_NAME
        defaultProcessFormConfigShouldBeFound("taskNodeName.equals=" + DEFAULT_TASK_NODE_NAME);

        // Get all the processFormConfigList where taskNodeName equals to UPDATED_TASK_NODE_NAME
        defaultProcessFormConfigShouldNotBeFound("taskNodeName.equals=" + UPDATED_TASK_NODE_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeName not equals to DEFAULT_TASK_NODE_NAME
        defaultProcessFormConfigShouldNotBeFound("taskNodeName.notEquals=" + DEFAULT_TASK_NODE_NAME);

        // Get all the processFormConfigList where taskNodeName not equals to UPDATED_TASK_NODE_NAME
        defaultProcessFormConfigShouldBeFound("taskNodeName.notEquals=" + UPDATED_TASK_NODE_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeNameIsInShouldWork() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeName in DEFAULT_TASK_NODE_NAME or UPDATED_TASK_NODE_NAME
        defaultProcessFormConfigShouldBeFound("taskNodeName.in=" + DEFAULT_TASK_NODE_NAME + "," + UPDATED_TASK_NODE_NAME);

        // Get all the processFormConfigList where taskNodeName equals to UPDATED_TASK_NODE_NAME
        defaultProcessFormConfigShouldNotBeFound("taskNodeName.in=" + UPDATED_TASK_NODE_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeName is not null
        defaultProcessFormConfigShouldBeFound("taskNodeName.specified=true");

        // Get all the processFormConfigList where taskNodeName is null
        defaultProcessFormConfigShouldNotBeFound("taskNodeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeNameContainsSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeName contains DEFAULT_TASK_NODE_NAME
        defaultProcessFormConfigShouldBeFound("taskNodeName.contains=" + DEFAULT_TASK_NODE_NAME);

        // Get all the processFormConfigList where taskNodeName contains UPDATED_TASK_NODE_NAME
        defaultProcessFormConfigShouldNotBeFound("taskNodeName.contains=" + UPDATED_TASK_NODE_NAME);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByTaskNodeNameNotContainsSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where taskNodeName does not contain DEFAULT_TASK_NODE_NAME
        defaultProcessFormConfigShouldNotBeFound("taskNodeName.doesNotContain=" + DEFAULT_TASK_NODE_NAME);

        // Get all the processFormConfigList where taskNodeName does not contain UPDATED_TASK_NODE_NAME
        defaultProcessFormConfigShouldBeFound("taskNodeName.doesNotContain=" + UPDATED_TASK_NODE_NAME);
    }


    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId equals to DEFAULT_COMMON_TABLE_ID
        defaultProcessFormConfigShouldBeFound("commonTableId.equals=" + DEFAULT_COMMON_TABLE_ID);

        // Get all the processFormConfigList where commonTableId equals to UPDATED_COMMON_TABLE_ID
        defaultProcessFormConfigShouldNotBeFound("commonTableId.equals=" + UPDATED_COMMON_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId not equals to DEFAULT_COMMON_TABLE_ID
        defaultProcessFormConfigShouldNotBeFound("commonTableId.notEquals=" + DEFAULT_COMMON_TABLE_ID);

        // Get all the processFormConfigList where commonTableId not equals to UPDATED_COMMON_TABLE_ID
        defaultProcessFormConfigShouldBeFound("commonTableId.notEquals=" + UPDATED_COMMON_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsInShouldWork() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId in DEFAULT_COMMON_TABLE_ID or UPDATED_COMMON_TABLE_ID
        defaultProcessFormConfigShouldBeFound("commonTableId.in=" + DEFAULT_COMMON_TABLE_ID + "," + UPDATED_COMMON_TABLE_ID);

        // Get all the processFormConfigList where commonTableId equals to UPDATED_COMMON_TABLE_ID
        defaultProcessFormConfigShouldNotBeFound("commonTableId.in=" + UPDATED_COMMON_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId is not null
        defaultProcessFormConfigShouldBeFound("commonTableId.specified=true");

        // Get all the processFormConfigList where commonTableId is null
        defaultProcessFormConfigShouldNotBeFound("commonTableId.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId is greater than or equal to DEFAULT_COMMON_TABLE_ID
        defaultProcessFormConfigShouldBeFound("commonTableId.greaterThanOrEqual=" + DEFAULT_COMMON_TABLE_ID);

        // Get all the processFormConfigList where commonTableId is greater than or equal to UPDATED_COMMON_TABLE_ID
        defaultProcessFormConfigShouldNotBeFound("commonTableId.greaterThanOrEqual=" + UPDATED_COMMON_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId is less than or equal to DEFAULT_COMMON_TABLE_ID
        defaultProcessFormConfigShouldBeFound("commonTableId.lessThanOrEqual=" + DEFAULT_COMMON_TABLE_ID);

        // Get all the processFormConfigList where commonTableId is less than or equal to SMALLER_COMMON_TABLE_ID
        defaultProcessFormConfigShouldNotBeFound("commonTableId.lessThanOrEqual=" + SMALLER_COMMON_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsLessThanSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId is less than DEFAULT_COMMON_TABLE_ID
        defaultProcessFormConfigShouldNotBeFound("commonTableId.lessThan=" + DEFAULT_COMMON_TABLE_ID);

        // Get all the processFormConfigList where commonTableId is less than UPDATED_COMMON_TABLE_ID
        defaultProcessFormConfigShouldBeFound("commonTableId.lessThan=" + UPDATED_COMMON_TABLE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessFormConfigsByCommonTableIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        // Get all the processFormConfigList where commonTableId is greater than DEFAULT_COMMON_TABLE_ID
        defaultProcessFormConfigShouldNotBeFound("commonTableId.greaterThan=" + DEFAULT_COMMON_TABLE_ID);

        // Get all the processFormConfigList where commonTableId is greater than SMALLER_COMMON_TABLE_ID
        defaultProcessFormConfigShouldBeFound("commonTableId.greaterThan=" + SMALLER_COMMON_TABLE_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessFormConfigShouldBeFound(String filter) throws Exception {
        restProcessFormConfigMockMvc.perform(get("/api/process-form-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processFormConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].processDefinitionKey").value(hasItem(DEFAULT_PROCESS_DEFINITION_KEY)))
            .andExpect(jsonPath("$.[*].taskNodeId").value(hasItem(DEFAULT_TASK_NODE_ID)))
            .andExpect(jsonPath("$.[*].taskNodeName").value(hasItem(DEFAULT_TASK_NODE_NAME)))
            .andExpect(jsonPath("$.[*].commonTableId").value(hasItem(DEFAULT_COMMON_TABLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].formData").value(hasItem(DEFAULT_FORM_DATA.toString())));

        // Check, that the count call also returns 1
        restProcessFormConfigMockMvc.perform(get("/api/process-form-configs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessFormConfigShouldNotBeFound(String filter) throws Exception {
        restProcessFormConfigMockMvc.perform(get("/api/process-form-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessFormConfigMockMvc.perform(get("/api/process-form-configs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProcessFormConfig() throws Exception {
        // Get the processFormConfig
        restProcessFormConfigMockMvc.perform(get("/api/process-form-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessFormConfig() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        int databaseSizeBeforeUpdate = processFormConfigRepository.findAll().size();

        // Update the processFormConfig
        ProcessFormConfig updatedProcessFormConfig = processFormConfigRepository.findById(processFormConfig.getId()).get();
        // Disconnect from session so that the updates on updatedProcessFormConfig are not directly saved in db
        em.detach(updatedProcessFormConfig);
        updatedProcessFormConfig
            .processDefinitionKey(UPDATED_PROCESS_DEFINITION_KEY)
            .taskNodeId(UPDATED_TASK_NODE_ID)
            .taskNodeName(UPDATED_TASK_NODE_NAME)
            .commonTableId(UPDATED_COMMON_TABLE_ID)
            .formData(UPDATED_FORM_DATA);
        ProcessFormConfigDTO processFormConfigDTO = processFormConfigMapper.toDto(updatedProcessFormConfig);

        restProcessFormConfigMockMvc.perform(put("/api/process-form-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processFormConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ProcessFormConfig in the database
        List<ProcessFormConfig> processFormConfigList = processFormConfigRepository.findAll();
        assertThat(processFormConfigList).hasSize(databaseSizeBeforeUpdate);
        ProcessFormConfig testProcessFormConfig = processFormConfigList.get(processFormConfigList.size() - 1);
        assertThat(testProcessFormConfig.getProcessDefinitionKey()).isEqualTo(UPDATED_PROCESS_DEFINITION_KEY);
        assertThat(testProcessFormConfig.getTaskNodeId()).isEqualTo(UPDATED_TASK_NODE_ID);
        assertThat(testProcessFormConfig.getTaskNodeName()).isEqualTo(UPDATED_TASK_NODE_NAME);
        assertThat(testProcessFormConfig.getCommonTableId()).isEqualTo(UPDATED_COMMON_TABLE_ID);
        assertThat(testProcessFormConfig.getFormData()).isEqualTo(UPDATED_FORM_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessFormConfig() throws Exception {
        int databaseSizeBeforeUpdate = processFormConfigRepository.findAll().size();

        // Create the ProcessFormConfig
        ProcessFormConfigDTO processFormConfigDTO = processFormConfigMapper.toDto(processFormConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessFormConfigMockMvc.perform(put("/api/process-form-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processFormConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessFormConfig in the database
        List<ProcessFormConfig> processFormConfigList = processFormConfigRepository.findAll();
        assertThat(processFormConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessFormConfig() throws Exception {
        // Initialize the database
        processFormConfigRepository.saveAndFlush(processFormConfig);

        int databaseSizeBeforeDelete = processFormConfigRepository.findAll().size();

        // Delete the processFormConfig
        restProcessFormConfigMockMvc.perform(delete("/api/process-form-configs/{id}", processFormConfig.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessFormConfig> processFormConfigList = processFormConfigRepository.findAll();
        assertThat(processFormConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
