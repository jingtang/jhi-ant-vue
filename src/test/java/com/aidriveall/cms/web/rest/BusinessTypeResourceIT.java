package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.BusinessType;
import com.aidriveall.cms.repository.BusinessTypeRepository;
import com.aidriveall.cms.service.BusinessTypeService;
import com.aidriveall.cms.service.dto.BusinessTypeDTO;
import com.aidriveall.cms.service.mapper.BusinessTypeMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.BusinessTypeCriteria;
import com.aidriveall.cms.service.BusinessTypeQueryService;

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
 * Integration tests for the {@link BusinessTypeResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class BusinessTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Autowired
    private BusinessTypeRepository businessTypeRepository;

    @Autowired
    private BusinessTypeMapper businessTypeMapper;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private BusinessTypeQueryService businessTypeQueryService;

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

    private MockMvc restBusinessTypeMockMvc;

    private BusinessType businessType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BusinessTypeResource businessTypeResource = new BusinessTypeResource(businessTypeService, businessTypeQueryService);
        this.restBusinessTypeMockMvc = MockMvcBuilders.standaloneSetup(businessTypeResource)
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
    public static BusinessType createEntity(EntityManager em) {
        BusinessType businessType = new BusinessType()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .icon(DEFAULT_ICON);
        return businessType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessType createUpdatedEntity(EntityManager em) {
        BusinessType businessType = new BusinessType()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .icon(UPDATED_ICON);
        return businessType;
    }

    @BeforeEach
    public void initTest() {
        businessType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessType() throws Exception {
        int databaseSizeBeforeCreate = businessTypeRepository.findAll().size();

        // Create the BusinessType
        BusinessTypeDTO businessTypeDTO = businessTypeMapper.toDto(businessType);
        restBusinessTypeMockMvc.perform(post("/api/business-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BusinessType in the database
        List<BusinessType> businessTypeList = businessTypeRepository.findAll();
        assertThat(businessTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessType testBusinessType = businessTypeList.get(businessTypeList.size() - 1);
        assertThat(testBusinessType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBusinessType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBusinessType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBusinessType.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createBusinessTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessTypeRepository.findAll().size();

        // Create the BusinessType with an existing ID
        businessType.setId(1L);
        BusinessTypeDTO businessTypeDTO = businessTypeMapper.toDto(businessType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessTypeMockMvc.perform(post("/api/business-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessType in the database
        List<BusinessType> businessTypeList = businessTypeRepository.findAll();
        assertThat(businessTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBusinessTypes() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList
        restBusinessTypeMockMvc.perform(get("/api/business-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }
    
    @Test
    @Transactional
    public void getBusinessType() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get the businessType
        restBusinessTypeMockMvc.perform(get("/api/business-types/{id}", businessType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }


    @Test
    @Transactional
    public void getBusinessTypesByIdFiltering() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        Long id = businessType.getId();

        defaultBusinessTypeShouldBeFound("id.equals=" + id);
        defaultBusinessTypeShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBusinessTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where name equals to DEFAULT_NAME
        defaultBusinessTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the businessTypeList where name equals to UPDATED_NAME
        defaultBusinessTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where name not equals to DEFAULT_NAME
        defaultBusinessTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the businessTypeList where name not equals to UPDATED_NAME
        defaultBusinessTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBusinessTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the businessTypeList where name equals to UPDATED_NAME
        defaultBusinessTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where name is not null
        defaultBusinessTypeShouldBeFound("name.specified=true");

        // Get all the businessTypeList where name is null
        defaultBusinessTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusinessTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where name contains DEFAULT_NAME
        defaultBusinessTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the businessTypeList where name contains UPDATED_NAME
        defaultBusinessTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where name does not contain DEFAULT_NAME
        defaultBusinessTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the businessTypeList where name does not contain UPDATED_NAME
        defaultBusinessTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllBusinessTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where code equals to DEFAULT_CODE
        defaultBusinessTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the businessTypeList where code equals to UPDATED_CODE
        defaultBusinessTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where code not equals to DEFAULT_CODE
        defaultBusinessTypeShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the businessTypeList where code not equals to UPDATED_CODE
        defaultBusinessTypeShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultBusinessTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the businessTypeList where code equals to UPDATED_CODE
        defaultBusinessTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where code is not null
        defaultBusinessTypeShouldBeFound("code.specified=true");

        // Get all the businessTypeList where code is null
        defaultBusinessTypeShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusinessTypesByCodeContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where code contains DEFAULT_CODE
        defaultBusinessTypeShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the businessTypeList where code contains UPDATED_CODE
        defaultBusinessTypeShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where code does not contain DEFAULT_CODE
        defaultBusinessTypeShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the businessTypeList where code does not contain UPDATED_CODE
        defaultBusinessTypeShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllBusinessTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where description equals to DEFAULT_DESCRIPTION
        defaultBusinessTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the businessTypeList where description equals to UPDATED_DESCRIPTION
        defaultBusinessTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultBusinessTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the businessTypeList where description not equals to UPDATED_DESCRIPTION
        defaultBusinessTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBusinessTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the businessTypeList where description equals to UPDATED_DESCRIPTION
        defaultBusinessTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where description is not null
        defaultBusinessTypeShouldBeFound("description.specified=true");

        // Get all the businessTypeList where description is null
        defaultBusinessTypeShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusinessTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where description contains DEFAULT_DESCRIPTION
        defaultBusinessTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the businessTypeList where description contains UPDATED_DESCRIPTION
        defaultBusinessTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultBusinessTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the businessTypeList where description does not contain UPDATED_DESCRIPTION
        defaultBusinessTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllBusinessTypesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where icon equals to DEFAULT_ICON
        defaultBusinessTypeShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the businessTypeList where icon equals to UPDATED_ICON
        defaultBusinessTypeShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where icon not equals to DEFAULT_ICON
        defaultBusinessTypeShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the businessTypeList where icon not equals to UPDATED_ICON
        defaultBusinessTypeShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultBusinessTypeShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the businessTypeList where icon equals to UPDATED_ICON
        defaultBusinessTypeShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where icon is not null
        defaultBusinessTypeShouldBeFound("icon.specified=true");

        // Get all the businessTypeList where icon is null
        defaultBusinessTypeShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllBusinessTypesByIconContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where icon contains DEFAULT_ICON
        defaultBusinessTypeShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the businessTypeList where icon contains UPDATED_ICON
        defaultBusinessTypeShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllBusinessTypesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        // Get all the businessTypeList where icon does not contain DEFAULT_ICON
        defaultBusinessTypeShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the businessTypeList where icon does not contain UPDATED_ICON
        defaultBusinessTypeShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessTypeShouldBeFound(String filter) throws Exception {
        restBusinessTypeMockMvc.perform(get("/api/business-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));

        // Check, that the count call also returns 1
        restBusinessTypeMockMvc.perform(get("/api/business-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessTypeShouldNotBeFound(String filter) throws Exception {
        restBusinessTypeMockMvc.perform(get("/api/business-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessTypeMockMvc.perform(get("/api/business-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBusinessType() throws Exception {
        // Get the businessType
        restBusinessTypeMockMvc.perform(get("/api/business-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessType() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        int databaseSizeBeforeUpdate = businessTypeRepository.findAll().size();

        // Update the businessType
        BusinessType updatedBusinessType = businessTypeRepository.findById(businessType.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessType are not directly saved in db
        em.detach(updatedBusinessType);
        updatedBusinessType
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .icon(UPDATED_ICON);
        BusinessTypeDTO businessTypeDTO = businessTypeMapper.toDto(updatedBusinessType);

        restBusinessTypeMockMvc.perform(put("/api/business-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessTypeDTO)))
            .andExpect(status().isOk());

        // Validate the BusinessType in the database
        List<BusinessType> businessTypeList = businessTypeRepository.findAll();
        assertThat(businessTypeList).hasSize(databaseSizeBeforeUpdate);
        BusinessType testBusinessType = businessTypeList.get(businessTypeList.size() - 1);
        assertThat(testBusinessType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBusinessType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBusinessType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBusinessType.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessType() throws Exception {
        int databaseSizeBeforeUpdate = businessTypeRepository.findAll().size();

        // Create the BusinessType
        BusinessTypeDTO businessTypeDTO = businessTypeMapper.toDto(businessType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessTypeMockMvc.perform(put("/api/business-types")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(businessTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BusinessType in the database
        List<BusinessType> businessTypeList = businessTypeRepository.findAll();
        assertThat(businessTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessType() throws Exception {
        // Initialize the database
        businessTypeRepository.saveAndFlush(businessType);

        int databaseSizeBeforeDelete = businessTypeRepository.findAll().size();

        // Delete the businessType
        restBusinessTypeMockMvc.perform(delete("/api/business-types/{id}", businessType.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessType> businessTypeList = businessTypeRepository.findAll();
        assertThat(businessTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
