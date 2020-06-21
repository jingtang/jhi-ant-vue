package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.ApiPermission;
import com.aidriveall.cms.domain.ApiPermission;
import com.aidriveall.cms.domain.ViewPermission;
import com.aidriveall.cms.repository.ApiPermissionRepository;
import com.aidriveall.cms.service.ApiPermissionService;
import com.aidriveall.cms.service.dto.ApiPermissionDTO;
import com.aidriveall.cms.service.mapper.ApiPermissionMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.ApiPermissionCriteria;
import com.aidriveall.cms.service.ApiPermissionQueryService;

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

import com.aidriveall.cms.domain.enumeration.ApiPermissionType;
/**
 * Integration tests for the {@link ApiPermissionResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class ApiPermissionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ApiPermissionType DEFAULT_TYPE = ApiPermissionType.BUSINESS;
    private static final ApiPermissionType UPDATED_TYPE = ApiPermissionType.API;

    @Autowired
    private ApiPermissionRepository apiPermissionRepository;

    @Autowired
    private ApiPermissionMapper apiPermissionMapper;

    @Autowired
    private ApiPermissionService apiPermissionService;

    @Autowired
    private ApiPermissionQueryService apiPermissionQueryService;

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

    private MockMvc restApiPermissionMockMvc;

    private ApiPermission apiPermission;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiPermissionResource apiPermissionResource = new ApiPermissionResource(apiPermissionService, apiPermissionQueryService);
        this.restApiPermissionMockMvc = MockMvcBuilders.standaloneSetup(apiPermissionResource)
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
    public static ApiPermission createEntity(EntityManager em) {
        ApiPermission apiPermission = new ApiPermission()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        return apiPermission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiPermission createUpdatedEntity(EntityManager em) {
        ApiPermission apiPermission = new ApiPermission()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        return apiPermission;
    }

    @BeforeEach
    public void initTest() {
        apiPermission = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiPermission() throws Exception {
        int databaseSizeBeforeCreate = apiPermissionRepository.findAll().size();

        // Create the ApiPermission
        ApiPermissionDTO apiPermissionDTO = apiPermissionMapper.toDto(apiPermission);
        restApiPermissionMockMvc.perform(post("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiPermissionDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        ApiPermission testApiPermission = apiPermissionList.get(apiPermissionList.size() - 1);
        assertThat(testApiPermission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiPermission.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApiPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiPermission.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createApiPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiPermissionRepository.findAll().size();

        // Create the ApiPermission with an existing ID
        apiPermission.setId(1L);
        ApiPermissionDTO apiPermissionDTO = apiPermissionMapper.toDto(apiPermission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiPermissionMockMvc.perform(post("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApiPermissions() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList
        restApiPermissionMockMvc.perform(get("/api/api-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getApiPermission() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get the apiPermission
        restApiPermissionMockMvc.perform(get("/api/api-permissions/{id}", apiPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiPermission.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getApiPermissionsByIdFiltering() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        Long id = apiPermission.getId();

        defaultApiPermissionShouldBeFound("id.equals=" + id);
        defaultApiPermissionShouldNotBeFound("id.notEquals=" + id);

        defaultApiPermissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApiPermissionShouldNotBeFound("id.greaterThan=" + id);

        defaultApiPermissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApiPermissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApiPermissionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where name equals to DEFAULT_NAME
        defaultApiPermissionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the apiPermissionList where name equals to UPDATED_NAME
        defaultApiPermissionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where name not equals to DEFAULT_NAME
        defaultApiPermissionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the apiPermissionList where name not equals to UPDATED_NAME
        defaultApiPermissionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApiPermissionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the apiPermissionList where name equals to UPDATED_NAME
        defaultApiPermissionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where name is not null
        defaultApiPermissionShouldBeFound("name.specified=true");

        // Get all the apiPermissionList where name is null
        defaultApiPermissionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllApiPermissionsByNameContainsSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where name contains DEFAULT_NAME
        defaultApiPermissionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the apiPermissionList where name contains UPDATED_NAME
        defaultApiPermissionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where name does not contain DEFAULT_NAME
        defaultApiPermissionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the apiPermissionList where name does not contain UPDATED_NAME
        defaultApiPermissionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllApiPermissionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where code equals to DEFAULT_CODE
        defaultApiPermissionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the apiPermissionList where code equals to UPDATED_CODE
        defaultApiPermissionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where code not equals to DEFAULT_CODE
        defaultApiPermissionShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the apiPermissionList where code not equals to UPDATED_CODE
        defaultApiPermissionShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultApiPermissionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the apiPermissionList where code equals to UPDATED_CODE
        defaultApiPermissionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where code is not null
        defaultApiPermissionShouldBeFound("code.specified=true");

        // Get all the apiPermissionList where code is null
        defaultApiPermissionShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllApiPermissionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where code contains DEFAULT_CODE
        defaultApiPermissionShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the apiPermissionList where code contains UPDATED_CODE
        defaultApiPermissionShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where code does not contain DEFAULT_CODE
        defaultApiPermissionShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the apiPermissionList where code does not contain UPDATED_CODE
        defaultApiPermissionShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllApiPermissionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where description equals to DEFAULT_DESCRIPTION
        defaultApiPermissionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the apiPermissionList where description equals to UPDATED_DESCRIPTION
        defaultApiPermissionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where description not equals to DEFAULT_DESCRIPTION
        defaultApiPermissionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the apiPermissionList where description not equals to UPDATED_DESCRIPTION
        defaultApiPermissionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultApiPermissionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the apiPermissionList where description equals to UPDATED_DESCRIPTION
        defaultApiPermissionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where description is not null
        defaultApiPermissionShouldBeFound("description.specified=true");

        // Get all the apiPermissionList where description is null
        defaultApiPermissionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllApiPermissionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where description contains DEFAULT_DESCRIPTION
        defaultApiPermissionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the apiPermissionList where description contains UPDATED_DESCRIPTION
        defaultApiPermissionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where description does not contain DEFAULT_DESCRIPTION
        defaultApiPermissionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the apiPermissionList where description does not contain UPDATED_DESCRIPTION
        defaultApiPermissionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllApiPermissionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where type equals to DEFAULT_TYPE
        defaultApiPermissionShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the apiPermissionList where type equals to UPDATED_TYPE
        defaultApiPermissionShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where type not equals to DEFAULT_TYPE
        defaultApiPermissionShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the apiPermissionList where type not equals to UPDATED_TYPE
        defaultApiPermissionShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultApiPermissionShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the apiPermissionList where type equals to UPDATED_TYPE
        defaultApiPermissionShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        // Get all the apiPermissionList where type is not null
        defaultApiPermissionShouldBeFound("type.specified=true");

        // Get all the apiPermissionList where type is null
        defaultApiPermissionShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllApiPermissionsByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);
        ApiPermission children = ApiPermissionResourceIT.createEntity(em);
        em.persist(children);
        em.flush();
        apiPermission.addChildren(children);
        apiPermissionRepository.saveAndFlush(apiPermission);
        Long childrenId = children.getId();

        // Get all the apiPermissionList where children equals to childrenId
        defaultApiPermissionShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the apiPermissionList where children equals to childrenId + 1
        defaultApiPermissionShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }


    @Test
    @Transactional
    public void getAllApiPermissionsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);
        ApiPermission parent = ApiPermissionResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        apiPermission.setParent(parent);
        apiPermissionRepository.saveAndFlush(apiPermission);
        Long parentId = parent.getId();

        // Get all the apiPermissionList where parent equals to parentId
        defaultApiPermissionShouldBeFound("parentId.equals=" + parentId);

        // Get all the apiPermissionList where parent equals to parentId + 1
        defaultApiPermissionShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllApiPermissionsByViewPermissionsIsEqualToSomething() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);
        ViewPermission viewPermissions = ViewPermissionResourceIT.createEntity(em);
        em.persist(viewPermissions);
        em.flush();
        apiPermission.addViewPermissions(viewPermissions);
        apiPermissionRepository.saveAndFlush(apiPermission);
        Long viewPermissionsId = viewPermissions.getId();

        // Get all the apiPermissionList where viewPermissions equals to viewPermissionsId
        defaultApiPermissionShouldBeFound("viewPermissionsId.equals=" + viewPermissionsId);

        // Get all the apiPermissionList where viewPermissions equals to viewPermissionsId + 1
        defaultApiPermissionShouldNotBeFound("viewPermissionsId.equals=" + (viewPermissionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApiPermissionShouldBeFound(String filter) throws Exception {
        restApiPermissionMockMvc.perform(get("/api/api-permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restApiPermissionMockMvc.perform(get("/api/api-permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApiPermissionShouldNotBeFound(String filter) throws Exception {
        restApiPermissionMockMvc.perform(get("/api/api-permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApiPermissionMockMvc.perform(get("/api/api-permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingApiPermission() throws Exception {
        // Get the apiPermission
        restApiPermissionMockMvc.perform(get("/api/api-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiPermission() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        int databaseSizeBeforeUpdate = apiPermissionRepository.findAll().size();

        // Update the apiPermission
        ApiPermission updatedApiPermission = apiPermissionRepository.findById(apiPermission.getId()).get();
        // Disconnect from session so that the updates on updatedApiPermission are not directly saved in db
        em.detach(updatedApiPermission);
        updatedApiPermission
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        ApiPermissionDTO apiPermissionDTO = apiPermissionMapper.toDto(updatedApiPermission);

        restApiPermissionMockMvc.perform(put("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiPermissionDTO)))
            .andExpect(status().isOk());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeUpdate);
        ApiPermission testApiPermission = apiPermissionList.get(apiPermissionList.size() - 1);
        assertThat(testApiPermission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiPermission.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApiPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiPermission.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingApiPermission() throws Exception {
        int databaseSizeBeforeUpdate = apiPermissionRepository.findAll().size();

        // Create the ApiPermission
        ApiPermissionDTO apiPermissionDTO = apiPermissionMapper.toDto(apiPermission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiPermissionMockMvc.perform(put("/api/api-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiPermission in the database
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiPermission() throws Exception {
        // Initialize the database
        apiPermissionRepository.saveAndFlush(apiPermission);

        int databaseSizeBeforeDelete = apiPermissionRepository.findAll().size();

        // Delete the apiPermission
        restApiPermissionMockMvc.perform(delete("/api/api-permissions/{id}", apiPermission.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApiPermission> apiPermissionList = apiPermissionRepository.findAll();
        assertThat(apiPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
