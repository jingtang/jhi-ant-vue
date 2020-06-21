package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.UReportFile;
import com.aidriveall.cms.domain.CommonTable;
import com.aidriveall.cms.repository.UReportFileRepository;
import com.aidriveall.cms.service.UReportFileService;
import com.aidriveall.cms.service.dto.UReportFileDTO;
import com.aidriveall.cms.service.mapper.UReportFileMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.UReportFileCriteria;
import com.aidriveall.cms.service.UReportFileQueryService;

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
 * Integration tests for the {@link UReportFileResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class UReportFileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_UPDATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_UPDATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private UReportFileRepository uReportFileRepository;

    @Autowired
    private UReportFileMapper uReportFileMapper;

    @Autowired
    private UReportFileService uReportFileService;

    @Autowired
    private UReportFileQueryService uReportFileQueryService;

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

    private MockMvc restUReportFileMockMvc;

    private UReportFile uReportFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UReportFileResource uReportFileResource = new UReportFileResource(uReportFileService, uReportFileQueryService);
        this.restUReportFileMockMvc = MockMvcBuilders.standaloneSetup(uReportFileResource)
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
    public static UReportFile createEntity(EntityManager em) {
        UReportFile uReportFile = new UReportFile()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT)
            .createAt(DEFAULT_CREATE_AT)
            .updateAt(DEFAULT_UPDATE_AT);
        return uReportFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UReportFile createUpdatedEntity(EntityManager em) {
        UReportFile uReportFile = new UReportFile()
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        return uReportFile;
    }

    @BeforeEach
    public void initTest() {
        uReportFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUReportFile() throws Exception {
        int databaseSizeBeforeCreate = uReportFileRepository.findAll().size();

        // Create the UReportFile
        UReportFileDTO uReportFileDTO = uReportFileMapper.toDto(uReportFile);
        restUReportFileMockMvc.perform(post("/api/u-report-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uReportFileDTO)))
            .andExpect(status().isCreated());

        // Validate the UReportFile in the database
        List<UReportFile> uReportFileList = uReportFileRepository.findAll();
        assertThat(uReportFileList).hasSize(databaseSizeBeforeCreate + 1);
        UReportFile testUReportFile = uReportFileList.get(uReportFileList.size() - 1);
        assertThat(testUReportFile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUReportFile.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testUReportFile.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testUReportFile.getUpdateAt()).isEqualTo(DEFAULT_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createUReportFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uReportFileRepository.findAll().size();

        // Create the UReportFile with an existing ID
        uReportFile.setId(1L);
        UReportFileDTO uReportFileDTO = uReportFileMapper.toDto(uReportFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUReportFileMockMvc.perform(post("/api/u-report-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uReportFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UReportFile in the database
        List<UReportFile> uReportFileList = uReportFileRepository.findAll();
        assertThat(uReportFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUReportFiles() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList
        restUReportFileMockMvc.perform(get("/api/u-report-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uReportFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(sameInstant(DEFAULT_CREATE_AT))))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(sameInstant(DEFAULT_UPDATE_AT))));
    }
    
    @Test
    @Transactional
    public void getUReportFile() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get the uReportFile
        restUReportFileMockMvc.perform(get("/api/u-report-files/{id}", uReportFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uReportFile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.createAt").value(sameInstant(DEFAULT_CREATE_AT)))
            .andExpect(jsonPath("$.updateAt").value(sameInstant(DEFAULT_UPDATE_AT)));
    }


    @Test
    @Transactional
    public void getUReportFilesByIdFiltering() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        Long id = uReportFile.getId();

        defaultUReportFileShouldBeFound("id.equals=" + id);
        defaultUReportFileShouldNotBeFound("id.notEquals=" + id);

        defaultUReportFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUReportFileShouldNotBeFound("id.greaterThan=" + id);

        defaultUReportFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUReportFileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUReportFilesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where name equals to DEFAULT_NAME
        defaultUReportFileShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the uReportFileList where name equals to UPDATED_NAME
        defaultUReportFileShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where name not equals to DEFAULT_NAME
        defaultUReportFileShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the uReportFileList where name not equals to UPDATED_NAME
        defaultUReportFileShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUReportFileShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the uReportFileList where name equals to UPDATED_NAME
        defaultUReportFileShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where name is not null
        defaultUReportFileShouldBeFound("name.specified=true");

        // Get all the uReportFileList where name is null
        defaultUReportFileShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUReportFilesByNameContainsSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where name contains DEFAULT_NAME
        defaultUReportFileShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the uReportFileList where name contains UPDATED_NAME
        defaultUReportFileShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where name does not contain DEFAULT_NAME
        defaultUReportFileShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the uReportFileList where name does not contain UPDATED_NAME
        defaultUReportFileShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt equals to DEFAULT_CREATE_AT
        defaultUReportFileShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the uReportFileList where createAt equals to UPDATED_CREATE_AT
        defaultUReportFileShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt not equals to DEFAULT_CREATE_AT
        defaultUReportFileShouldNotBeFound("createAt.notEquals=" + DEFAULT_CREATE_AT);

        // Get all the uReportFileList where createAt not equals to UPDATED_CREATE_AT
        defaultUReportFileShouldBeFound("createAt.notEquals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultUReportFileShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the uReportFileList where createAt equals to UPDATED_CREATE_AT
        defaultUReportFileShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt is not null
        defaultUReportFileShouldBeFound("createAt.specified=true");

        // Get all the uReportFileList where createAt is null
        defaultUReportFileShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt is greater than or equal to DEFAULT_CREATE_AT
        defaultUReportFileShouldBeFound("createAt.greaterThanOrEqual=" + DEFAULT_CREATE_AT);

        // Get all the uReportFileList where createAt is greater than or equal to UPDATED_CREATE_AT
        defaultUReportFileShouldNotBeFound("createAt.greaterThanOrEqual=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt is less than or equal to DEFAULT_CREATE_AT
        defaultUReportFileShouldBeFound("createAt.lessThanOrEqual=" + DEFAULT_CREATE_AT);

        // Get all the uReportFileList where createAt is less than or equal to SMALLER_CREATE_AT
        defaultUReportFileShouldNotBeFound("createAt.lessThanOrEqual=" + SMALLER_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt is less than DEFAULT_CREATE_AT
        defaultUReportFileShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the uReportFileList where createAt is less than UPDATED_CREATE_AT
        defaultUReportFileShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByCreateAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where createAt is greater than DEFAULT_CREATE_AT
        defaultUReportFileShouldNotBeFound("createAt.greaterThan=" + DEFAULT_CREATE_AT);

        // Get all the uReportFileList where createAt is greater than SMALLER_CREATE_AT
        defaultUReportFileShouldBeFound("createAt.greaterThan=" + SMALLER_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt equals to DEFAULT_UPDATE_AT
        defaultUReportFileShouldBeFound("updateAt.equals=" + DEFAULT_UPDATE_AT);

        // Get all the uReportFileList where updateAt equals to UPDATED_UPDATE_AT
        defaultUReportFileShouldNotBeFound("updateAt.equals=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt not equals to DEFAULT_UPDATE_AT
        defaultUReportFileShouldNotBeFound("updateAt.notEquals=" + DEFAULT_UPDATE_AT);

        // Get all the uReportFileList where updateAt not equals to UPDATED_UPDATE_AT
        defaultUReportFileShouldBeFound("updateAt.notEquals=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsInShouldWork() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt in DEFAULT_UPDATE_AT or UPDATED_UPDATE_AT
        defaultUReportFileShouldBeFound("updateAt.in=" + DEFAULT_UPDATE_AT + "," + UPDATED_UPDATE_AT);

        // Get all the uReportFileList where updateAt equals to UPDATED_UPDATE_AT
        defaultUReportFileShouldNotBeFound("updateAt.in=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt is not null
        defaultUReportFileShouldBeFound("updateAt.specified=true");

        // Get all the uReportFileList where updateAt is null
        defaultUReportFileShouldNotBeFound("updateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt is greater than or equal to DEFAULT_UPDATE_AT
        defaultUReportFileShouldBeFound("updateAt.greaterThanOrEqual=" + DEFAULT_UPDATE_AT);

        // Get all the uReportFileList where updateAt is greater than or equal to UPDATED_UPDATE_AT
        defaultUReportFileShouldNotBeFound("updateAt.greaterThanOrEqual=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt is less than or equal to DEFAULT_UPDATE_AT
        defaultUReportFileShouldBeFound("updateAt.lessThanOrEqual=" + DEFAULT_UPDATE_AT);

        // Get all the uReportFileList where updateAt is less than or equal to SMALLER_UPDATE_AT
        defaultUReportFileShouldNotBeFound("updateAt.lessThanOrEqual=" + SMALLER_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt is less than DEFAULT_UPDATE_AT
        defaultUReportFileShouldNotBeFound("updateAt.lessThan=" + DEFAULT_UPDATE_AT);

        // Get all the uReportFileList where updateAt is less than UPDATED_UPDATE_AT
        defaultUReportFileShouldBeFound("updateAt.lessThan=" + UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllUReportFilesByUpdateAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        // Get all the uReportFileList where updateAt is greater than DEFAULT_UPDATE_AT
        defaultUReportFileShouldNotBeFound("updateAt.greaterThan=" + DEFAULT_UPDATE_AT);

        // Get all the uReportFileList where updateAt is greater than SMALLER_UPDATE_AT
        defaultUReportFileShouldBeFound("updateAt.greaterThan=" + SMALLER_UPDATE_AT);
    }


    @Test
    @Transactional
    public void getAllUReportFilesByCommonTableIsEqualToSomething() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);
        CommonTable commonTable = CommonTableResourceIT.createEntity(em);
        em.persist(commonTable);
        em.flush();
        uReportFile.setCommonTable(commonTable);
        uReportFileRepository.saveAndFlush(uReportFile);
        Long commonTableId = commonTable.getId();

        // Get all the uReportFileList where commonTable equals to commonTableId
        defaultUReportFileShouldBeFound("commonTableId.equals=" + commonTableId);

        // Get all the uReportFileList where commonTable equals to commonTableId + 1
        defaultUReportFileShouldNotBeFound("commonTableId.equals=" + (commonTableId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUReportFileShouldBeFound(String filter) throws Exception {
        restUReportFileMockMvc.perform(get("/api/u-report-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uReportFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(sameInstant(DEFAULT_CREATE_AT))))
            .andExpect(jsonPath("$.[*].updateAt").value(hasItem(sameInstant(DEFAULT_UPDATE_AT))));

        // Check, that the count call also returns 1
        restUReportFileMockMvc.perform(get("/api/u-report-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUReportFileShouldNotBeFound(String filter) throws Exception {
        restUReportFileMockMvc.perform(get("/api/u-report-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUReportFileMockMvc.perform(get("/api/u-report-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUReportFile() throws Exception {
        // Get the uReportFile
        restUReportFileMockMvc.perform(get("/api/u-report-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUReportFile() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        int databaseSizeBeforeUpdate = uReportFileRepository.findAll().size();

        // Update the uReportFile
        UReportFile updatedUReportFile = uReportFileRepository.findById(uReportFile.getId()).get();
        // Disconnect from session so that the updates on updatedUReportFile are not directly saved in db
        em.detach(updatedUReportFile);
        updatedUReportFile
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .createAt(UPDATED_CREATE_AT)
            .updateAt(UPDATED_UPDATE_AT);
        UReportFileDTO uReportFileDTO = uReportFileMapper.toDto(updatedUReportFile);

        restUReportFileMockMvc.perform(put("/api/u-report-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uReportFileDTO)))
            .andExpect(status().isOk());

        // Validate the UReportFile in the database
        List<UReportFile> uReportFileList = uReportFileRepository.findAll();
        assertThat(uReportFileList).hasSize(databaseSizeBeforeUpdate);
        UReportFile testUReportFile = uReportFileList.get(uReportFileList.size() - 1);
        assertThat(testUReportFile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUReportFile.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testUReportFile.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testUReportFile.getUpdateAt()).isEqualTo(UPDATED_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUReportFile() throws Exception {
        int databaseSizeBeforeUpdate = uReportFileRepository.findAll().size();

        // Create the UReportFile
        UReportFileDTO uReportFileDTO = uReportFileMapper.toDto(uReportFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUReportFileMockMvc.perform(put("/api/u-report-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uReportFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UReportFile in the database
        List<UReportFile> uReportFileList = uReportFileRepository.findAll();
        assertThat(uReportFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUReportFile() throws Exception {
        // Initialize the database
        uReportFileRepository.saveAndFlush(uReportFile);

        int databaseSizeBeforeDelete = uReportFileRepository.findAll().size();

        // Delete the uReportFile
        restUReportFileMockMvc.perform(delete("/api/u-report-files/{id}", uReportFile.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UReportFile> uReportFileList = uReportFileRepository.findAll();
        assertThat(uReportFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
