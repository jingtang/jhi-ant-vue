package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.ProcessEntityRelation;
import com.aidriveall.cms.repository.ProcessEntityRelationRepository;
import com.aidriveall.cms.service.ProcessEntityRelationService;
import com.aidriveall.cms.service.dto.ProcessEntityRelationDTO;
import com.aidriveall.cms.service.mapper.ProcessEntityRelationMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.ProcessEntityRelationCriteria;
import com.aidriveall.cms.service.ProcessEntityRelationQueryService;

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

import com.aidriveall.cms.domain.enumeration.ProcessEntityStatus;
/**
 * Integration tests for the {@link ProcessEntityRelationResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class ProcessEntityRelationResourceIT {

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_ENTITY_ID = 1L;
    private static final Long UPDATED_ENTITY_ID = 2L;
    private static final Long SMALLER_ENTITY_ID = 1L - 1L;

    private static final String DEFAULT_PROCESS_INSTANCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROCESS_INSTANCE_ID = "BBBBBBBBBB";

    private static final ProcessEntityStatus DEFAULT_STATUS = ProcessEntityStatus.START;
    private static final ProcessEntityStatus UPDATED_STATUS = ProcessEntityStatus.RUNNING;

    @Autowired
    private ProcessEntityRelationRepository processEntityRelationRepository;

    @Autowired
    private ProcessEntityRelationMapper processEntityRelationMapper;

    @Autowired
    private ProcessEntityRelationService processEntityRelationService;

    @Autowired
    private ProcessEntityRelationQueryService processEntityRelationQueryService;

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

    private MockMvc restProcessEntityRelationMockMvc;

    private ProcessEntityRelation processEntityRelation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessEntityRelationResource processEntityRelationResource = new ProcessEntityRelationResource(processEntityRelationService, processEntityRelationQueryService);
        this.restProcessEntityRelationMockMvc = MockMvcBuilders.standaloneSetup(processEntityRelationResource)
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
    public static ProcessEntityRelation createEntity(EntityManager em) {
        ProcessEntityRelation processEntityRelation = new ProcessEntityRelation()
            .entityType(DEFAULT_ENTITY_TYPE)
            .entityId(DEFAULT_ENTITY_ID)
            .processInstanceId(DEFAULT_PROCESS_INSTANCE_ID)
            .status(DEFAULT_STATUS);
        return processEntityRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessEntityRelation createUpdatedEntity(EntityManager em) {
        ProcessEntityRelation processEntityRelation = new ProcessEntityRelation()
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .status(UPDATED_STATUS);
        return processEntityRelation;
    }

    @BeforeEach
    public void initTest() {
        processEntityRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcessEntityRelation() throws Exception {
        int databaseSizeBeforeCreate = processEntityRelationRepository.findAll().size();

        // Create the ProcessEntityRelation
        ProcessEntityRelationDTO processEntityRelationDTO = processEntityRelationMapper.toDto(processEntityRelation);
        restProcessEntityRelationMockMvc.perform(post("/api/process-entity-relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processEntityRelationDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcessEntityRelation in the database
        List<ProcessEntityRelation> processEntityRelationList = processEntityRelationRepository.findAll();
        assertThat(processEntityRelationList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessEntityRelation testProcessEntityRelation = processEntityRelationList.get(processEntityRelationList.size() - 1);
        assertThat(testProcessEntityRelation.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testProcessEntityRelation.getEntityId()).isEqualTo(DEFAULT_ENTITY_ID);
        assertThat(testProcessEntityRelation.getProcessInstanceId()).isEqualTo(DEFAULT_PROCESS_INSTANCE_ID);
        assertThat(testProcessEntityRelation.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createProcessEntityRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processEntityRelationRepository.findAll().size();

        // Create the ProcessEntityRelation with an existing ID
        processEntityRelation.setId(1L);
        ProcessEntityRelationDTO processEntityRelationDTO = processEntityRelationMapper.toDto(processEntityRelation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessEntityRelationMockMvc.perform(post("/api/process-entity-relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processEntityRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessEntityRelation in the database
        List<ProcessEntityRelation> processEntityRelationList = processEntityRelationRepository.findAll();
        assertThat(processEntityRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessEntityRelations() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList
        restProcessEntityRelationMockMvc.perform(get("/api/process-entity-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processEntityRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getProcessEntityRelation() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get the processEntityRelation
        restProcessEntityRelationMockMvc.perform(get("/api/process-entity-relations/{id}", processEntityRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processEntityRelation.getId().intValue()))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE))
            .andExpect(jsonPath("$.entityId").value(DEFAULT_ENTITY_ID.intValue()))
            .andExpect(jsonPath("$.processInstanceId").value(DEFAULT_PROCESS_INSTANCE_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getProcessEntityRelationsByIdFiltering() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        Long id = processEntityRelation.getId();

        defaultProcessEntityRelationShouldBeFound("id.equals=" + id);
        defaultProcessEntityRelationShouldNotBeFound("id.notEquals=" + id);

        defaultProcessEntityRelationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessEntityRelationShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessEntityRelationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessEntityRelationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityType equals to DEFAULT_ENTITY_TYPE
        defaultProcessEntityRelationShouldBeFound("entityType.equals=" + DEFAULT_ENTITY_TYPE);

        // Get all the processEntityRelationList where entityType equals to UPDATED_ENTITY_TYPE
        defaultProcessEntityRelationShouldNotBeFound("entityType.equals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityType not equals to DEFAULT_ENTITY_TYPE
        defaultProcessEntityRelationShouldNotBeFound("entityType.notEquals=" + DEFAULT_ENTITY_TYPE);

        // Get all the processEntityRelationList where entityType not equals to UPDATED_ENTITY_TYPE
        defaultProcessEntityRelationShouldBeFound("entityType.notEquals=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityTypeIsInShouldWork() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityType in DEFAULT_ENTITY_TYPE or UPDATED_ENTITY_TYPE
        defaultProcessEntityRelationShouldBeFound("entityType.in=" + DEFAULT_ENTITY_TYPE + "," + UPDATED_ENTITY_TYPE);

        // Get all the processEntityRelationList where entityType equals to UPDATED_ENTITY_TYPE
        defaultProcessEntityRelationShouldNotBeFound("entityType.in=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityType is not null
        defaultProcessEntityRelationShouldBeFound("entityType.specified=true");

        // Get all the processEntityRelationList where entityType is null
        defaultProcessEntityRelationShouldNotBeFound("entityType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityTypeContainsSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityType contains DEFAULT_ENTITY_TYPE
        defaultProcessEntityRelationShouldBeFound("entityType.contains=" + DEFAULT_ENTITY_TYPE);

        // Get all the processEntityRelationList where entityType contains UPDATED_ENTITY_TYPE
        defaultProcessEntityRelationShouldNotBeFound("entityType.contains=" + UPDATED_ENTITY_TYPE);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityTypeNotContainsSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityType does not contain DEFAULT_ENTITY_TYPE
        defaultProcessEntityRelationShouldNotBeFound("entityType.doesNotContain=" + DEFAULT_ENTITY_TYPE);

        // Get all the processEntityRelationList where entityType does not contain UPDATED_ENTITY_TYPE
        defaultProcessEntityRelationShouldBeFound("entityType.doesNotContain=" + UPDATED_ENTITY_TYPE);
    }


    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId equals to DEFAULT_ENTITY_ID
        defaultProcessEntityRelationShouldBeFound("entityId.equals=" + DEFAULT_ENTITY_ID);

        // Get all the processEntityRelationList where entityId equals to UPDATED_ENTITY_ID
        defaultProcessEntityRelationShouldNotBeFound("entityId.equals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId not equals to DEFAULT_ENTITY_ID
        defaultProcessEntityRelationShouldNotBeFound("entityId.notEquals=" + DEFAULT_ENTITY_ID);

        // Get all the processEntityRelationList where entityId not equals to UPDATED_ENTITY_ID
        defaultProcessEntityRelationShouldBeFound("entityId.notEquals=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsInShouldWork() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId in DEFAULT_ENTITY_ID or UPDATED_ENTITY_ID
        defaultProcessEntityRelationShouldBeFound("entityId.in=" + DEFAULT_ENTITY_ID + "," + UPDATED_ENTITY_ID);

        // Get all the processEntityRelationList where entityId equals to UPDATED_ENTITY_ID
        defaultProcessEntityRelationShouldNotBeFound("entityId.in=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId is not null
        defaultProcessEntityRelationShouldBeFound("entityId.specified=true");

        // Get all the processEntityRelationList where entityId is null
        defaultProcessEntityRelationShouldNotBeFound("entityId.specified=false");
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId is greater than or equal to DEFAULT_ENTITY_ID
        defaultProcessEntityRelationShouldBeFound("entityId.greaterThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the processEntityRelationList where entityId is greater than or equal to UPDATED_ENTITY_ID
        defaultProcessEntityRelationShouldNotBeFound("entityId.greaterThanOrEqual=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId is less than or equal to DEFAULT_ENTITY_ID
        defaultProcessEntityRelationShouldBeFound("entityId.lessThanOrEqual=" + DEFAULT_ENTITY_ID);

        // Get all the processEntityRelationList where entityId is less than or equal to SMALLER_ENTITY_ID
        defaultProcessEntityRelationShouldNotBeFound("entityId.lessThanOrEqual=" + SMALLER_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId is less than DEFAULT_ENTITY_ID
        defaultProcessEntityRelationShouldNotBeFound("entityId.lessThan=" + DEFAULT_ENTITY_ID);

        // Get all the processEntityRelationList where entityId is less than UPDATED_ENTITY_ID
        defaultProcessEntityRelationShouldBeFound("entityId.lessThan=" + UPDATED_ENTITY_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByEntityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where entityId is greater than DEFAULT_ENTITY_ID
        defaultProcessEntityRelationShouldNotBeFound("entityId.greaterThan=" + DEFAULT_ENTITY_ID);

        // Get all the processEntityRelationList where entityId is greater than SMALLER_ENTITY_ID
        defaultProcessEntityRelationShouldBeFound("entityId.greaterThan=" + SMALLER_ENTITY_ID);
    }


    @Test
    @Transactional
    public void getAllProcessEntityRelationsByProcessInstanceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where processInstanceId equals to DEFAULT_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldBeFound("processInstanceId.equals=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the processEntityRelationList where processInstanceId equals to UPDATED_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldNotBeFound("processInstanceId.equals=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByProcessInstanceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where processInstanceId not equals to DEFAULT_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldNotBeFound("processInstanceId.notEquals=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the processEntityRelationList where processInstanceId not equals to UPDATED_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldBeFound("processInstanceId.notEquals=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByProcessInstanceIdIsInShouldWork() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where processInstanceId in DEFAULT_PROCESS_INSTANCE_ID or UPDATED_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldBeFound("processInstanceId.in=" + DEFAULT_PROCESS_INSTANCE_ID + "," + UPDATED_PROCESS_INSTANCE_ID);

        // Get all the processEntityRelationList where processInstanceId equals to UPDATED_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldNotBeFound("processInstanceId.in=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByProcessInstanceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where processInstanceId is not null
        defaultProcessEntityRelationShouldBeFound("processInstanceId.specified=true");

        // Get all the processEntityRelationList where processInstanceId is null
        defaultProcessEntityRelationShouldNotBeFound("processInstanceId.specified=false");
    }
                @Test
    @Transactional
    public void getAllProcessEntityRelationsByProcessInstanceIdContainsSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where processInstanceId contains DEFAULT_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldBeFound("processInstanceId.contains=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the processEntityRelationList where processInstanceId contains UPDATED_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldNotBeFound("processInstanceId.contains=" + UPDATED_PROCESS_INSTANCE_ID);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByProcessInstanceIdNotContainsSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where processInstanceId does not contain DEFAULT_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldNotBeFound("processInstanceId.doesNotContain=" + DEFAULT_PROCESS_INSTANCE_ID);

        // Get all the processEntityRelationList where processInstanceId does not contain UPDATED_PROCESS_INSTANCE_ID
        defaultProcessEntityRelationShouldBeFound("processInstanceId.doesNotContain=" + UPDATED_PROCESS_INSTANCE_ID);
    }


    @Test
    @Transactional
    public void getAllProcessEntityRelationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where status equals to DEFAULT_STATUS
        defaultProcessEntityRelationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the processEntityRelationList where status equals to UPDATED_STATUS
        defaultProcessEntityRelationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where status not equals to DEFAULT_STATUS
        defaultProcessEntityRelationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the processEntityRelationList where status not equals to UPDATED_STATUS
        defaultProcessEntityRelationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProcessEntityRelationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the processEntityRelationList where status equals to UPDATED_STATUS
        defaultProcessEntityRelationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllProcessEntityRelationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        // Get all the processEntityRelationList where status is not null
        defaultProcessEntityRelationShouldBeFound("status.specified=true");

        // Get all the processEntityRelationList where status is null
        defaultProcessEntityRelationShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessEntityRelationShouldBeFound(String filter) throws Exception {
        restProcessEntityRelationMockMvc.perform(get("/api/process-entity-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processEntityRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].entityId").value(hasItem(DEFAULT_ENTITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].processInstanceId").value(hasItem(DEFAULT_PROCESS_INSTANCE_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restProcessEntityRelationMockMvc.perform(get("/api/process-entity-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessEntityRelationShouldNotBeFound(String filter) throws Exception {
        restProcessEntityRelationMockMvc.perform(get("/api/process-entity-relations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessEntityRelationMockMvc.perform(get("/api/process-entity-relations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProcessEntityRelation() throws Exception {
        // Get the processEntityRelation
        restProcessEntityRelationMockMvc.perform(get("/api/process-entity-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcessEntityRelation() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        int databaseSizeBeforeUpdate = processEntityRelationRepository.findAll().size();

        // Update the processEntityRelation
        ProcessEntityRelation updatedProcessEntityRelation = processEntityRelationRepository.findById(processEntityRelation.getId()).get();
        // Disconnect from session so that the updates on updatedProcessEntityRelation are not directly saved in db
        em.detach(updatedProcessEntityRelation);
        updatedProcessEntityRelation
            .entityType(UPDATED_ENTITY_TYPE)
            .entityId(UPDATED_ENTITY_ID)
            .processInstanceId(UPDATED_PROCESS_INSTANCE_ID)
            .status(UPDATED_STATUS);
        ProcessEntityRelationDTO processEntityRelationDTO = processEntityRelationMapper.toDto(updatedProcessEntityRelation);

        restProcessEntityRelationMockMvc.perform(put("/api/process-entity-relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processEntityRelationDTO)))
            .andExpect(status().isOk());

        // Validate the ProcessEntityRelation in the database
        List<ProcessEntityRelation> processEntityRelationList = processEntityRelationRepository.findAll();
        assertThat(processEntityRelationList).hasSize(databaseSizeBeforeUpdate);
        ProcessEntityRelation testProcessEntityRelation = processEntityRelationList.get(processEntityRelationList.size() - 1);
        assertThat(testProcessEntityRelation.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testProcessEntityRelation.getEntityId()).isEqualTo(UPDATED_ENTITY_ID);
        assertThat(testProcessEntityRelation.getProcessInstanceId()).isEqualTo(UPDATED_PROCESS_INSTANCE_ID);
        assertThat(testProcessEntityRelation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingProcessEntityRelation() throws Exception {
        int databaseSizeBeforeUpdate = processEntityRelationRepository.findAll().size();

        // Create the ProcessEntityRelation
        ProcessEntityRelationDTO processEntityRelationDTO = processEntityRelationMapper.toDto(processEntityRelation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessEntityRelationMockMvc.perform(put("/api/process-entity-relations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processEntityRelationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcessEntityRelation in the database
        List<ProcessEntityRelation> processEntityRelationList = processEntityRelationRepository.findAll();
        assertThat(processEntityRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcessEntityRelation() throws Exception {
        // Initialize the database
        processEntityRelationRepository.saveAndFlush(processEntityRelation);

        int databaseSizeBeforeDelete = processEntityRelationRepository.findAll().size();

        // Delete the processEntityRelation
        restProcessEntityRelationMockMvc.perform(delete("/api/process-entity-relations/{id}", processEntityRelation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessEntityRelation> processEntityRelationList = processEntityRelationRepository.findAll();
        assertThat(processEntityRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
