package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.CommonQueryItem;
import com.aidriveall.cms.domain.CommonQuery;
import com.aidriveall.cms.repository.CommonQueryItemRepository;
import com.aidriveall.cms.service.CommonQueryItemService;
import com.aidriveall.cms.service.dto.CommonQueryItemDTO;
import com.aidriveall.cms.service.mapper.CommonQueryItemMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.CommonQueryItemCriteria;
import com.aidriveall.cms.service.CommonQueryItemQueryService;

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
 * Integration tests for the {@link CommonQueryItemResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class CommonQueryItemResourceIT {

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATOR = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_SUFFIX = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

    @Autowired
    private CommonQueryItemRepository commonQueryItemRepository;

    @Autowired
    private CommonQueryItemMapper commonQueryItemMapper;

    @Autowired
    private CommonQueryItemService commonQueryItemService;

    @Autowired
    private CommonQueryItemQueryService commonQueryItemQueryService;

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

    private MockMvc restCommonQueryItemMockMvc;

    private CommonQueryItem commonQueryItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommonQueryItemResource commonQueryItemResource = new CommonQueryItemResource(commonQueryItemService, commonQueryItemQueryService);
        this.restCommonQueryItemMockMvc = MockMvcBuilders.standaloneSetup(commonQueryItemResource)
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
    public static CommonQueryItem createEntity(EntityManager em) {
        CommonQueryItem commonQueryItem = new CommonQueryItem()
            .prefix(DEFAULT_PREFIX)
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldType(DEFAULT_FIELD_TYPE)
            .operator(DEFAULT_OPERATOR)
            .value(DEFAULT_VALUE)
            .suffix(DEFAULT_SUFFIX)
            .order(DEFAULT_ORDER);
        return commonQueryItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommonQueryItem createUpdatedEntity(EntityManager em) {
        CommonQueryItem commonQueryItem = new CommonQueryItem()
            .prefix(UPDATED_PREFIX)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .operator(UPDATED_OPERATOR)
            .value(UPDATED_VALUE)
            .suffix(UPDATED_SUFFIX)
            .order(UPDATED_ORDER);
        return commonQueryItem;
    }

    @BeforeEach
    public void initTest() {
        commonQueryItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommonQueryItem() throws Exception {
        int databaseSizeBeforeCreate = commonQueryItemRepository.findAll().size();

        // Create the CommonQueryItem
        CommonQueryItemDTO commonQueryItemDTO = commonQueryItemMapper.toDto(commonQueryItem);
        restCommonQueryItemMockMvc.perform(post("/api/common-query-items")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CommonQueryItem in the database
        List<CommonQueryItem> commonQueryItemList = commonQueryItemRepository.findAll();
        assertThat(commonQueryItemList).hasSize(databaseSizeBeforeCreate + 1);
        CommonQueryItem testCommonQueryItem = commonQueryItemList.get(commonQueryItemList.size() - 1);
        assertThat(testCommonQueryItem.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testCommonQueryItem.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testCommonQueryItem.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testCommonQueryItem.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testCommonQueryItem.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCommonQueryItem.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testCommonQueryItem.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createCommonQueryItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commonQueryItemRepository.findAll().size();

        // Create the CommonQueryItem with an existing ID
        commonQueryItem.setId(1L);
        CommonQueryItemDTO commonQueryItemDTO = commonQueryItemMapper.toDto(commonQueryItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonQueryItemMockMvc.perform(post("/api/common-query-items")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommonQueryItem in the database
        List<CommonQueryItem> commonQueryItemList = commonQueryItemRepository.findAll();
        assertThat(commonQueryItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItems() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList
        restCommonQueryItemMockMvc.perform(get("/api/common-query-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonQueryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getCommonQueryItem() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get the commonQueryItem
        restCommonQueryItemMockMvc.perform(get("/api/common-query-items/{id}", commonQueryItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commonQueryItem.getId().intValue()))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.suffix").value(DEFAULT_SUFFIX))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }


    @Test
    @Transactional
    public void getCommonQueryItemsByIdFiltering() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        Long id = commonQueryItem.getId();

        defaultCommonQueryItemShouldBeFound("id.equals=" + id);
        defaultCommonQueryItemShouldNotBeFound("id.notEquals=" + id);

        defaultCommonQueryItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommonQueryItemShouldNotBeFound("id.greaterThan=" + id);

        defaultCommonQueryItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommonQueryItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsByPrefixIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where prefix equals to DEFAULT_PREFIX
        defaultCommonQueryItemShouldBeFound("prefix.equals=" + DEFAULT_PREFIX);

        // Get all the commonQueryItemList where prefix equals to UPDATED_PREFIX
        defaultCommonQueryItemShouldNotBeFound("prefix.equals=" + UPDATED_PREFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByPrefixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where prefix not equals to DEFAULT_PREFIX
        defaultCommonQueryItemShouldNotBeFound("prefix.notEquals=" + DEFAULT_PREFIX);

        // Get all the commonQueryItemList where prefix not equals to UPDATED_PREFIX
        defaultCommonQueryItemShouldBeFound("prefix.notEquals=" + UPDATED_PREFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByPrefixIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where prefix in DEFAULT_PREFIX or UPDATED_PREFIX
        defaultCommonQueryItemShouldBeFound("prefix.in=" + DEFAULT_PREFIX + "," + UPDATED_PREFIX);

        // Get all the commonQueryItemList where prefix equals to UPDATED_PREFIX
        defaultCommonQueryItemShouldNotBeFound("prefix.in=" + UPDATED_PREFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByPrefixIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where prefix is not null
        defaultCommonQueryItemShouldBeFound("prefix.specified=true");

        // Get all the commonQueryItemList where prefix is null
        defaultCommonQueryItemShouldNotBeFound("prefix.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueryItemsByPrefixContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where prefix contains DEFAULT_PREFIX
        defaultCommonQueryItemShouldBeFound("prefix.contains=" + DEFAULT_PREFIX);

        // Get all the commonQueryItemList where prefix contains UPDATED_PREFIX
        defaultCommonQueryItemShouldNotBeFound("prefix.contains=" + UPDATED_PREFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByPrefixNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where prefix does not contain DEFAULT_PREFIX
        defaultCommonQueryItemShouldNotBeFound("prefix.doesNotContain=" + DEFAULT_PREFIX);

        // Get all the commonQueryItemList where prefix does not contain UPDATED_PREFIX
        defaultCommonQueryItemShouldBeFound("prefix.doesNotContain=" + UPDATED_PREFIX);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldName equals to DEFAULT_FIELD_NAME
        defaultCommonQueryItemShouldBeFound("fieldName.equals=" + DEFAULT_FIELD_NAME);

        // Get all the commonQueryItemList where fieldName equals to UPDATED_FIELD_NAME
        defaultCommonQueryItemShouldNotBeFound("fieldName.equals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldName not equals to DEFAULT_FIELD_NAME
        defaultCommonQueryItemShouldNotBeFound("fieldName.notEquals=" + DEFAULT_FIELD_NAME);

        // Get all the commonQueryItemList where fieldName not equals to UPDATED_FIELD_NAME
        defaultCommonQueryItemShouldBeFound("fieldName.notEquals=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldNameIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldName in DEFAULT_FIELD_NAME or UPDATED_FIELD_NAME
        defaultCommonQueryItemShouldBeFound("fieldName.in=" + DEFAULT_FIELD_NAME + "," + UPDATED_FIELD_NAME);

        // Get all the commonQueryItemList where fieldName equals to UPDATED_FIELD_NAME
        defaultCommonQueryItemShouldNotBeFound("fieldName.in=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldName is not null
        defaultCommonQueryItemShouldBeFound("fieldName.specified=true");

        // Get all the commonQueryItemList where fieldName is null
        defaultCommonQueryItemShouldNotBeFound("fieldName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldNameContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldName contains DEFAULT_FIELD_NAME
        defaultCommonQueryItemShouldBeFound("fieldName.contains=" + DEFAULT_FIELD_NAME);

        // Get all the commonQueryItemList where fieldName contains UPDATED_FIELD_NAME
        defaultCommonQueryItemShouldNotBeFound("fieldName.contains=" + UPDATED_FIELD_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldNameNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldName does not contain DEFAULT_FIELD_NAME
        defaultCommonQueryItemShouldNotBeFound("fieldName.doesNotContain=" + DEFAULT_FIELD_NAME);

        // Get all the commonQueryItemList where fieldName does not contain UPDATED_FIELD_NAME
        defaultCommonQueryItemShouldBeFound("fieldName.doesNotContain=" + UPDATED_FIELD_NAME);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldType equals to DEFAULT_FIELD_TYPE
        defaultCommonQueryItemShouldBeFound("fieldType.equals=" + DEFAULT_FIELD_TYPE);

        // Get all the commonQueryItemList where fieldType equals to UPDATED_FIELD_TYPE
        defaultCommonQueryItemShouldNotBeFound("fieldType.equals=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldType not equals to DEFAULT_FIELD_TYPE
        defaultCommonQueryItemShouldNotBeFound("fieldType.notEquals=" + DEFAULT_FIELD_TYPE);

        // Get all the commonQueryItemList where fieldType not equals to UPDATED_FIELD_TYPE
        defaultCommonQueryItemShouldBeFound("fieldType.notEquals=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldTypeIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldType in DEFAULT_FIELD_TYPE or UPDATED_FIELD_TYPE
        defaultCommonQueryItemShouldBeFound("fieldType.in=" + DEFAULT_FIELD_TYPE + "," + UPDATED_FIELD_TYPE);

        // Get all the commonQueryItemList where fieldType equals to UPDATED_FIELD_TYPE
        defaultCommonQueryItemShouldNotBeFound("fieldType.in=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldType is not null
        defaultCommonQueryItemShouldBeFound("fieldType.specified=true");

        // Get all the commonQueryItemList where fieldType is null
        defaultCommonQueryItemShouldNotBeFound("fieldType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldTypeContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldType contains DEFAULT_FIELD_TYPE
        defaultCommonQueryItemShouldBeFound("fieldType.contains=" + DEFAULT_FIELD_TYPE);

        // Get all the commonQueryItemList where fieldType contains UPDATED_FIELD_TYPE
        defaultCommonQueryItemShouldNotBeFound("fieldType.contains=" + UPDATED_FIELD_TYPE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByFieldTypeNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where fieldType does not contain DEFAULT_FIELD_TYPE
        defaultCommonQueryItemShouldNotBeFound("fieldType.doesNotContain=" + DEFAULT_FIELD_TYPE);

        // Get all the commonQueryItemList where fieldType does not contain UPDATED_FIELD_TYPE
        defaultCommonQueryItemShouldBeFound("fieldType.doesNotContain=" + UPDATED_FIELD_TYPE);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsByOperatorIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where operator equals to DEFAULT_OPERATOR
        defaultCommonQueryItemShouldBeFound("operator.equals=" + DEFAULT_OPERATOR);

        // Get all the commonQueryItemList where operator equals to UPDATED_OPERATOR
        defaultCommonQueryItemShouldNotBeFound("operator.equals=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOperatorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where operator not equals to DEFAULT_OPERATOR
        defaultCommonQueryItemShouldNotBeFound("operator.notEquals=" + DEFAULT_OPERATOR);

        // Get all the commonQueryItemList where operator not equals to UPDATED_OPERATOR
        defaultCommonQueryItemShouldBeFound("operator.notEquals=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOperatorIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where operator in DEFAULT_OPERATOR or UPDATED_OPERATOR
        defaultCommonQueryItemShouldBeFound("operator.in=" + DEFAULT_OPERATOR + "," + UPDATED_OPERATOR);

        // Get all the commonQueryItemList where operator equals to UPDATED_OPERATOR
        defaultCommonQueryItemShouldNotBeFound("operator.in=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOperatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where operator is not null
        defaultCommonQueryItemShouldBeFound("operator.specified=true");

        // Get all the commonQueryItemList where operator is null
        defaultCommonQueryItemShouldNotBeFound("operator.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueryItemsByOperatorContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where operator contains DEFAULT_OPERATOR
        defaultCommonQueryItemShouldBeFound("operator.contains=" + DEFAULT_OPERATOR);

        // Get all the commonQueryItemList where operator contains UPDATED_OPERATOR
        defaultCommonQueryItemShouldNotBeFound("operator.contains=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOperatorNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where operator does not contain DEFAULT_OPERATOR
        defaultCommonQueryItemShouldNotBeFound("operator.doesNotContain=" + DEFAULT_OPERATOR);

        // Get all the commonQueryItemList where operator does not contain UPDATED_OPERATOR
        defaultCommonQueryItemShouldBeFound("operator.doesNotContain=" + UPDATED_OPERATOR);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where value equals to DEFAULT_VALUE
        defaultCommonQueryItemShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the commonQueryItemList where value equals to UPDATED_VALUE
        defaultCommonQueryItemShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where value not equals to DEFAULT_VALUE
        defaultCommonQueryItemShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the commonQueryItemList where value not equals to UPDATED_VALUE
        defaultCommonQueryItemShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCommonQueryItemShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the commonQueryItemList where value equals to UPDATED_VALUE
        defaultCommonQueryItemShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where value is not null
        defaultCommonQueryItemShouldBeFound("value.specified=true");

        // Get all the commonQueryItemList where value is null
        defaultCommonQueryItemShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueryItemsByValueContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where value contains DEFAULT_VALUE
        defaultCommonQueryItemShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the commonQueryItemList where value contains UPDATED_VALUE
        defaultCommonQueryItemShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where value does not contain DEFAULT_VALUE
        defaultCommonQueryItemShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the commonQueryItemList where value does not contain UPDATED_VALUE
        defaultCommonQueryItemShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsBySuffixIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where suffix equals to DEFAULT_SUFFIX
        defaultCommonQueryItemShouldBeFound("suffix.equals=" + DEFAULT_SUFFIX);

        // Get all the commonQueryItemList where suffix equals to UPDATED_SUFFIX
        defaultCommonQueryItemShouldNotBeFound("suffix.equals=" + UPDATED_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsBySuffixIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where suffix not equals to DEFAULT_SUFFIX
        defaultCommonQueryItemShouldNotBeFound("suffix.notEquals=" + DEFAULT_SUFFIX);

        // Get all the commonQueryItemList where suffix not equals to UPDATED_SUFFIX
        defaultCommonQueryItemShouldBeFound("suffix.notEquals=" + UPDATED_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsBySuffixIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where suffix in DEFAULT_SUFFIX or UPDATED_SUFFIX
        defaultCommonQueryItemShouldBeFound("suffix.in=" + DEFAULT_SUFFIX + "," + UPDATED_SUFFIX);

        // Get all the commonQueryItemList where suffix equals to UPDATED_SUFFIX
        defaultCommonQueryItemShouldNotBeFound("suffix.in=" + UPDATED_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsBySuffixIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where suffix is not null
        defaultCommonQueryItemShouldBeFound("suffix.specified=true");

        // Get all the commonQueryItemList where suffix is null
        defaultCommonQueryItemShouldNotBeFound("suffix.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueryItemsBySuffixContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where suffix contains DEFAULT_SUFFIX
        defaultCommonQueryItemShouldBeFound("suffix.contains=" + DEFAULT_SUFFIX);

        // Get all the commonQueryItemList where suffix contains UPDATED_SUFFIX
        defaultCommonQueryItemShouldNotBeFound("suffix.contains=" + UPDATED_SUFFIX);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsBySuffixNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where suffix does not contain DEFAULT_SUFFIX
        defaultCommonQueryItemShouldNotBeFound("suffix.doesNotContain=" + DEFAULT_SUFFIX);

        // Get all the commonQueryItemList where suffix does not contain UPDATED_SUFFIX
        defaultCommonQueryItemShouldBeFound("suffix.doesNotContain=" + UPDATED_SUFFIX);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order equals to DEFAULT_ORDER
        defaultCommonQueryItemShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the commonQueryItemList where order equals to UPDATED_ORDER
        defaultCommonQueryItemShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order not equals to DEFAULT_ORDER
        defaultCommonQueryItemShouldNotBeFound("order.notEquals=" + DEFAULT_ORDER);

        // Get all the commonQueryItemList where order not equals to UPDATED_ORDER
        defaultCommonQueryItemShouldBeFound("order.notEquals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultCommonQueryItemShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the commonQueryItemList where order equals to UPDATED_ORDER
        defaultCommonQueryItemShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order is not null
        defaultCommonQueryItemShouldBeFound("order.specified=true");

        // Get all the commonQueryItemList where order is null
        defaultCommonQueryItemShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order is greater than or equal to DEFAULT_ORDER
        defaultCommonQueryItemShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the commonQueryItemList where order is greater than or equal to UPDATED_ORDER
        defaultCommonQueryItemShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order is less than or equal to DEFAULT_ORDER
        defaultCommonQueryItemShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the commonQueryItemList where order is less than or equal to SMALLER_ORDER
        defaultCommonQueryItemShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order is less than DEFAULT_ORDER
        defaultCommonQueryItemShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the commonQueryItemList where order is less than UPDATED_ORDER
        defaultCommonQueryItemShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllCommonQueryItemsByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        // Get all the commonQueryItemList where order is greater than DEFAULT_ORDER
        defaultCommonQueryItemShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the commonQueryItemList where order is greater than SMALLER_ORDER
        defaultCommonQueryItemShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }


    @Test
    @Transactional
    public void getAllCommonQueryItemsByQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);
        CommonQuery query = CommonQueryResourceIT.createEntity(em);
        em.persist(query);
        em.flush();
        commonQueryItem.setQuery(query);
        commonQueryItemRepository.saveAndFlush(commonQueryItem);
        Long queryId = query.getId();

        // Get all the commonQueryItemList where query equals to queryId
        defaultCommonQueryItemShouldBeFound("queryId.equals=" + queryId);

        // Get all the commonQueryItemList where query equals to queryId + 1
        defaultCommonQueryItemShouldNotBeFound("queryId.equals=" + (queryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommonQueryItemShouldBeFound(String filter) throws Exception {
        restCommonQueryItemMockMvc.perform(get("/api/common-query-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonQueryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));

        // Check, that the count call also returns 1
        restCommonQueryItemMockMvc.perform(get("/api/common-query-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommonQueryItemShouldNotBeFound(String filter) throws Exception {
        restCommonQueryItemMockMvc.perform(get("/api/common-query-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommonQueryItemMockMvc.perform(get("/api/common-query-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommonQueryItem() throws Exception {
        // Get the commonQueryItem
        restCommonQueryItemMockMvc.perform(get("/api/common-query-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommonQueryItem() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        int databaseSizeBeforeUpdate = commonQueryItemRepository.findAll().size();

        // Update the commonQueryItem
        CommonQueryItem updatedCommonQueryItem = commonQueryItemRepository.findById(commonQueryItem.getId()).get();
        // Disconnect from session so that the updates on updatedCommonQueryItem are not directly saved in db
        em.detach(updatedCommonQueryItem);
        updatedCommonQueryItem
            .prefix(UPDATED_PREFIX)
            .fieldName(UPDATED_FIELD_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .operator(UPDATED_OPERATOR)
            .value(UPDATED_VALUE)
            .suffix(UPDATED_SUFFIX)
            .order(UPDATED_ORDER);
        CommonQueryItemDTO commonQueryItemDTO = commonQueryItemMapper.toDto(updatedCommonQueryItem);

        restCommonQueryItemMockMvc.perform(put("/api/common-query-items")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryItemDTO)))
            .andExpect(status().isOk());

        // Validate the CommonQueryItem in the database
        List<CommonQueryItem> commonQueryItemList = commonQueryItemRepository.findAll();
        assertThat(commonQueryItemList).hasSize(databaseSizeBeforeUpdate);
        CommonQueryItem testCommonQueryItem = commonQueryItemList.get(commonQueryItemList.size() - 1);
        assertThat(testCommonQueryItem.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testCommonQueryItem.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testCommonQueryItem.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testCommonQueryItem.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testCommonQueryItem.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCommonQueryItem.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testCommonQueryItem.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCommonQueryItem() throws Exception {
        int databaseSizeBeforeUpdate = commonQueryItemRepository.findAll().size();

        // Create the CommonQueryItem
        CommonQueryItemDTO commonQueryItemDTO = commonQueryItemMapper.toDto(commonQueryItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonQueryItemMockMvc.perform(put("/api/common-query-items")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommonQueryItem in the database
        List<CommonQueryItem> commonQueryItemList = commonQueryItemRepository.findAll();
        assertThat(commonQueryItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommonQueryItem() throws Exception {
        // Initialize the database
        commonQueryItemRepository.saveAndFlush(commonQueryItem);

        int databaseSizeBeforeDelete = commonQueryItemRepository.findAll().size();

        // Delete the commonQueryItem
        restCommonQueryItemMockMvc.perform(delete("/api/common-query-items/{id}", commonQueryItem.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommonQueryItem> commonQueryItemList = commonQueryItemRepository.findAll();
        assertThat(commonQueryItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
