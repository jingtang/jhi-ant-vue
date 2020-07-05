package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.CommonQuery;
import com.aidriveall.cms.domain.CommonQueryItem;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.domain.CommonTable;
import com.aidriveall.cms.repository.CommonQueryRepository;
import com.aidriveall.cms.service.CommonQueryService;
import com.aidriveall.cms.service.dto.CommonQueryDTO;
import com.aidriveall.cms.service.mapper.CommonQueryMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.CommonQueryCriteria;
import com.aidriveall.cms.service.CommonQueryQueryService;

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
 * Integration tests for the {@link CommonQueryResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class CommonQueryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private CommonQueryRepository commonQueryRepository;

    @Autowired
    private CommonQueryMapper commonQueryMapper;

    @Autowired
    private CommonQueryService commonQueryService;

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

    private MockMvc restCommonQueryMockMvc;

    private CommonQuery commonQuery;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommonQueryResource commonQueryResource = new CommonQueryResource(commonQueryService, commonQueryQueryService);
        this.restCommonQueryMockMvc = MockMvcBuilders.standaloneSetup(commonQueryResource)
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
    public static CommonQuery createEntity(EntityManager em) {
        CommonQuery commonQuery = new CommonQuery()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .lastModifiedTime(DEFAULT_LAST_MODIFIED_TIME);
        return commonQuery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommonQuery createUpdatedEntity(EntityManager em) {
        CommonQuery commonQuery = new CommonQuery()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModifiedTime(UPDATED_LAST_MODIFIED_TIME);
        return commonQuery;
    }

    @BeforeEach
    public void initTest() {
        commonQuery = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommonQuery() throws Exception {
        int databaseSizeBeforeCreate = commonQueryRepository.findAll().size();

        // Create the CommonQuery
        CommonQueryDTO commonQueryDTO = commonQueryMapper.toDto(commonQuery);
        restCommonQueryMockMvc.perform(post("/api/common-queries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryDTO)))
            .andExpect(status().isCreated());

        // Validate the CommonQuery in the database
        List<CommonQuery> commonQueryList = commonQueryRepository.findAll();
        assertThat(commonQueryList).hasSize(databaseSizeBeforeCreate + 1);
        CommonQuery testCommonQuery = commonQueryList.get(commonQueryList.size() - 1);
        assertThat(testCommonQuery.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommonQuery.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommonQuery.getLastModifiedTime()).isEqualTo(DEFAULT_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void createCommonQueryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commonQueryRepository.findAll().size();

        // Create the CommonQuery with an existing ID
        commonQuery.setId(1L);
        CommonQueryDTO commonQueryDTO = commonQueryMapper.toDto(commonQuery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonQueryMockMvc.perform(post("/api/common-queries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommonQuery in the database
        List<CommonQuery> commonQueryList = commonQueryRepository.findAll();
        assertThat(commonQueryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commonQueryRepository.findAll().size();
        // set the field null
        commonQuery.setName(null);

        // Create the CommonQuery, which fails.
        CommonQueryDTO commonQueryDTO = commonQueryMapper.toDto(commonQuery);

        restCommonQueryMockMvc.perform(post("/api/common-queries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryDTO)))
            .andExpect(status().isBadRequest());

        List<CommonQuery> commonQueryList = commonQueryRepository.findAll();
        assertThat(commonQueryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommonQueries() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList
        restCommonQueryMockMvc.perform(get("/api/common-queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModifiedTime").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_TIME))));
    }
    
    @Test
    @Transactional
    public void getCommonQuery() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get the commonQuery
        restCommonQueryMockMvc.perform(get("/api/common-queries/{id}", commonQuery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commonQuery.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lastModifiedTime").value(sameInstant(DEFAULT_LAST_MODIFIED_TIME)));
    }


    @Test
    @Transactional
    public void getCommonQueriesByIdFiltering() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        Long id = commonQuery.getId();

        defaultCommonQueryShouldBeFound("id.equals=" + id);
        defaultCommonQueryShouldNotBeFound("id.notEquals=" + id);

        defaultCommonQueryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommonQueryShouldNotBeFound("id.greaterThan=" + id);

        defaultCommonQueryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommonQueryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommonQueriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where name equals to DEFAULT_NAME
        defaultCommonQueryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the commonQueryList where name equals to UPDATED_NAME
        defaultCommonQueryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where name not equals to DEFAULT_NAME
        defaultCommonQueryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the commonQueryList where name not equals to UPDATED_NAME
        defaultCommonQueryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCommonQueryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the commonQueryList where name equals to UPDATED_NAME
        defaultCommonQueryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where name is not null
        defaultCommonQueryShouldBeFound("name.specified=true");

        // Get all the commonQueryList where name is null
        defaultCommonQueryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueriesByNameContainsSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where name contains DEFAULT_NAME
        defaultCommonQueryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the commonQueryList where name contains UPDATED_NAME
        defaultCommonQueryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where name does not contain DEFAULT_NAME
        defaultCommonQueryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the commonQueryList where name does not contain UPDATED_NAME
        defaultCommonQueryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCommonQueriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where description equals to DEFAULT_DESCRIPTION
        defaultCommonQueryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the commonQueryList where description equals to UPDATED_DESCRIPTION
        defaultCommonQueryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where description not equals to DEFAULT_DESCRIPTION
        defaultCommonQueryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the commonQueryList where description not equals to UPDATED_DESCRIPTION
        defaultCommonQueryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCommonQueryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the commonQueryList where description equals to UPDATED_DESCRIPTION
        defaultCommonQueryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where description is not null
        defaultCommonQueryShouldBeFound("description.specified=true");

        // Get all the commonQueryList where description is null
        defaultCommonQueryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommonQueriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where description contains DEFAULT_DESCRIPTION
        defaultCommonQueryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the commonQueryList where description contains UPDATED_DESCRIPTION
        defaultCommonQueryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where description does not contain DEFAULT_DESCRIPTION
        defaultCommonQueryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the commonQueryList where description does not contain UPDATED_DESCRIPTION
        defaultCommonQueryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime equals to DEFAULT_LAST_MODIFIED_TIME
        defaultCommonQueryShouldBeFound("lastModifiedTime.equals=" + DEFAULT_LAST_MODIFIED_TIME);

        // Get all the commonQueryList where lastModifiedTime equals to UPDATED_LAST_MODIFIED_TIME
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.equals=" + UPDATED_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime not equals to DEFAULT_LAST_MODIFIED_TIME
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.notEquals=" + DEFAULT_LAST_MODIFIED_TIME);

        // Get all the commonQueryList where lastModifiedTime not equals to UPDATED_LAST_MODIFIED_TIME
        defaultCommonQueryShouldBeFound("lastModifiedTime.notEquals=" + UPDATED_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime in DEFAULT_LAST_MODIFIED_TIME or UPDATED_LAST_MODIFIED_TIME
        defaultCommonQueryShouldBeFound("lastModifiedTime.in=" + DEFAULT_LAST_MODIFIED_TIME + "," + UPDATED_LAST_MODIFIED_TIME);

        // Get all the commonQueryList where lastModifiedTime equals to UPDATED_LAST_MODIFIED_TIME
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.in=" + UPDATED_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime is not null
        defaultCommonQueryShouldBeFound("lastModifiedTime.specified=true");

        // Get all the commonQueryList where lastModifiedTime is null
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime is greater than or equal to DEFAULT_LAST_MODIFIED_TIME
        defaultCommonQueryShouldBeFound("lastModifiedTime.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED_TIME);

        // Get all the commonQueryList where lastModifiedTime is greater than or equal to UPDATED_LAST_MODIFIED_TIME
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime is less than or equal to DEFAULT_LAST_MODIFIED_TIME
        defaultCommonQueryShouldBeFound("lastModifiedTime.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED_TIME);

        // Get all the commonQueryList where lastModifiedTime is less than or equal to SMALLER_LAST_MODIFIED_TIME
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.lessThanOrEqual=" + SMALLER_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime is less than DEFAULT_LAST_MODIFIED_TIME
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.lessThan=" + DEFAULT_LAST_MODIFIED_TIME);

        // Get all the commonQueryList where lastModifiedTime is less than UPDATED_LAST_MODIFIED_TIME
        defaultCommonQueryShouldBeFound("lastModifiedTime.lessThan=" + UPDATED_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void getAllCommonQueriesByLastModifiedTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        // Get all the commonQueryList where lastModifiedTime is greater than DEFAULT_LAST_MODIFIED_TIME
        defaultCommonQueryShouldNotBeFound("lastModifiedTime.greaterThan=" + DEFAULT_LAST_MODIFIED_TIME);

        // Get all the commonQueryList where lastModifiedTime is greater than SMALLER_LAST_MODIFIED_TIME
        defaultCommonQueryShouldBeFound("lastModifiedTime.greaterThan=" + SMALLER_LAST_MODIFIED_TIME);
    }


    @Test
    @Transactional
    public void getAllCommonQueriesByItemsIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);
        CommonQueryItem items = CommonQueryItemResourceIT.createEntity(em);
        em.persist(items);
        em.flush();
        commonQuery.addItems(items);
        commonQueryRepository.saveAndFlush(commonQuery);
        Long itemsId = items.getId();

        // Get all the commonQueryList where items equals to itemsId
        defaultCommonQueryShouldBeFound("itemsId.equals=" + itemsId);

        // Get all the commonQueryList where items equals to itemsId + 1
        defaultCommonQueryShouldNotBeFound("itemsId.equals=" + (itemsId + 1));
    }


    @Test
    @Transactional
    public void getAllCommonQueriesByModifierIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);
        User modifier = UserResourceIT.createEntity(em);
        em.persist(modifier);
        em.flush();
        commonQuery.setModifier(modifier);
        commonQueryRepository.saveAndFlush(commonQuery);
        Long modifierId = modifier.getId();

        // Get all the commonQueryList where modifier equals to modifierId
        defaultCommonQueryShouldBeFound("modifierId.equals=" + modifierId);

        // Get all the commonQueryList where modifier equals to modifierId + 1
        defaultCommonQueryShouldNotBeFound("modifierId.equals=" + (modifierId + 1));
    }


    @Test
    @Transactional
    public void getAllCommonQueriesByCommonTableIsEqualToSomething() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);
        CommonTable commonTable = CommonTableResourceIT.createEntity(em);
        em.persist(commonTable);
        em.flush();
        commonQuery.setCommonTable(commonTable);
        commonQueryRepository.saveAndFlush(commonQuery);
        Long commonTableId = commonTable.getId();

        // Get all the commonQueryList where commonTable equals to commonTableId
        defaultCommonQueryShouldBeFound("commonTableId.equals=" + commonTableId);

        // Get all the commonQueryList where commonTable equals to commonTableId + 1
        defaultCommonQueryShouldNotBeFound("commonTableId.equals=" + (commonTableId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommonQueryShouldBeFound(String filter) throws Exception {
        restCommonQueryMockMvc.perform(get("/api/common-queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonQuery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModifiedTime").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_TIME))));

        // Check, that the count call also returns 1
        restCommonQueryMockMvc.perform(get("/api/common-queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommonQueryShouldNotBeFound(String filter) throws Exception {
        restCommonQueryMockMvc.perform(get("/api/common-queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommonQueryMockMvc.perform(get("/api/common-queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommonQuery() throws Exception {
        // Get the commonQuery
        restCommonQueryMockMvc.perform(get("/api/common-queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommonQuery() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        int databaseSizeBeforeUpdate = commonQueryRepository.findAll().size();

        // Update the commonQuery
        CommonQuery updatedCommonQuery = commonQueryRepository.findById(commonQuery.getId()).get();
        // Disconnect from session so that the updates on updatedCommonQuery are not directly saved in db
        em.detach(updatedCommonQuery);
        updatedCommonQuery
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .lastModifiedTime(UPDATED_LAST_MODIFIED_TIME);
        CommonQueryDTO commonQueryDTO = commonQueryMapper.toDto(updatedCommonQuery);

        restCommonQueryMockMvc.perform(put("/api/common-queries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryDTO)))
            .andExpect(status().isOk());

        // Validate the CommonQuery in the database
        List<CommonQuery> commonQueryList = commonQueryRepository.findAll();
        assertThat(commonQueryList).hasSize(databaseSizeBeforeUpdate);
        CommonQuery testCommonQuery = commonQueryList.get(commonQueryList.size() - 1);
        assertThat(testCommonQuery.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommonQuery.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommonQuery.getLastModifiedTime()).isEqualTo(UPDATED_LAST_MODIFIED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCommonQuery() throws Exception {
        int databaseSizeBeforeUpdate = commonQueryRepository.findAll().size();

        // Create the CommonQuery
        CommonQueryDTO commonQueryDTO = commonQueryMapper.toDto(commonQuery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonQueryMockMvc.perform(put("/api/common-queries")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commonQueryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommonQuery in the database
        List<CommonQuery> commonQueryList = commonQueryRepository.findAll();
        assertThat(commonQueryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommonQuery() throws Exception {
        // Initialize the database
        commonQueryRepository.saveAndFlush(commonQuery);

        int databaseSizeBeforeDelete = commonQueryRepository.findAll().size();

        // Delete the commonQuery
        restCommonQueryMockMvc.perform(delete("/api/common-queries/{id}", commonQuery.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommonQuery> commonQueryList = commonQueryRepository.findAll();
        assertThat(commonQueryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
