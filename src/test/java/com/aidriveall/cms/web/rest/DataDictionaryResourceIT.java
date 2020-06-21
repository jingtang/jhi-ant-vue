package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.DataDictionary;
import com.aidriveall.cms.domain.DataDictionary;
import com.aidriveall.cms.repository.DataDictionaryRepository;
import com.aidriveall.cms.service.DataDictionaryService;
import com.aidriveall.cms.service.dto.DataDictionaryDTO;
import com.aidriveall.cms.service.mapper.DataDictionaryMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.DataDictionaryCriteria;
import com.aidriveall.cms.service.DataDictionaryQueryService;

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
 * Integration tests for the {@link DataDictionaryResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class DataDictionaryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FONT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_FONT_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;

    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Autowired
    private DataDictionaryQueryService dataDictionaryQueryService;

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

    private MockMvc restDataDictionaryMockMvc;

    private DataDictionary dataDictionary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataDictionaryResource dataDictionaryResource = new DataDictionaryResource(dataDictionaryService, dataDictionaryQueryService);
        this.restDataDictionaryMockMvc = MockMvcBuilders.standaloneSetup(dataDictionaryResource)
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
    public static DataDictionary createEntity(EntityManager em) {
        DataDictionary dataDictionary = new DataDictionary()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .fontColor(DEFAULT_FONT_COLOR)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR);
        return dataDictionary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataDictionary createUpdatedEntity(EntityManager em) {
        DataDictionary dataDictionary = new DataDictionary()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .fontColor(UPDATED_FONT_COLOR)
            .backgroundColor(UPDATED_BACKGROUND_COLOR);
        return dataDictionary;
    }

    @BeforeEach
    public void initTest() {
        dataDictionary = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataDictionary() throws Exception {
        int databaseSizeBeforeCreate = dataDictionaryRepository.findAll().size();

        // Create the DataDictionary
        DataDictionaryDTO dataDictionaryDTO = dataDictionaryMapper.toDto(dataDictionary);
        restDataDictionaryMockMvc.perform(post("/api/data-dictionaries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the DataDictionary in the database
        List<DataDictionary> dataDictionaryList = dataDictionaryRepository.findAll();
        assertThat(dataDictionaryList).hasSize(databaseSizeBeforeCreate + 1);
        DataDictionary testDataDictionary = dataDictionaryList.get(dataDictionaryList.size() - 1);
        assertThat(testDataDictionary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataDictionary.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDataDictionary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDataDictionary.getFontColor()).isEqualTo(DEFAULT_FONT_COLOR);
        assertThat(testDataDictionary.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    @Transactional
    public void createDataDictionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataDictionaryRepository.findAll().size();

        // Create the DataDictionary with an existing ID
        dataDictionary.setId(1L);
        DataDictionaryDTO dataDictionaryDTO = dataDictionaryMapper.toDto(dataDictionary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataDictionaryMockMvc.perform(post("/api/data-dictionaries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDictionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataDictionary in the database
        List<DataDictionary> dataDictionaryList = dataDictionaryRepository.findAll();
        assertThat(dataDictionaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataDictionaries() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList
        restDataDictionaryMockMvc.perform(get("/api/data-dictionaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataDictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fontColor").value(hasItem(DEFAULT_FONT_COLOR)))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)));
    }
    
    @Test
    @Transactional
    public void getDataDictionary() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get the dataDictionary
        restDataDictionaryMockMvc.perform(get("/api/data-dictionaries/{id}", dataDictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataDictionary.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fontColor").value(DEFAULT_FONT_COLOR))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR));
    }


    @Test
    @Transactional
    public void getDataDictionariesByIdFiltering() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        Long id = dataDictionary.getId();

        defaultDataDictionaryShouldBeFound("id.equals=" + id);
        defaultDataDictionaryShouldNotBeFound("id.notEquals=" + id);

        defaultDataDictionaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDataDictionaryShouldNotBeFound("id.greaterThan=" + id);

        defaultDataDictionaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDataDictionaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDataDictionariesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where name equals to DEFAULT_NAME
        defaultDataDictionaryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dataDictionaryList where name equals to UPDATED_NAME
        defaultDataDictionaryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where name not equals to DEFAULT_NAME
        defaultDataDictionaryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the dataDictionaryList where name not equals to UPDATED_NAME
        defaultDataDictionaryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDataDictionaryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dataDictionaryList where name equals to UPDATED_NAME
        defaultDataDictionaryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where name is not null
        defaultDataDictionaryShouldBeFound("name.specified=true");

        // Get all the dataDictionaryList where name is null
        defaultDataDictionaryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDataDictionariesByNameContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where name contains DEFAULT_NAME
        defaultDataDictionaryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the dataDictionaryList where name contains UPDATED_NAME
        defaultDataDictionaryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where name does not contain DEFAULT_NAME
        defaultDataDictionaryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the dataDictionaryList where name does not contain UPDATED_NAME
        defaultDataDictionaryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDataDictionariesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where code equals to DEFAULT_CODE
        defaultDataDictionaryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the dataDictionaryList where code equals to UPDATED_CODE
        defaultDataDictionaryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where code not equals to DEFAULT_CODE
        defaultDataDictionaryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the dataDictionaryList where code not equals to UPDATED_CODE
        defaultDataDictionaryShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultDataDictionaryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the dataDictionaryList where code equals to UPDATED_CODE
        defaultDataDictionaryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where code is not null
        defaultDataDictionaryShouldBeFound("code.specified=true");

        // Get all the dataDictionaryList where code is null
        defaultDataDictionaryShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllDataDictionariesByCodeContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where code contains DEFAULT_CODE
        defaultDataDictionaryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the dataDictionaryList where code contains UPDATED_CODE
        defaultDataDictionaryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where code does not contain DEFAULT_CODE
        defaultDataDictionaryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the dataDictionaryList where code does not contain UPDATED_CODE
        defaultDataDictionaryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllDataDictionariesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where description equals to DEFAULT_DESCRIPTION
        defaultDataDictionaryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the dataDictionaryList where description equals to UPDATED_DESCRIPTION
        defaultDataDictionaryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where description not equals to DEFAULT_DESCRIPTION
        defaultDataDictionaryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the dataDictionaryList where description not equals to UPDATED_DESCRIPTION
        defaultDataDictionaryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDataDictionaryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the dataDictionaryList where description equals to UPDATED_DESCRIPTION
        defaultDataDictionaryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where description is not null
        defaultDataDictionaryShouldBeFound("description.specified=true");

        // Get all the dataDictionaryList where description is null
        defaultDataDictionaryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllDataDictionariesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where description contains DEFAULT_DESCRIPTION
        defaultDataDictionaryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the dataDictionaryList where description contains UPDATED_DESCRIPTION
        defaultDataDictionaryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where description does not contain DEFAULT_DESCRIPTION
        defaultDataDictionaryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the dataDictionaryList where description does not contain UPDATED_DESCRIPTION
        defaultDataDictionaryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllDataDictionariesByFontColorIsEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where fontColor equals to DEFAULT_FONT_COLOR
        defaultDataDictionaryShouldBeFound("fontColor.equals=" + DEFAULT_FONT_COLOR);

        // Get all the dataDictionaryList where fontColor equals to UPDATED_FONT_COLOR
        defaultDataDictionaryShouldNotBeFound("fontColor.equals=" + UPDATED_FONT_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByFontColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where fontColor not equals to DEFAULT_FONT_COLOR
        defaultDataDictionaryShouldNotBeFound("fontColor.notEquals=" + DEFAULT_FONT_COLOR);

        // Get all the dataDictionaryList where fontColor not equals to UPDATED_FONT_COLOR
        defaultDataDictionaryShouldBeFound("fontColor.notEquals=" + UPDATED_FONT_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByFontColorIsInShouldWork() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where fontColor in DEFAULT_FONT_COLOR or UPDATED_FONT_COLOR
        defaultDataDictionaryShouldBeFound("fontColor.in=" + DEFAULT_FONT_COLOR + "," + UPDATED_FONT_COLOR);

        // Get all the dataDictionaryList where fontColor equals to UPDATED_FONT_COLOR
        defaultDataDictionaryShouldNotBeFound("fontColor.in=" + UPDATED_FONT_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByFontColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where fontColor is not null
        defaultDataDictionaryShouldBeFound("fontColor.specified=true");

        // Get all the dataDictionaryList where fontColor is null
        defaultDataDictionaryShouldNotBeFound("fontColor.specified=false");
    }
                @Test
    @Transactional
    public void getAllDataDictionariesByFontColorContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where fontColor contains DEFAULT_FONT_COLOR
        defaultDataDictionaryShouldBeFound("fontColor.contains=" + DEFAULT_FONT_COLOR);

        // Get all the dataDictionaryList where fontColor contains UPDATED_FONT_COLOR
        defaultDataDictionaryShouldNotBeFound("fontColor.contains=" + UPDATED_FONT_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByFontColorNotContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where fontColor does not contain DEFAULT_FONT_COLOR
        defaultDataDictionaryShouldNotBeFound("fontColor.doesNotContain=" + DEFAULT_FONT_COLOR);

        // Get all the dataDictionaryList where fontColor does not contain UPDATED_FONT_COLOR
        defaultDataDictionaryShouldBeFound("fontColor.doesNotContain=" + UPDATED_FONT_COLOR);
    }


    @Test
    @Transactional
    public void getAllDataDictionariesByBackgroundColorIsEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where backgroundColor equals to DEFAULT_BACKGROUND_COLOR
        defaultDataDictionaryShouldBeFound("backgroundColor.equals=" + DEFAULT_BACKGROUND_COLOR);

        // Get all the dataDictionaryList where backgroundColor equals to UPDATED_BACKGROUND_COLOR
        defaultDataDictionaryShouldNotBeFound("backgroundColor.equals=" + UPDATED_BACKGROUND_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByBackgroundColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where backgroundColor not equals to DEFAULT_BACKGROUND_COLOR
        defaultDataDictionaryShouldNotBeFound("backgroundColor.notEquals=" + DEFAULT_BACKGROUND_COLOR);

        // Get all the dataDictionaryList where backgroundColor not equals to UPDATED_BACKGROUND_COLOR
        defaultDataDictionaryShouldBeFound("backgroundColor.notEquals=" + UPDATED_BACKGROUND_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByBackgroundColorIsInShouldWork() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where backgroundColor in DEFAULT_BACKGROUND_COLOR or UPDATED_BACKGROUND_COLOR
        defaultDataDictionaryShouldBeFound("backgroundColor.in=" + DEFAULT_BACKGROUND_COLOR + "," + UPDATED_BACKGROUND_COLOR);

        // Get all the dataDictionaryList where backgroundColor equals to UPDATED_BACKGROUND_COLOR
        defaultDataDictionaryShouldNotBeFound("backgroundColor.in=" + UPDATED_BACKGROUND_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByBackgroundColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where backgroundColor is not null
        defaultDataDictionaryShouldBeFound("backgroundColor.specified=true");

        // Get all the dataDictionaryList where backgroundColor is null
        defaultDataDictionaryShouldNotBeFound("backgroundColor.specified=false");
    }
                @Test
    @Transactional
    public void getAllDataDictionariesByBackgroundColorContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where backgroundColor contains DEFAULT_BACKGROUND_COLOR
        defaultDataDictionaryShouldBeFound("backgroundColor.contains=" + DEFAULT_BACKGROUND_COLOR);

        // Get all the dataDictionaryList where backgroundColor contains UPDATED_BACKGROUND_COLOR
        defaultDataDictionaryShouldNotBeFound("backgroundColor.contains=" + UPDATED_BACKGROUND_COLOR);
    }

    @Test
    @Transactional
    public void getAllDataDictionariesByBackgroundColorNotContainsSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        // Get all the dataDictionaryList where backgroundColor does not contain DEFAULT_BACKGROUND_COLOR
        defaultDataDictionaryShouldNotBeFound("backgroundColor.doesNotContain=" + DEFAULT_BACKGROUND_COLOR);

        // Get all the dataDictionaryList where backgroundColor does not contain UPDATED_BACKGROUND_COLOR
        defaultDataDictionaryShouldBeFound("backgroundColor.doesNotContain=" + UPDATED_BACKGROUND_COLOR);
    }


    @Test
    @Transactional
    public void getAllDataDictionariesByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);
        DataDictionary children = DataDictionaryResourceIT.createEntity(em);
        em.persist(children);
        em.flush();
        dataDictionary.addChildren(children);
        dataDictionaryRepository.saveAndFlush(dataDictionary);
        Long childrenId = children.getId();

        // Get all the dataDictionaryList where children equals to childrenId
        defaultDataDictionaryShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the dataDictionaryList where children equals to childrenId + 1
        defaultDataDictionaryShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }


    @Test
    @Transactional
    public void getAllDataDictionariesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);
        DataDictionary parent = DataDictionaryResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        dataDictionary.setParent(parent);
        dataDictionaryRepository.saveAndFlush(dataDictionary);
        Long parentId = parent.getId();

        // Get all the dataDictionaryList where parent equals to parentId
        defaultDataDictionaryShouldBeFound("parentId.equals=" + parentId);

        // Get all the dataDictionaryList where parent equals to parentId + 1
        defaultDataDictionaryShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDataDictionaryShouldBeFound(String filter) throws Exception {
        restDataDictionaryMockMvc.perform(get("/api/data-dictionaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataDictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fontColor").value(hasItem(DEFAULT_FONT_COLOR)))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)));

        // Check, that the count call also returns 1
        restDataDictionaryMockMvc.perform(get("/api/data-dictionaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDataDictionaryShouldNotBeFound(String filter) throws Exception {
        restDataDictionaryMockMvc.perform(get("/api/data-dictionaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDataDictionaryMockMvc.perform(get("/api/data-dictionaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataDictionary() throws Exception {
        // Get the dataDictionary
        restDataDictionaryMockMvc.perform(get("/api/data-dictionaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataDictionary() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        int databaseSizeBeforeUpdate = dataDictionaryRepository.findAll().size();

        // Update the dataDictionary
        DataDictionary updatedDataDictionary = dataDictionaryRepository.findById(dataDictionary.getId()).get();
        // Disconnect from session so that the updates on updatedDataDictionary are not directly saved in db
        em.detach(updatedDataDictionary);
        updatedDataDictionary
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .fontColor(UPDATED_FONT_COLOR)
            .backgroundColor(UPDATED_BACKGROUND_COLOR);
        DataDictionaryDTO dataDictionaryDTO = dataDictionaryMapper.toDto(updatedDataDictionary);

        restDataDictionaryMockMvc.perform(put("/api/data-dictionaries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDictionaryDTO)))
            .andExpect(status().isOk());

        // Validate the DataDictionary in the database
        List<DataDictionary> dataDictionaryList = dataDictionaryRepository.findAll();
        assertThat(dataDictionaryList).hasSize(databaseSizeBeforeUpdate);
        DataDictionary testDataDictionary = dataDictionaryList.get(dataDictionaryList.size() - 1);
        assertThat(testDataDictionary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataDictionary.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDataDictionary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDataDictionary.getFontColor()).isEqualTo(UPDATED_FONT_COLOR);
        assertThat(testDataDictionary.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDataDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dataDictionaryRepository.findAll().size();

        // Create the DataDictionary
        DataDictionaryDTO dataDictionaryDTO = dataDictionaryMapper.toDto(dataDictionary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataDictionaryMockMvc.perform(put("/api/data-dictionaries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dataDictionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataDictionary in the database
        List<DataDictionary> dataDictionaryList = dataDictionaryRepository.findAll();
        assertThat(dataDictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataDictionary() throws Exception {
        // Initialize the database
        dataDictionaryRepository.saveAndFlush(dataDictionary);

        int databaseSizeBeforeDelete = dataDictionaryRepository.findAll().size();

        // Delete the dataDictionary
        restDataDictionaryMockMvc.perform(delete("/api/data-dictionaries/{id}", dataDictionary.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataDictionary> dataDictionaryList = dataDictionaryRepository.findAll();
        assertThat(dataDictionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
