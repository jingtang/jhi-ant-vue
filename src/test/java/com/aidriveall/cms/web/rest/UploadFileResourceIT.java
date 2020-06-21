package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.UploadFile;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.repository.UploadFileRepository;
import com.aidriveall.cms.service.UploadFileService;
import com.aidriveall.cms.service.dto.UploadFileDTO;
import com.aidriveall.cms.service.mapper.UploadFileMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.UploadFileCriteria;
import com.aidriveall.cms.service.UploadFileQueryService;

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
 * Integration tests for the {@link UploadFileResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class UploadFileResourceIT {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXT = "AAAAAAAAAA";
    private static final String UPDATED_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATE_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;
    private static final Long SMALLER_FILE_SIZE = 1L - 1L;

    private static final Long DEFAULT_REFERENCE_COUNT = 1L;
    private static final Long UPDATED_REFERENCE_COUNT = 2L;
    private static final Long SMALLER_REFERENCE_COUNT = 1L - 1L;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private UploadFileQueryService uploadFileQueryService;

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

    private MockMvc restUploadFileMockMvc;

    private UploadFile uploadFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadFileResource uploadFileResource = new UploadFileResource(uploadFileService, uploadFileQueryService);
        this.restUploadFileMockMvc = MockMvcBuilders.standaloneSetup(uploadFileResource)
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
    public static UploadFile createEntity(EntityManager em) {
        UploadFile uploadFile = new UploadFile()
            .fullName(DEFAULT_FULL_NAME)
            .name(DEFAULT_NAME)
            .ext(DEFAULT_EXT)
            .type(DEFAULT_TYPE)
            .url(DEFAULT_URL)
            .path(DEFAULT_PATH)
            .folder(DEFAULT_FOLDER)
            .entityName(DEFAULT_ENTITY_NAME)
            .createAt(DEFAULT_CREATE_AT)
            .fileSize(DEFAULT_FILE_SIZE)
            .referenceCount(DEFAULT_REFERENCE_COUNT);
        return uploadFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UploadFile createUpdatedEntity(EntityManager em) {
        UploadFile uploadFile = new UploadFile()
            .fullName(UPDATED_FULL_NAME)
            .name(UPDATED_NAME)
            .ext(UPDATED_EXT)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .path(UPDATED_PATH)
            .folder(UPDATED_FOLDER)
            .entityName(UPDATED_ENTITY_NAME)
            .createAt(UPDATED_CREATE_AT)
            .fileSize(UPDATED_FILE_SIZE)
            .referenceCount(UPDATED_REFERENCE_COUNT);
        return uploadFile;
    }

    @BeforeEach
    public void initTest() {
        uploadFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadFile() throws Exception {
        int databaseSizeBeforeCreate = uploadFileRepository.findAll().size();

        // Create the UploadFile
        UploadFileDTO uploadFileDTO = uploadFileMapper.toDto(uploadFile);
        restUploadFileMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadFile in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeCreate + 1);
        UploadFile testUploadFile = uploadFileList.get(uploadFileList.size() - 1);
        assertThat(testUploadFile.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testUploadFile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUploadFile.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testUploadFile.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUploadFile.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testUploadFile.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testUploadFile.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testUploadFile.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testUploadFile.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testUploadFile.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testUploadFile.getReferenceCount()).isEqualTo(DEFAULT_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void createUploadFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadFileRepository.findAll().size();

        // Create the UploadFile with an existing ID
        uploadFile.setId(1L);
        UploadFileDTO uploadFileDTO = uploadFileMapper.toDto(uploadFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadFileMockMvc.perform(post("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadFile in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUploadFiles() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList
        restUploadFileMockMvc.perform(get("/api/upload-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(sameInstant(DEFAULT_CREATE_AT))))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].referenceCount").value(hasItem(DEFAULT_REFERENCE_COUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getUploadFile() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get the uploadFile
        restUploadFileMockMvc.perform(get("/api/upload-files/{id}", uploadFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uploadFile.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.createAt").value(sameInstant(DEFAULT_CREATE_AT)))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.referenceCount").value(DEFAULT_REFERENCE_COUNT.intValue()));
    }


    @Test
    @Transactional
    public void getUploadFilesByIdFiltering() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        Long id = uploadFile.getId();

        defaultUploadFileShouldBeFound("id.equals=" + id);
        defaultUploadFileShouldNotBeFound("id.notEquals=" + id);

        defaultUploadFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUploadFileShouldNotBeFound("id.greaterThan=" + id);

        defaultUploadFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUploadFileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fullName equals to DEFAULT_FULL_NAME
        defaultUploadFileShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the uploadFileList where fullName equals to UPDATED_FULL_NAME
        defaultUploadFileShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fullName not equals to DEFAULT_FULL_NAME
        defaultUploadFileShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the uploadFileList where fullName not equals to UPDATED_FULL_NAME
        defaultUploadFileShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultUploadFileShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the uploadFileList where fullName equals to UPDATED_FULL_NAME
        defaultUploadFileShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fullName is not null
        defaultUploadFileShouldBeFound("fullName.specified=true");

        // Get all the uploadFileList where fullName is null
        defaultUploadFileShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByFullNameContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fullName contains DEFAULT_FULL_NAME
        defaultUploadFileShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the uploadFileList where fullName contains UPDATED_FULL_NAME
        defaultUploadFileShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fullName does not contain DEFAULT_FULL_NAME
        defaultUploadFileShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the uploadFileList where fullName does not contain UPDATED_FULL_NAME
        defaultUploadFileShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where name equals to DEFAULT_NAME
        defaultUploadFileShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the uploadFileList where name equals to UPDATED_NAME
        defaultUploadFileShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where name not equals to DEFAULT_NAME
        defaultUploadFileShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the uploadFileList where name not equals to UPDATED_NAME
        defaultUploadFileShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUploadFileShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the uploadFileList where name equals to UPDATED_NAME
        defaultUploadFileShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where name is not null
        defaultUploadFileShouldBeFound("name.specified=true");

        // Get all the uploadFileList where name is null
        defaultUploadFileShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByNameContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where name contains DEFAULT_NAME
        defaultUploadFileShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the uploadFileList where name contains UPDATED_NAME
        defaultUploadFileShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where name does not contain DEFAULT_NAME
        defaultUploadFileShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the uploadFileList where name does not contain UPDATED_NAME
        defaultUploadFileShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByExtIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where ext equals to DEFAULT_EXT
        defaultUploadFileShouldBeFound("ext.equals=" + DEFAULT_EXT);

        // Get all the uploadFileList where ext equals to UPDATED_EXT
        defaultUploadFileShouldNotBeFound("ext.equals=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByExtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where ext not equals to DEFAULT_EXT
        defaultUploadFileShouldNotBeFound("ext.notEquals=" + DEFAULT_EXT);

        // Get all the uploadFileList where ext not equals to UPDATED_EXT
        defaultUploadFileShouldBeFound("ext.notEquals=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByExtIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where ext in DEFAULT_EXT or UPDATED_EXT
        defaultUploadFileShouldBeFound("ext.in=" + DEFAULT_EXT + "," + UPDATED_EXT);

        // Get all the uploadFileList where ext equals to UPDATED_EXT
        defaultUploadFileShouldNotBeFound("ext.in=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where ext is not null
        defaultUploadFileShouldBeFound("ext.specified=true");

        // Get all the uploadFileList where ext is null
        defaultUploadFileShouldNotBeFound("ext.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByExtContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where ext contains DEFAULT_EXT
        defaultUploadFileShouldBeFound("ext.contains=" + DEFAULT_EXT);

        // Get all the uploadFileList where ext contains UPDATED_EXT
        defaultUploadFileShouldNotBeFound("ext.contains=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByExtNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where ext does not contain DEFAULT_EXT
        defaultUploadFileShouldNotBeFound("ext.doesNotContain=" + DEFAULT_EXT);

        // Get all the uploadFileList where ext does not contain UPDATED_EXT
        defaultUploadFileShouldBeFound("ext.doesNotContain=" + UPDATED_EXT);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where type equals to DEFAULT_TYPE
        defaultUploadFileShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the uploadFileList where type equals to UPDATED_TYPE
        defaultUploadFileShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where type not equals to DEFAULT_TYPE
        defaultUploadFileShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the uploadFileList where type not equals to UPDATED_TYPE
        defaultUploadFileShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultUploadFileShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the uploadFileList where type equals to UPDATED_TYPE
        defaultUploadFileShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where type is not null
        defaultUploadFileShouldBeFound("type.specified=true");

        // Get all the uploadFileList where type is null
        defaultUploadFileShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByTypeContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where type contains DEFAULT_TYPE
        defaultUploadFileShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the uploadFileList where type contains UPDATED_TYPE
        defaultUploadFileShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where type does not contain DEFAULT_TYPE
        defaultUploadFileShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the uploadFileList where type does not contain UPDATED_TYPE
        defaultUploadFileShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where url equals to DEFAULT_URL
        defaultUploadFileShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the uploadFileList where url equals to UPDATED_URL
        defaultUploadFileShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where url not equals to DEFAULT_URL
        defaultUploadFileShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the uploadFileList where url not equals to UPDATED_URL
        defaultUploadFileShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where url in DEFAULT_URL or UPDATED_URL
        defaultUploadFileShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the uploadFileList where url equals to UPDATED_URL
        defaultUploadFileShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where url is not null
        defaultUploadFileShouldBeFound("url.specified=true");

        // Get all the uploadFileList where url is null
        defaultUploadFileShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByUrlContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where url contains DEFAULT_URL
        defaultUploadFileShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the uploadFileList where url contains UPDATED_URL
        defaultUploadFileShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where url does not contain DEFAULT_URL
        defaultUploadFileShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the uploadFileList where url does not contain UPDATED_URL
        defaultUploadFileShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByPathIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where path equals to DEFAULT_PATH
        defaultUploadFileShouldBeFound("path.equals=" + DEFAULT_PATH);

        // Get all the uploadFileList where path equals to UPDATED_PATH
        defaultUploadFileShouldNotBeFound("path.equals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByPathIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where path not equals to DEFAULT_PATH
        defaultUploadFileShouldNotBeFound("path.notEquals=" + DEFAULT_PATH);

        // Get all the uploadFileList where path not equals to UPDATED_PATH
        defaultUploadFileShouldBeFound("path.notEquals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByPathIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where path in DEFAULT_PATH or UPDATED_PATH
        defaultUploadFileShouldBeFound("path.in=" + DEFAULT_PATH + "," + UPDATED_PATH);

        // Get all the uploadFileList where path equals to UPDATED_PATH
        defaultUploadFileShouldNotBeFound("path.in=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where path is not null
        defaultUploadFileShouldBeFound("path.specified=true");

        // Get all the uploadFileList where path is null
        defaultUploadFileShouldNotBeFound("path.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByPathContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where path contains DEFAULT_PATH
        defaultUploadFileShouldBeFound("path.contains=" + DEFAULT_PATH);

        // Get all the uploadFileList where path contains UPDATED_PATH
        defaultUploadFileShouldNotBeFound("path.contains=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByPathNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where path does not contain DEFAULT_PATH
        defaultUploadFileShouldNotBeFound("path.doesNotContain=" + DEFAULT_PATH);

        // Get all the uploadFileList where path does not contain UPDATED_PATH
        defaultUploadFileShouldBeFound("path.doesNotContain=" + UPDATED_PATH);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByFolderIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where folder equals to DEFAULT_FOLDER
        defaultUploadFileShouldBeFound("folder.equals=" + DEFAULT_FOLDER);

        // Get all the uploadFileList where folder equals to UPDATED_FOLDER
        defaultUploadFileShouldNotBeFound("folder.equals=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFolderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where folder not equals to DEFAULT_FOLDER
        defaultUploadFileShouldNotBeFound("folder.notEquals=" + DEFAULT_FOLDER);

        // Get all the uploadFileList where folder not equals to UPDATED_FOLDER
        defaultUploadFileShouldBeFound("folder.notEquals=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFolderIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where folder in DEFAULT_FOLDER or UPDATED_FOLDER
        defaultUploadFileShouldBeFound("folder.in=" + DEFAULT_FOLDER + "," + UPDATED_FOLDER);

        // Get all the uploadFileList where folder equals to UPDATED_FOLDER
        defaultUploadFileShouldNotBeFound("folder.in=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFolderIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where folder is not null
        defaultUploadFileShouldBeFound("folder.specified=true");

        // Get all the uploadFileList where folder is null
        defaultUploadFileShouldNotBeFound("folder.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByFolderContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where folder contains DEFAULT_FOLDER
        defaultUploadFileShouldBeFound("folder.contains=" + DEFAULT_FOLDER);

        // Get all the uploadFileList where folder contains UPDATED_FOLDER
        defaultUploadFileShouldNotBeFound("folder.contains=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFolderNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where folder does not contain DEFAULT_FOLDER
        defaultUploadFileShouldNotBeFound("folder.doesNotContain=" + DEFAULT_FOLDER);

        // Get all the uploadFileList where folder does not contain UPDATED_FOLDER
        defaultUploadFileShouldBeFound("folder.doesNotContain=" + UPDATED_FOLDER);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where entityName equals to DEFAULT_ENTITY_NAME
        defaultUploadFileShouldBeFound("entityName.equals=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadFileList where entityName equals to UPDATED_ENTITY_NAME
        defaultUploadFileShouldNotBeFound("entityName.equals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByEntityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where entityName not equals to DEFAULT_ENTITY_NAME
        defaultUploadFileShouldNotBeFound("entityName.notEquals=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadFileList where entityName not equals to UPDATED_ENTITY_NAME
        defaultUploadFileShouldBeFound("entityName.notEquals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where entityName in DEFAULT_ENTITY_NAME or UPDATED_ENTITY_NAME
        defaultUploadFileShouldBeFound("entityName.in=" + DEFAULT_ENTITY_NAME + "," + UPDATED_ENTITY_NAME);

        // Get all the uploadFileList where entityName equals to UPDATED_ENTITY_NAME
        defaultUploadFileShouldNotBeFound("entityName.in=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where entityName is not null
        defaultUploadFileShouldBeFound("entityName.specified=true");

        // Get all the uploadFileList where entityName is null
        defaultUploadFileShouldNotBeFound("entityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadFilesByEntityNameContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where entityName contains DEFAULT_ENTITY_NAME
        defaultUploadFileShouldBeFound("entityName.contains=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadFileList where entityName contains UPDATED_ENTITY_NAME
        defaultUploadFileShouldNotBeFound("entityName.contains=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByEntityNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where entityName does not contain DEFAULT_ENTITY_NAME
        defaultUploadFileShouldNotBeFound("entityName.doesNotContain=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadFileList where entityName does not contain UPDATED_ENTITY_NAME
        defaultUploadFileShouldBeFound("entityName.doesNotContain=" + UPDATED_ENTITY_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt equals to DEFAULT_CREATE_AT
        defaultUploadFileShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the uploadFileList where createAt equals to UPDATED_CREATE_AT
        defaultUploadFileShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt not equals to DEFAULT_CREATE_AT
        defaultUploadFileShouldNotBeFound("createAt.notEquals=" + DEFAULT_CREATE_AT);

        // Get all the uploadFileList where createAt not equals to UPDATED_CREATE_AT
        defaultUploadFileShouldBeFound("createAt.notEquals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultUploadFileShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the uploadFileList where createAt equals to UPDATED_CREATE_AT
        defaultUploadFileShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt is not null
        defaultUploadFileShouldBeFound("createAt.specified=true");

        // Get all the uploadFileList where createAt is null
        defaultUploadFileShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt is greater than or equal to DEFAULT_CREATE_AT
        defaultUploadFileShouldBeFound("createAt.greaterThanOrEqual=" + DEFAULT_CREATE_AT);

        // Get all the uploadFileList where createAt is greater than or equal to UPDATED_CREATE_AT
        defaultUploadFileShouldNotBeFound("createAt.greaterThanOrEqual=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt is less than or equal to DEFAULT_CREATE_AT
        defaultUploadFileShouldBeFound("createAt.lessThanOrEqual=" + DEFAULT_CREATE_AT);

        // Get all the uploadFileList where createAt is less than or equal to SMALLER_CREATE_AT
        defaultUploadFileShouldNotBeFound("createAt.lessThanOrEqual=" + SMALLER_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt is less than DEFAULT_CREATE_AT
        defaultUploadFileShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the uploadFileList where createAt is less than UPDATED_CREATE_AT
        defaultUploadFileShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByCreateAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where createAt is greater than DEFAULT_CREATE_AT
        defaultUploadFileShouldNotBeFound("createAt.greaterThan=" + DEFAULT_CREATE_AT);

        // Get all the uploadFileList where createAt is greater than SMALLER_CREATE_AT
        defaultUploadFileShouldBeFound("createAt.greaterThan=" + SMALLER_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize equals to DEFAULT_FILE_SIZE
        defaultUploadFileShouldBeFound("fileSize.equals=" + DEFAULT_FILE_SIZE);

        // Get all the uploadFileList where fileSize equals to UPDATED_FILE_SIZE
        defaultUploadFileShouldNotBeFound("fileSize.equals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize not equals to DEFAULT_FILE_SIZE
        defaultUploadFileShouldNotBeFound("fileSize.notEquals=" + DEFAULT_FILE_SIZE);

        // Get all the uploadFileList where fileSize not equals to UPDATED_FILE_SIZE
        defaultUploadFileShouldBeFound("fileSize.notEquals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize in DEFAULT_FILE_SIZE or UPDATED_FILE_SIZE
        defaultUploadFileShouldBeFound("fileSize.in=" + DEFAULT_FILE_SIZE + "," + UPDATED_FILE_SIZE);

        // Get all the uploadFileList where fileSize equals to UPDATED_FILE_SIZE
        defaultUploadFileShouldNotBeFound("fileSize.in=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize is not null
        defaultUploadFileShouldBeFound("fileSize.specified=true");

        // Get all the uploadFileList where fileSize is null
        defaultUploadFileShouldNotBeFound("fileSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize is greater than or equal to DEFAULT_FILE_SIZE
        defaultUploadFileShouldBeFound("fileSize.greaterThanOrEqual=" + DEFAULT_FILE_SIZE);

        // Get all the uploadFileList where fileSize is greater than or equal to UPDATED_FILE_SIZE
        defaultUploadFileShouldNotBeFound("fileSize.greaterThanOrEqual=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize is less than or equal to DEFAULT_FILE_SIZE
        defaultUploadFileShouldBeFound("fileSize.lessThanOrEqual=" + DEFAULT_FILE_SIZE);

        // Get all the uploadFileList where fileSize is less than or equal to SMALLER_FILE_SIZE
        defaultUploadFileShouldNotBeFound("fileSize.lessThanOrEqual=" + SMALLER_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize is less than DEFAULT_FILE_SIZE
        defaultUploadFileShouldNotBeFound("fileSize.lessThan=" + DEFAULT_FILE_SIZE);

        // Get all the uploadFileList where fileSize is less than UPDATED_FILE_SIZE
        defaultUploadFileShouldBeFound("fileSize.lessThan=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByFileSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where fileSize is greater than DEFAULT_FILE_SIZE
        defaultUploadFileShouldNotBeFound("fileSize.greaterThan=" + DEFAULT_FILE_SIZE);

        // Get all the uploadFileList where fileSize is greater than SMALLER_FILE_SIZE
        defaultUploadFileShouldBeFound("fileSize.greaterThan=" + SMALLER_FILE_SIZE);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount equals to DEFAULT_REFERENCE_COUNT
        defaultUploadFileShouldBeFound("referenceCount.equals=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadFileList where referenceCount equals to UPDATED_REFERENCE_COUNT
        defaultUploadFileShouldNotBeFound("referenceCount.equals=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount not equals to DEFAULT_REFERENCE_COUNT
        defaultUploadFileShouldNotBeFound("referenceCount.notEquals=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadFileList where referenceCount not equals to UPDATED_REFERENCE_COUNT
        defaultUploadFileShouldBeFound("referenceCount.notEquals=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsInShouldWork() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount in DEFAULT_REFERENCE_COUNT or UPDATED_REFERENCE_COUNT
        defaultUploadFileShouldBeFound("referenceCount.in=" + DEFAULT_REFERENCE_COUNT + "," + UPDATED_REFERENCE_COUNT);

        // Get all the uploadFileList where referenceCount equals to UPDATED_REFERENCE_COUNT
        defaultUploadFileShouldNotBeFound("referenceCount.in=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount is not null
        defaultUploadFileShouldBeFound("referenceCount.specified=true");

        // Get all the uploadFileList where referenceCount is null
        defaultUploadFileShouldNotBeFound("referenceCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount is greater than or equal to DEFAULT_REFERENCE_COUNT
        defaultUploadFileShouldBeFound("referenceCount.greaterThanOrEqual=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadFileList where referenceCount is greater than or equal to UPDATED_REFERENCE_COUNT
        defaultUploadFileShouldNotBeFound("referenceCount.greaterThanOrEqual=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount is less than or equal to DEFAULT_REFERENCE_COUNT
        defaultUploadFileShouldBeFound("referenceCount.lessThanOrEqual=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadFileList where referenceCount is less than or equal to SMALLER_REFERENCE_COUNT
        defaultUploadFileShouldNotBeFound("referenceCount.lessThanOrEqual=" + SMALLER_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount is less than DEFAULT_REFERENCE_COUNT
        defaultUploadFileShouldNotBeFound("referenceCount.lessThan=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadFileList where referenceCount is less than UPDATED_REFERENCE_COUNT
        defaultUploadFileShouldBeFound("referenceCount.lessThan=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadFilesByReferenceCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        // Get all the uploadFileList where referenceCount is greater than DEFAULT_REFERENCE_COUNT
        defaultUploadFileShouldNotBeFound("referenceCount.greaterThan=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadFileList where referenceCount is greater than SMALLER_REFERENCE_COUNT
        defaultUploadFileShouldBeFound("referenceCount.greaterThan=" + SMALLER_REFERENCE_COUNT);
    }


    @Test
    @Transactional
    public void getAllUploadFilesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        uploadFile.setUser(user);
        uploadFileRepository.saveAndFlush(uploadFile);
        Long userId = user.getId();

        // Get all the uploadFileList where user equals to userId
        defaultUploadFileShouldBeFound("userId.equals=" + userId);

        // Get all the uploadFileList where user equals to userId + 1
        defaultUploadFileShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUploadFileShouldBeFound(String filter) throws Exception {
        restUploadFileMockMvc.perform(get("/api/upload-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER)))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(sameInstant(DEFAULT_CREATE_AT))))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].referenceCount").value(hasItem(DEFAULT_REFERENCE_COUNT.intValue())));

        // Check, that the count call also returns 1
        restUploadFileMockMvc.perform(get("/api/upload-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUploadFileShouldNotBeFound(String filter) throws Exception {
        restUploadFileMockMvc.perform(get("/api/upload-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUploadFileMockMvc.perform(get("/api/upload-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUploadFile() throws Exception {
        // Get the uploadFile
        restUploadFileMockMvc.perform(get("/api/upload-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadFile() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        int databaseSizeBeforeUpdate = uploadFileRepository.findAll().size();

        // Update the uploadFile
        UploadFile updatedUploadFile = uploadFileRepository.findById(uploadFile.getId()).get();
        // Disconnect from session so that the updates on updatedUploadFile are not directly saved in db
        em.detach(updatedUploadFile);
        updatedUploadFile
            .fullName(UPDATED_FULL_NAME)
            .name(UPDATED_NAME)
            .ext(UPDATED_EXT)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .path(UPDATED_PATH)
            .folder(UPDATED_FOLDER)
            .entityName(UPDATED_ENTITY_NAME)
            .createAt(UPDATED_CREATE_AT)
            .fileSize(UPDATED_FILE_SIZE)
            .referenceCount(UPDATED_REFERENCE_COUNT);
        UploadFileDTO uploadFileDTO = uploadFileMapper.toDto(updatedUploadFile);

        restUploadFileMockMvc.perform(put("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isOk());

        // Validate the UploadFile in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeUpdate);
        UploadFile testUploadFile = uploadFileList.get(uploadFileList.size() - 1);
        assertThat(testUploadFile.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testUploadFile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUploadFile.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testUploadFile.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUploadFile.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testUploadFile.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testUploadFile.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testUploadFile.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testUploadFile.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testUploadFile.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testUploadFile.getReferenceCount()).isEqualTo(UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = uploadFileRepository.findAll().size();

        // Create the UploadFile
        UploadFileDTO uploadFileDTO = uploadFileMapper.toDto(uploadFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadFileMockMvc.perform(put("/api/upload-files")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadFile in the database
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadFile() throws Exception {
        // Initialize the database
        uploadFileRepository.saveAndFlush(uploadFile);

        int databaseSizeBeforeDelete = uploadFileRepository.findAll().size();

        // Delete the uploadFile
        restUploadFileMockMvc.perform(delete("/api/upload-files/{id}", uploadFile.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UploadFile> uploadFileList = uploadFileRepository.findAll();
        assertThat(uploadFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
