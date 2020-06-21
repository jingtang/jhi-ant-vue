package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.UploadImage;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.repository.UploadImageRepository;
import com.aidriveall.cms.service.UploadImageService;
import com.aidriveall.cms.service.dto.UploadImageDTO;
import com.aidriveall.cms.service.mapper.UploadImageMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.UploadImageCriteria;
import com.aidriveall.cms.service.UploadImageQueryService;

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
 * Integration tests for the {@link UploadImageResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class UploadImageResourceIT {

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

    private static final String DEFAULT_SMART_URL = "AAAAAAAAAA";
    private static final String UPDATED_SMART_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIUM_URL = "AAAAAAAAAA";
    private static final String UPDATED_MEDIUM_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_REFERENCE_COUNT = 1L;
    private static final Long UPDATED_REFERENCE_COUNT = 2L;
    private static final Long SMALLER_REFERENCE_COUNT = 1L - 1L;

    @Autowired
    private UploadImageRepository uploadImageRepository;

    @Autowired
    private UploadImageMapper uploadImageMapper;

    @Autowired
    private UploadImageService uploadImageService;

    @Autowired
    private UploadImageQueryService uploadImageQueryService;

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

    private MockMvc restUploadImageMockMvc;

    private UploadImage uploadImage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UploadImageResource uploadImageResource = new UploadImageResource(uploadImageService, uploadImageQueryService);
        this.restUploadImageMockMvc = MockMvcBuilders.standaloneSetup(uploadImageResource)
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
    public static UploadImage createEntity(EntityManager em) {
        UploadImage uploadImage = new UploadImage()
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
            .smartUrl(DEFAULT_SMART_URL)
            .mediumUrl(DEFAULT_MEDIUM_URL)
            .referenceCount(DEFAULT_REFERENCE_COUNT);
        return uploadImage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UploadImage createUpdatedEntity(EntityManager em) {
        UploadImage uploadImage = new UploadImage()
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
            .smartUrl(UPDATED_SMART_URL)
            .mediumUrl(UPDATED_MEDIUM_URL)
            .referenceCount(UPDATED_REFERENCE_COUNT);
        return uploadImage;
    }

    @BeforeEach
    public void initTest() {
        uploadImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadImage() throws Exception {
        int databaseSizeBeforeCreate = uploadImageRepository.findAll().size();

        // Create the UploadImage
        UploadImageDTO uploadImageDTO = uploadImageMapper.toDto(uploadImage);
        restUploadImageMockMvc.perform(post("/api/upload-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadImageDTO)))
            .andExpect(status().isCreated());

        // Validate the UploadImage in the database
        List<UploadImage> uploadImageList = uploadImageRepository.findAll();
        assertThat(uploadImageList).hasSize(databaseSizeBeforeCreate + 1);
        UploadImage testUploadImage = uploadImageList.get(uploadImageList.size() - 1);
        assertThat(testUploadImage.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testUploadImage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUploadImage.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testUploadImage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUploadImage.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testUploadImage.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testUploadImage.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testUploadImage.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testUploadImage.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
        assertThat(testUploadImage.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testUploadImage.getSmartUrl()).isEqualTo(DEFAULT_SMART_URL);
        assertThat(testUploadImage.getMediumUrl()).isEqualTo(DEFAULT_MEDIUM_URL);
        assertThat(testUploadImage.getReferenceCount()).isEqualTo(DEFAULT_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void createUploadImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadImageRepository.findAll().size();

        // Create the UploadImage with an existing ID
        uploadImage.setId(1L);
        UploadImageDTO uploadImageDTO = uploadImageMapper.toDto(uploadImage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadImageMockMvc.perform(post("/api/upload-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadImage in the database
        List<UploadImage> uploadImageList = uploadImageRepository.findAll();
        assertThat(uploadImageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUploadImages() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList
        restUploadImageMockMvc.perform(get("/api/upload-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadImage.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].smartUrl").value(hasItem(DEFAULT_SMART_URL)))
            .andExpect(jsonPath("$.[*].mediumUrl").value(hasItem(DEFAULT_MEDIUM_URL)))
            .andExpect(jsonPath("$.[*].referenceCount").value(hasItem(DEFAULT_REFERENCE_COUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getUploadImage() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get the uploadImage
        restUploadImageMockMvc.perform(get("/api/upload-images/{id}", uploadImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uploadImage.getId().intValue()))
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
            .andExpect(jsonPath("$.smartUrl").value(DEFAULT_SMART_URL))
            .andExpect(jsonPath("$.mediumUrl").value(DEFAULT_MEDIUM_URL))
            .andExpect(jsonPath("$.referenceCount").value(DEFAULT_REFERENCE_COUNT.intValue()));
    }


    @Test
    @Transactional
    public void getUploadImagesByIdFiltering() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        Long id = uploadImage.getId();

        defaultUploadImageShouldBeFound("id.equals=" + id);
        defaultUploadImageShouldNotBeFound("id.notEquals=" + id);

        defaultUploadImageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUploadImageShouldNotBeFound("id.greaterThan=" + id);

        defaultUploadImageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUploadImageShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fullName equals to DEFAULT_FULL_NAME
        defaultUploadImageShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the uploadImageList where fullName equals to UPDATED_FULL_NAME
        defaultUploadImageShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFullNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fullName not equals to DEFAULT_FULL_NAME
        defaultUploadImageShouldNotBeFound("fullName.notEquals=" + DEFAULT_FULL_NAME);

        // Get all the uploadImageList where fullName not equals to UPDATED_FULL_NAME
        defaultUploadImageShouldBeFound("fullName.notEquals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultUploadImageShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the uploadImageList where fullName equals to UPDATED_FULL_NAME
        defaultUploadImageShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fullName is not null
        defaultUploadImageShouldBeFound("fullName.specified=true");

        // Get all the uploadImageList where fullName is null
        defaultUploadImageShouldNotBeFound("fullName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByFullNameContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fullName contains DEFAULT_FULL_NAME
        defaultUploadImageShouldBeFound("fullName.contains=" + DEFAULT_FULL_NAME);

        // Get all the uploadImageList where fullName contains UPDATED_FULL_NAME
        defaultUploadImageShouldNotBeFound("fullName.contains=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFullNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fullName does not contain DEFAULT_FULL_NAME
        defaultUploadImageShouldNotBeFound("fullName.doesNotContain=" + DEFAULT_FULL_NAME);

        // Get all the uploadImageList where fullName does not contain UPDATED_FULL_NAME
        defaultUploadImageShouldBeFound("fullName.doesNotContain=" + UPDATED_FULL_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where name equals to DEFAULT_NAME
        defaultUploadImageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the uploadImageList where name equals to UPDATED_NAME
        defaultUploadImageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where name not equals to DEFAULT_NAME
        defaultUploadImageShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the uploadImageList where name not equals to UPDATED_NAME
        defaultUploadImageShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUploadImageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the uploadImageList where name equals to UPDATED_NAME
        defaultUploadImageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where name is not null
        defaultUploadImageShouldBeFound("name.specified=true");

        // Get all the uploadImageList where name is null
        defaultUploadImageShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByNameContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where name contains DEFAULT_NAME
        defaultUploadImageShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the uploadImageList where name contains UPDATED_NAME
        defaultUploadImageShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where name does not contain DEFAULT_NAME
        defaultUploadImageShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the uploadImageList where name does not contain UPDATED_NAME
        defaultUploadImageShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByExtIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where ext equals to DEFAULT_EXT
        defaultUploadImageShouldBeFound("ext.equals=" + DEFAULT_EXT);

        // Get all the uploadImageList where ext equals to UPDATED_EXT
        defaultUploadImageShouldNotBeFound("ext.equals=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByExtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where ext not equals to DEFAULT_EXT
        defaultUploadImageShouldNotBeFound("ext.notEquals=" + DEFAULT_EXT);

        // Get all the uploadImageList where ext not equals to UPDATED_EXT
        defaultUploadImageShouldBeFound("ext.notEquals=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByExtIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where ext in DEFAULT_EXT or UPDATED_EXT
        defaultUploadImageShouldBeFound("ext.in=" + DEFAULT_EXT + "," + UPDATED_EXT);

        // Get all the uploadImageList where ext equals to UPDATED_EXT
        defaultUploadImageShouldNotBeFound("ext.in=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByExtIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where ext is not null
        defaultUploadImageShouldBeFound("ext.specified=true");

        // Get all the uploadImageList where ext is null
        defaultUploadImageShouldNotBeFound("ext.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByExtContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where ext contains DEFAULT_EXT
        defaultUploadImageShouldBeFound("ext.contains=" + DEFAULT_EXT);

        // Get all the uploadImageList where ext contains UPDATED_EXT
        defaultUploadImageShouldNotBeFound("ext.contains=" + UPDATED_EXT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByExtNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where ext does not contain DEFAULT_EXT
        defaultUploadImageShouldNotBeFound("ext.doesNotContain=" + DEFAULT_EXT);

        // Get all the uploadImageList where ext does not contain UPDATED_EXT
        defaultUploadImageShouldBeFound("ext.doesNotContain=" + UPDATED_EXT);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where type equals to DEFAULT_TYPE
        defaultUploadImageShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the uploadImageList where type equals to UPDATED_TYPE
        defaultUploadImageShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where type not equals to DEFAULT_TYPE
        defaultUploadImageShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the uploadImageList where type not equals to UPDATED_TYPE
        defaultUploadImageShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultUploadImageShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the uploadImageList where type equals to UPDATED_TYPE
        defaultUploadImageShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where type is not null
        defaultUploadImageShouldBeFound("type.specified=true");

        // Get all the uploadImageList where type is null
        defaultUploadImageShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByTypeContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where type contains DEFAULT_TYPE
        defaultUploadImageShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the uploadImageList where type contains UPDATED_TYPE
        defaultUploadImageShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where type does not contain DEFAULT_TYPE
        defaultUploadImageShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the uploadImageList where type does not contain UPDATED_TYPE
        defaultUploadImageShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where url equals to DEFAULT_URL
        defaultUploadImageShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the uploadImageList where url equals to UPDATED_URL
        defaultUploadImageShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where url not equals to DEFAULT_URL
        defaultUploadImageShouldNotBeFound("url.notEquals=" + DEFAULT_URL);

        // Get all the uploadImageList where url not equals to UPDATED_URL
        defaultUploadImageShouldBeFound("url.notEquals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where url in DEFAULT_URL or UPDATED_URL
        defaultUploadImageShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the uploadImageList where url equals to UPDATED_URL
        defaultUploadImageShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where url is not null
        defaultUploadImageShouldBeFound("url.specified=true");

        // Get all the uploadImageList where url is null
        defaultUploadImageShouldNotBeFound("url.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByUrlContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where url contains DEFAULT_URL
        defaultUploadImageShouldBeFound("url.contains=" + DEFAULT_URL);

        // Get all the uploadImageList where url contains UPDATED_URL
        defaultUploadImageShouldNotBeFound("url.contains=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByUrlNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where url does not contain DEFAULT_URL
        defaultUploadImageShouldNotBeFound("url.doesNotContain=" + DEFAULT_URL);

        // Get all the uploadImageList where url does not contain UPDATED_URL
        defaultUploadImageShouldBeFound("url.doesNotContain=" + UPDATED_URL);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByPathIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where path equals to DEFAULT_PATH
        defaultUploadImageShouldBeFound("path.equals=" + DEFAULT_PATH);

        // Get all the uploadImageList where path equals to UPDATED_PATH
        defaultUploadImageShouldNotBeFound("path.equals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByPathIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where path not equals to DEFAULT_PATH
        defaultUploadImageShouldNotBeFound("path.notEquals=" + DEFAULT_PATH);

        // Get all the uploadImageList where path not equals to UPDATED_PATH
        defaultUploadImageShouldBeFound("path.notEquals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByPathIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where path in DEFAULT_PATH or UPDATED_PATH
        defaultUploadImageShouldBeFound("path.in=" + DEFAULT_PATH + "," + UPDATED_PATH);

        // Get all the uploadImageList where path equals to UPDATED_PATH
        defaultUploadImageShouldNotBeFound("path.in=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where path is not null
        defaultUploadImageShouldBeFound("path.specified=true");

        // Get all the uploadImageList where path is null
        defaultUploadImageShouldNotBeFound("path.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByPathContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where path contains DEFAULT_PATH
        defaultUploadImageShouldBeFound("path.contains=" + DEFAULT_PATH);

        // Get all the uploadImageList where path contains UPDATED_PATH
        defaultUploadImageShouldNotBeFound("path.contains=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByPathNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where path does not contain DEFAULT_PATH
        defaultUploadImageShouldNotBeFound("path.doesNotContain=" + DEFAULT_PATH);

        // Get all the uploadImageList where path does not contain UPDATED_PATH
        defaultUploadImageShouldBeFound("path.doesNotContain=" + UPDATED_PATH);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByFolderIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where folder equals to DEFAULT_FOLDER
        defaultUploadImageShouldBeFound("folder.equals=" + DEFAULT_FOLDER);

        // Get all the uploadImageList where folder equals to UPDATED_FOLDER
        defaultUploadImageShouldNotBeFound("folder.equals=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFolderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where folder not equals to DEFAULT_FOLDER
        defaultUploadImageShouldNotBeFound("folder.notEquals=" + DEFAULT_FOLDER);

        // Get all the uploadImageList where folder not equals to UPDATED_FOLDER
        defaultUploadImageShouldBeFound("folder.notEquals=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFolderIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where folder in DEFAULT_FOLDER or UPDATED_FOLDER
        defaultUploadImageShouldBeFound("folder.in=" + DEFAULT_FOLDER + "," + UPDATED_FOLDER);

        // Get all the uploadImageList where folder equals to UPDATED_FOLDER
        defaultUploadImageShouldNotBeFound("folder.in=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFolderIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where folder is not null
        defaultUploadImageShouldBeFound("folder.specified=true");

        // Get all the uploadImageList where folder is null
        defaultUploadImageShouldNotBeFound("folder.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByFolderContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where folder contains DEFAULT_FOLDER
        defaultUploadImageShouldBeFound("folder.contains=" + DEFAULT_FOLDER);

        // Get all the uploadImageList where folder contains UPDATED_FOLDER
        defaultUploadImageShouldNotBeFound("folder.contains=" + UPDATED_FOLDER);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFolderNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where folder does not contain DEFAULT_FOLDER
        defaultUploadImageShouldNotBeFound("folder.doesNotContain=" + DEFAULT_FOLDER);

        // Get all the uploadImageList where folder does not contain UPDATED_FOLDER
        defaultUploadImageShouldBeFound("folder.doesNotContain=" + UPDATED_FOLDER);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByEntityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where entityName equals to DEFAULT_ENTITY_NAME
        defaultUploadImageShouldBeFound("entityName.equals=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadImageList where entityName equals to UPDATED_ENTITY_NAME
        defaultUploadImageShouldNotBeFound("entityName.equals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByEntityNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where entityName not equals to DEFAULT_ENTITY_NAME
        defaultUploadImageShouldNotBeFound("entityName.notEquals=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadImageList where entityName not equals to UPDATED_ENTITY_NAME
        defaultUploadImageShouldBeFound("entityName.notEquals=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByEntityNameIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where entityName in DEFAULT_ENTITY_NAME or UPDATED_ENTITY_NAME
        defaultUploadImageShouldBeFound("entityName.in=" + DEFAULT_ENTITY_NAME + "," + UPDATED_ENTITY_NAME);

        // Get all the uploadImageList where entityName equals to UPDATED_ENTITY_NAME
        defaultUploadImageShouldNotBeFound("entityName.in=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByEntityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where entityName is not null
        defaultUploadImageShouldBeFound("entityName.specified=true");

        // Get all the uploadImageList where entityName is null
        defaultUploadImageShouldNotBeFound("entityName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByEntityNameContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where entityName contains DEFAULT_ENTITY_NAME
        defaultUploadImageShouldBeFound("entityName.contains=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadImageList where entityName contains UPDATED_ENTITY_NAME
        defaultUploadImageShouldNotBeFound("entityName.contains=" + UPDATED_ENTITY_NAME);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByEntityNameNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where entityName does not contain DEFAULT_ENTITY_NAME
        defaultUploadImageShouldNotBeFound("entityName.doesNotContain=" + DEFAULT_ENTITY_NAME);

        // Get all the uploadImageList where entityName does not contain UPDATED_ENTITY_NAME
        defaultUploadImageShouldBeFound("entityName.doesNotContain=" + UPDATED_ENTITY_NAME);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt equals to DEFAULT_CREATE_AT
        defaultUploadImageShouldBeFound("createAt.equals=" + DEFAULT_CREATE_AT);

        // Get all the uploadImageList where createAt equals to UPDATED_CREATE_AT
        defaultUploadImageShouldNotBeFound("createAt.equals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt not equals to DEFAULT_CREATE_AT
        defaultUploadImageShouldNotBeFound("createAt.notEquals=" + DEFAULT_CREATE_AT);

        // Get all the uploadImageList where createAt not equals to UPDATED_CREATE_AT
        defaultUploadImageShouldBeFound("createAt.notEquals=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt in DEFAULT_CREATE_AT or UPDATED_CREATE_AT
        defaultUploadImageShouldBeFound("createAt.in=" + DEFAULT_CREATE_AT + "," + UPDATED_CREATE_AT);

        // Get all the uploadImageList where createAt equals to UPDATED_CREATE_AT
        defaultUploadImageShouldNotBeFound("createAt.in=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt is not null
        defaultUploadImageShouldBeFound("createAt.specified=true");

        // Get all the uploadImageList where createAt is null
        defaultUploadImageShouldNotBeFound("createAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt is greater than or equal to DEFAULT_CREATE_AT
        defaultUploadImageShouldBeFound("createAt.greaterThanOrEqual=" + DEFAULT_CREATE_AT);

        // Get all the uploadImageList where createAt is greater than or equal to UPDATED_CREATE_AT
        defaultUploadImageShouldNotBeFound("createAt.greaterThanOrEqual=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt is less than or equal to DEFAULT_CREATE_AT
        defaultUploadImageShouldBeFound("createAt.lessThanOrEqual=" + DEFAULT_CREATE_AT);

        // Get all the uploadImageList where createAt is less than or equal to SMALLER_CREATE_AT
        defaultUploadImageShouldNotBeFound("createAt.lessThanOrEqual=" + SMALLER_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt is less than DEFAULT_CREATE_AT
        defaultUploadImageShouldNotBeFound("createAt.lessThan=" + DEFAULT_CREATE_AT);

        // Get all the uploadImageList where createAt is less than UPDATED_CREATE_AT
        defaultUploadImageShouldBeFound("createAt.lessThan=" + UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByCreateAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where createAt is greater than DEFAULT_CREATE_AT
        defaultUploadImageShouldNotBeFound("createAt.greaterThan=" + DEFAULT_CREATE_AT);

        // Get all the uploadImageList where createAt is greater than SMALLER_CREATE_AT
        defaultUploadImageShouldBeFound("createAt.greaterThan=" + SMALLER_CREATE_AT);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize equals to DEFAULT_FILE_SIZE
        defaultUploadImageShouldBeFound("fileSize.equals=" + DEFAULT_FILE_SIZE);

        // Get all the uploadImageList where fileSize equals to UPDATED_FILE_SIZE
        defaultUploadImageShouldNotBeFound("fileSize.equals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize not equals to DEFAULT_FILE_SIZE
        defaultUploadImageShouldNotBeFound("fileSize.notEquals=" + DEFAULT_FILE_SIZE);

        // Get all the uploadImageList where fileSize not equals to UPDATED_FILE_SIZE
        defaultUploadImageShouldBeFound("fileSize.notEquals=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize in DEFAULT_FILE_SIZE or UPDATED_FILE_SIZE
        defaultUploadImageShouldBeFound("fileSize.in=" + DEFAULT_FILE_SIZE + "," + UPDATED_FILE_SIZE);

        // Get all the uploadImageList where fileSize equals to UPDATED_FILE_SIZE
        defaultUploadImageShouldNotBeFound("fileSize.in=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize is not null
        defaultUploadImageShouldBeFound("fileSize.specified=true");

        // Get all the uploadImageList where fileSize is null
        defaultUploadImageShouldNotBeFound("fileSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize is greater than or equal to DEFAULT_FILE_SIZE
        defaultUploadImageShouldBeFound("fileSize.greaterThanOrEqual=" + DEFAULT_FILE_SIZE);

        // Get all the uploadImageList where fileSize is greater than or equal to UPDATED_FILE_SIZE
        defaultUploadImageShouldNotBeFound("fileSize.greaterThanOrEqual=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize is less than or equal to DEFAULT_FILE_SIZE
        defaultUploadImageShouldBeFound("fileSize.lessThanOrEqual=" + DEFAULT_FILE_SIZE);

        // Get all the uploadImageList where fileSize is less than or equal to SMALLER_FILE_SIZE
        defaultUploadImageShouldNotBeFound("fileSize.lessThanOrEqual=" + SMALLER_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize is less than DEFAULT_FILE_SIZE
        defaultUploadImageShouldNotBeFound("fileSize.lessThan=" + DEFAULT_FILE_SIZE);

        // Get all the uploadImageList where fileSize is less than UPDATED_FILE_SIZE
        defaultUploadImageShouldBeFound("fileSize.lessThan=" + UPDATED_FILE_SIZE);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByFileSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where fileSize is greater than DEFAULT_FILE_SIZE
        defaultUploadImageShouldNotBeFound("fileSize.greaterThan=" + DEFAULT_FILE_SIZE);

        // Get all the uploadImageList where fileSize is greater than SMALLER_FILE_SIZE
        defaultUploadImageShouldBeFound("fileSize.greaterThan=" + SMALLER_FILE_SIZE);
    }


    @Test
    @Transactional
    public void getAllUploadImagesBySmartUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where smartUrl equals to DEFAULT_SMART_URL
        defaultUploadImageShouldBeFound("smartUrl.equals=" + DEFAULT_SMART_URL);

        // Get all the uploadImageList where smartUrl equals to UPDATED_SMART_URL
        defaultUploadImageShouldNotBeFound("smartUrl.equals=" + UPDATED_SMART_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesBySmartUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where smartUrl not equals to DEFAULT_SMART_URL
        defaultUploadImageShouldNotBeFound("smartUrl.notEquals=" + DEFAULT_SMART_URL);

        // Get all the uploadImageList where smartUrl not equals to UPDATED_SMART_URL
        defaultUploadImageShouldBeFound("smartUrl.notEquals=" + UPDATED_SMART_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesBySmartUrlIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where smartUrl in DEFAULT_SMART_URL or UPDATED_SMART_URL
        defaultUploadImageShouldBeFound("smartUrl.in=" + DEFAULT_SMART_URL + "," + UPDATED_SMART_URL);

        // Get all the uploadImageList where smartUrl equals to UPDATED_SMART_URL
        defaultUploadImageShouldNotBeFound("smartUrl.in=" + UPDATED_SMART_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesBySmartUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where smartUrl is not null
        defaultUploadImageShouldBeFound("smartUrl.specified=true");

        // Get all the uploadImageList where smartUrl is null
        defaultUploadImageShouldNotBeFound("smartUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesBySmartUrlContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where smartUrl contains DEFAULT_SMART_URL
        defaultUploadImageShouldBeFound("smartUrl.contains=" + DEFAULT_SMART_URL);

        // Get all the uploadImageList where smartUrl contains UPDATED_SMART_URL
        defaultUploadImageShouldNotBeFound("smartUrl.contains=" + UPDATED_SMART_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesBySmartUrlNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where smartUrl does not contain DEFAULT_SMART_URL
        defaultUploadImageShouldNotBeFound("smartUrl.doesNotContain=" + DEFAULT_SMART_URL);

        // Get all the uploadImageList where smartUrl does not contain UPDATED_SMART_URL
        defaultUploadImageShouldBeFound("smartUrl.doesNotContain=" + UPDATED_SMART_URL);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByMediumUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where mediumUrl equals to DEFAULT_MEDIUM_URL
        defaultUploadImageShouldBeFound("mediumUrl.equals=" + DEFAULT_MEDIUM_URL);

        // Get all the uploadImageList where mediumUrl equals to UPDATED_MEDIUM_URL
        defaultUploadImageShouldNotBeFound("mediumUrl.equals=" + UPDATED_MEDIUM_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByMediumUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where mediumUrl not equals to DEFAULT_MEDIUM_URL
        defaultUploadImageShouldNotBeFound("mediumUrl.notEquals=" + DEFAULT_MEDIUM_URL);

        // Get all the uploadImageList where mediumUrl not equals to UPDATED_MEDIUM_URL
        defaultUploadImageShouldBeFound("mediumUrl.notEquals=" + UPDATED_MEDIUM_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByMediumUrlIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where mediumUrl in DEFAULT_MEDIUM_URL or UPDATED_MEDIUM_URL
        defaultUploadImageShouldBeFound("mediumUrl.in=" + DEFAULT_MEDIUM_URL + "," + UPDATED_MEDIUM_URL);

        // Get all the uploadImageList where mediumUrl equals to UPDATED_MEDIUM_URL
        defaultUploadImageShouldNotBeFound("mediumUrl.in=" + UPDATED_MEDIUM_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByMediumUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where mediumUrl is not null
        defaultUploadImageShouldBeFound("mediumUrl.specified=true");

        // Get all the uploadImageList where mediumUrl is null
        defaultUploadImageShouldNotBeFound("mediumUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllUploadImagesByMediumUrlContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where mediumUrl contains DEFAULT_MEDIUM_URL
        defaultUploadImageShouldBeFound("mediumUrl.contains=" + DEFAULT_MEDIUM_URL);

        // Get all the uploadImageList where mediumUrl contains UPDATED_MEDIUM_URL
        defaultUploadImageShouldNotBeFound("mediumUrl.contains=" + UPDATED_MEDIUM_URL);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByMediumUrlNotContainsSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where mediumUrl does not contain DEFAULT_MEDIUM_URL
        defaultUploadImageShouldNotBeFound("mediumUrl.doesNotContain=" + DEFAULT_MEDIUM_URL);

        // Get all the uploadImageList where mediumUrl does not contain UPDATED_MEDIUM_URL
        defaultUploadImageShouldBeFound("mediumUrl.doesNotContain=" + UPDATED_MEDIUM_URL);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount equals to DEFAULT_REFERENCE_COUNT
        defaultUploadImageShouldBeFound("referenceCount.equals=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadImageList where referenceCount equals to UPDATED_REFERENCE_COUNT
        defaultUploadImageShouldNotBeFound("referenceCount.equals=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount not equals to DEFAULT_REFERENCE_COUNT
        defaultUploadImageShouldNotBeFound("referenceCount.notEquals=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadImageList where referenceCount not equals to UPDATED_REFERENCE_COUNT
        defaultUploadImageShouldBeFound("referenceCount.notEquals=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsInShouldWork() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount in DEFAULT_REFERENCE_COUNT or UPDATED_REFERENCE_COUNT
        defaultUploadImageShouldBeFound("referenceCount.in=" + DEFAULT_REFERENCE_COUNT + "," + UPDATED_REFERENCE_COUNT);

        // Get all the uploadImageList where referenceCount equals to UPDATED_REFERENCE_COUNT
        defaultUploadImageShouldNotBeFound("referenceCount.in=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount is not null
        defaultUploadImageShouldBeFound("referenceCount.specified=true");

        // Get all the uploadImageList where referenceCount is null
        defaultUploadImageShouldNotBeFound("referenceCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount is greater than or equal to DEFAULT_REFERENCE_COUNT
        defaultUploadImageShouldBeFound("referenceCount.greaterThanOrEqual=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadImageList where referenceCount is greater than or equal to UPDATED_REFERENCE_COUNT
        defaultUploadImageShouldNotBeFound("referenceCount.greaterThanOrEqual=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount is less than or equal to DEFAULT_REFERENCE_COUNT
        defaultUploadImageShouldBeFound("referenceCount.lessThanOrEqual=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadImageList where referenceCount is less than or equal to SMALLER_REFERENCE_COUNT
        defaultUploadImageShouldNotBeFound("referenceCount.lessThanOrEqual=" + SMALLER_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsLessThanSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount is less than DEFAULT_REFERENCE_COUNT
        defaultUploadImageShouldNotBeFound("referenceCount.lessThan=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadImageList where referenceCount is less than UPDATED_REFERENCE_COUNT
        defaultUploadImageShouldBeFound("referenceCount.lessThan=" + UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void getAllUploadImagesByReferenceCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        // Get all the uploadImageList where referenceCount is greater than DEFAULT_REFERENCE_COUNT
        defaultUploadImageShouldNotBeFound("referenceCount.greaterThan=" + DEFAULT_REFERENCE_COUNT);

        // Get all the uploadImageList where referenceCount is greater than SMALLER_REFERENCE_COUNT
        defaultUploadImageShouldBeFound("referenceCount.greaterThan=" + SMALLER_REFERENCE_COUNT);
    }


    @Test
    @Transactional
    public void getAllUploadImagesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        uploadImage.setUser(user);
        uploadImageRepository.saveAndFlush(uploadImage);
        Long userId = user.getId();

        // Get all the uploadImageList where user equals to userId
        defaultUploadImageShouldBeFound("userId.equals=" + userId);

        // Get all the uploadImageList where user equals to userId + 1
        defaultUploadImageShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUploadImageShouldBeFound(String filter) throws Exception {
        restUploadImageMockMvc.perform(get("/api/upload-images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadImage.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].smartUrl").value(hasItem(DEFAULT_SMART_URL)))
            .andExpect(jsonPath("$.[*].mediumUrl").value(hasItem(DEFAULT_MEDIUM_URL)))
            .andExpect(jsonPath("$.[*].referenceCount").value(hasItem(DEFAULT_REFERENCE_COUNT.intValue())));

        // Check, that the count call also returns 1
        restUploadImageMockMvc.perform(get("/api/upload-images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUploadImageShouldNotBeFound(String filter) throws Exception {
        restUploadImageMockMvc.perform(get("/api/upload-images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUploadImageMockMvc.perform(get("/api/upload-images/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUploadImage() throws Exception {
        // Get the uploadImage
        restUploadImageMockMvc.perform(get("/api/upload-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadImage() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        int databaseSizeBeforeUpdate = uploadImageRepository.findAll().size();

        // Update the uploadImage
        UploadImage updatedUploadImage = uploadImageRepository.findById(uploadImage.getId()).get();
        // Disconnect from session so that the updates on updatedUploadImage are not directly saved in db
        em.detach(updatedUploadImage);
        updatedUploadImage
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
            .smartUrl(UPDATED_SMART_URL)
            .mediumUrl(UPDATED_MEDIUM_URL)
            .referenceCount(UPDATED_REFERENCE_COUNT);
        UploadImageDTO uploadImageDTO = uploadImageMapper.toDto(updatedUploadImage);

        restUploadImageMockMvc.perform(put("/api/upload-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadImageDTO)))
            .andExpect(status().isOk());

        // Validate the UploadImage in the database
        List<UploadImage> uploadImageList = uploadImageRepository.findAll();
        assertThat(uploadImageList).hasSize(databaseSizeBeforeUpdate);
        UploadImage testUploadImage = uploadImageList.get(uploadImageList.size() - 1);
        assertThat(testUploadImage.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testUploadImage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUploadImage.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testUploadImage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUploadImage.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testUploadImage.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testUploadImage.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testUploadImage.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testUploadImage.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
        assertThat(testUploadImage.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testUploadImage.getSmartUrl()).isEqualTo(UPDATED_SMART_URL);
        assertThat(testUploadImage.getMediumUrl()).isEqualTo(UPDATED_MEDIUM_URL);
        assertThat(testUploadImage.getReferenceCount()).isEqualTo(UPDATED_REFERENCE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadImage() throws Exception {
        int databaseSizeBeforeUpdate = uploadImageRepository.findAll().size();

        // Create the UploadImage
        UploadImageDTO uploadImageDTO = uploadImageMapper.toDto(uploadImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadImageMockMvc.perform(put("/api/upload-images")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UploadImage in the database
        List<UploadImage> uploadImageList = uploadImageRepository.findAll();
        assertThat(uploadImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadImage() throws Exception {
        // Initialize the database
        uploadImageRepository.saveAndFlush(uploadImage);

        int databaseSizeBeforeDelete = uploadImageRepository.findAll().size();

        // Delete the uploadImage
        restUploadImageMockMvc.perform(delete("/api/upload-images/{id}", uploadImage.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UploadImage> uploadImageList = uploadImageRepository.findAll();
        assertThat(uploadImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
