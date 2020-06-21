package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.CompanyUser;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.domain.CompanyCustomer;
import com.aidriveall.cms.repository.CompanyUserRepository;
import com.aidriveall.cms.service.CompanyUserService;
import com.aidriveall.cms.service.dto.CompanyUserDTO;
import com.aidriveall.cms.service.mapper.CompanyUserMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.CompanyUserCriteria;
import com.aidriveall.cms.service.CompanyUserQueryService;

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
 * Integration tests for the {@link CompanyUserResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class CompanyUserResourceIT {

    @Autowired
    private CompanyUserRepository companyUserRepository;

    @Autowired
    private CompanyUserMapper companyUserMapper;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private CompanyUserQueryService companyUserQueryService;

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

    private MockMvc restCompanyUserMockMvc;

    private CompanyUser companyUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyUserResource companyUserResource = new CompanyUserResource(companyUserService, companyUserQueryService);
        this.restCompanyUserMockMvc = MockMvcBuilders.standaloneSetup(companyUserResource)
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
    public static CompanyUser createEntity(EntityManager em) {
        CompanyUser companyUser = new CompanyUser();
        return companyUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyUser createUpdatedEntity(EntityManager em) {
        CompanyUser companyUser = new CompanyUser();
        return companyUser;
    }

    @BeforeEach
    public void initTest() {
        companyUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyUser() throws Exception {
        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);
        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createCompanyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyUserRepository.findAll().size();

        // Create the CompanyUser with an existing ID
        companyUser.setId(1L);
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyUserMockMvc.perform(post("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompanyUsers() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get all the companyUserList
        restCompanyUserMockMvc.perform(get("/api/company-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyUser.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        // Get the companyUser
        restCompanyUserMockMvc.perform(get("/api/company-users/{id}", companyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyUser.getId().intValue()));
    }


    @Test
    @Transactional
    public void getCompanyUsersByIdFiltering() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        Long id = companyUser.getId();

        defaultCompanyUserShouldBeFound("id.equals=" + id);
        defaultCompanyUserShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyUserShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        companyUser.setUser(user);
        companyUserRepository.saveAndFlush(companyUser);
        Long userId = user.getId();

        // Get all the companyUserList where user equals to userId
        defaultCompanyUserShouldBeFound("userId.equals=" + userId);

        // Get all the companyUserList where user equals to userId + 1
        defaultCompanyUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllCompanyUsersByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);
        CompanyCustomer company = CompanyCustomerResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        companyUser.setCompany(company);
        companyUserRepository.saveAndFlush(companyUser);
        Long companyId = company.getId();

        // Get all the companyUserList where company equals to companyId
        defaultCompanyUserShouldBeFound("companyId.equals=" + companyId);

        // Get all the companyUserList where company equals to companyId + 1
        defaultCompanyUserShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyUserShouldBeFound(String filter) throws Exception {
        restCompanyUserMockMvc.perform(get("/api/company-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyUser.getId().intValue())));

        // Check, that the count call also returns 1
        restCompanyUserMockMvc.perform(get("/api/company-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyUserShouldNotBeFound(String filter) throws Exception {
        restCompanyUserMockMvc.perform(get("/api/company-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyUserMockMvc.perform(get("/api/company-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompanyUser() throws Exception {
        // Get the companyUser
        restCompanyUserMockMvc.perform(get("/api/company-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Update the companyUser
        CompanyUser updatedCompanyUser = companyUserRepository.findById(companyUser.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyUser are not directly saved in db
        em.detach(updatedCompanyUser);
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(updatedCompanyUser);

        restCompanyUserMockMvc.perform(put("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
        CompanyUser testCompanyUser = companyUserList.get(companyUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyUser() throws Exception {
        int databaseSizeBeforeUpdate = companyUserRepository.findAll().size();

        // Create the CompanyUser
        CompanyUserDTO companyUserDTO = companyUserMapper.toDto(companyUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyUserMockMvc.perform(put("/api/company-users")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyUser in the database
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompanyUser() throws Exception {
        // Initialize the database
        companyUserRepository.saveAndFlush(companyUser);

        int databaseSizeBeforeDelete = companyUserRepository.findAll().size();

        // Delete the companyUser
        restCompanyUserMockMvc.perform(delete("/api/company-users/{id}", companyUser.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyUser> companyUserList = companyUserRepository.findAll();
        assertThat(companyUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
