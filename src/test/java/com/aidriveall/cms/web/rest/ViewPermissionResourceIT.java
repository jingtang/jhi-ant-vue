package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.ViewPermission;
import com.aidriveall.cms.domain.ViewPermission;
import com.aidriveall.cms.domain.ApiPermission;
import com.aidriveall.cms.domain.Authority;
import com.aidriveall.cms.repository.ViewPermissionRepository;
import com.aidriveall.cms.service.ViewPermissionService;
import com.aidriveall.cms.service.dto.ViewPermissionDTO;
import com.aidriveall.cms.service.mapper.ViewPermissionMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.ViewPermissionCriteria;
import com.aidriveall.cms.service.ViewPermissionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.aidriveall.cms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aidriveall.cms.domain.enumeration.TargetType;
import com.aidriveall.cms.domain.enumeration.ViewPermissionType;
/**
 * Integration tests for the {@link ViewPermissionResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class ViewPermissionResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_I_18_N = "AAAAAAAAAA";
    private static final String UPDATED_I_18_N = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GROUP = false;
    private static final Boolean UPDATED_GROUP = true;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_LINK = "BBBBBBBBBB";

    private static final TargetType DEFAULT_TARGET = TargetType.BLANK;
    private static final TargetType UPDATED_TARGET = TargetType.SELF;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISABLED = false;
    private static final Boolean UPDATED_DISABLED = true;

    private static final Boolean DEFAULT_HIDE = false;
    private static final Boolean UPDATED_HIDE = true;

    private static final Boolean DEFAULT_HIDE_IN_BREADCRUMB = false;
    private static final Boolean UPDATED_HIDE_IN_BREADCRUMB = true;

    private static final Boolean DEFAULT_SHORTCUT = false;
    private static final Boolean UPDATED_SHORTCUT = true;

    private static final Boolean DEFAULT_SHORTCUT_ROOT = false;
    private static final Boolean UPDATED_SHORTCUT_ROOT = true;

    private static final Boolean DEFAULT_REUSE = false;
    private static final Boolean UPDATED_REUSE = true;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ViewPermissionType DEFAULT_TYPE = ViewPermissionType.MENU;
    private static final ViewPermissionType UPDATED_TYPE = ViewPermissionType.BUTTON;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

    private static final String DEFAULT_API_PERMISSION_CODES = "AAAAAAAAAA";
    private static final String UPDATED_API_PERMISSION_CODES = "BBBBBBBBBB";

    @Autowired
    private ViewPermissionRepository viewPermissionRepository;

    @Mock
    private ViewPermissionRepository viewPermissionRepositoryMock;

    @Autowired
    private ViewPermissionMapper viewPermissionMapper;

    @Mock
    private ViewPermissionService viewPermissionServiceMock;

    @Autowired
    private ViewPermissionService viewPermissionService;

    @Autowired
    private ViewPermissionQueryService viewPermissionQueryService;

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

    private MockMvc restViewPermissionMockMvc;

    private ViewPermission viewPermission;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ViewPermissionResource viewPermissionResource = new ViewPermissionResource(viewPermissionService, viewPermissionQueryService);
        this.restViewPermissionMockMvc = MockMvcBuilders.standaloneSetup(viewPermissionResource)
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
    public static ViewPermission createEntity(EntityManager em) {
        ViewPermission viewPermission = new ViewPermission()
            .text(DEFAULT_TEXT)
            .i18n(DEFAULT_I_18_N)
            .group(DEFAULT_GROUP)
            .link(DEFAULT_LINK)
            .externalLink(DEFAULT_EXTERNAL_LINK)
            .target(DEFAULT_TARGET)
            .icon(DEFAULT_ICON)
            .disabled(DEFAULT_DISABLED)
            .hide(DEFAULT_HIDE)
            .hideInBreadcrumb(DEFAULT_HIDE_IN_BREADCRUMB)
            .shortcut(DEFAULT_SHORTCUT)
            .shortcutRoot(DEFAULT_SHORTCUT_ROOT)
            .reuse(DEFAULT_REUSE)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .order(DEFAULT_ORDER)
            .apiPermissionCodes(DEFAULT_API_PERMISSION_CODES);
        return viewPermission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewPermission createUpdatedEntity(EntityManager em) {
        ViewPermission viewPermission = new ViewPermission()
            .text(UPDATED_TEXT)
            .i18n(UPDATED_I_18_N)
            .group(UPDATED_GROUP)
            .link(UPDATED_LINK)
            .externalLink(UPDATED_EXTERNAL_LINK)
            .target(UPDATED_TARGET)
            .icon(UPDATED_ICON)
            .disabled(UPDATED_DISABLED)
            .hide(UPDATED_HIDE)
            .hideInBreadcrumb(UPDATED_HIDE_IN_BREADCRUMB)
            .shortcut(UPDATED_SHORTCUT)
            .shortcutRoot(UPDATED_SHORTCUT_ROOT)
            .reuse(UPDATED_REUSE)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .order(UPDATED_ORDER)
            .apiPermissionCodes(UPDATED_API_PERMISSION_CODES);
        return viewPermission;
    }

    @BeforeEach
    public void initTest() {
        viewPermission = createEntity(em);
    }

    @Test
    @Transactional
    public void createViewPermission() throws Exception {
        int databaseSizeBeforeCreate = viewPermissionRepository.findAll().size();

        // Create the ViewPermission
        ViewPermissionDTO viewPermissionDTO = viewPermissionMapper.toDto(viewPermission);
        restViewPermissionMockMvc.perform(post("/api/view-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewPermissionDTO)))
            .andExpect(status().isCreated());

        // Validate the ViewPermission in the database
        List<ViewPermission> viewPermissionList = viewPermissionRepository.findAll();
        assertThat(viewPermissionList).hasSize(databaseSizeBeforeCreate + 1);
        ViewPermission testViewPermission = viewPermissionList.get(viewPermissionList.size() - 1);
        assertThat(testViewPermission.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testViewPermission.geti18n()).isEqualTo(DEFAULT_I_18_N);
        assertThat(testViewPermission.isGroup()).isEqualTo(DEFAULT_GROUP);
        assertThat(testViewPermission.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testViewPermission.getExternalLink()).isEqualTo(DEFAULT_EXTERNAL_LINK);
        assertThat(testViewPermission.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testViewPermission.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testViewPermission.isDisabled()).isEqualTo(DEFAULT_DISABLED);
        assertThat(testViewPermission.isHide()).isEqualTo(DEFAULT_HIDE);
        assertThat(testViewPermission.isHideInBreadcrumb()).isEqualTo(DEFAULT_HIDE_IN_BREADCRUMB);
        assertThat(testViewPermission.isShortcut()).isEqualTo(DEFAULT_SHORTCUT);
        assertThat(testViewPermission.isShortcutRoot()).isEqualTo(DEFAULT_SHORTCUT_ROOT);
        assertThat(testViewPermission.isReuse()).isEqualTo(DEFAULT_REUSE);
        assertThat(testViewPermission.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testViewPermission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testViewPermission.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testViewPermission.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testViewPermission.getApiPermissionCodes()).isEqualTo(DEFAULT_API_PERMISSION_CODES);
    }

    @Test
    @Transactional
    public void createViewPermissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = viewPermissionRepository.findAll().size();

        // Create the ViewPermission with an existing ID
        viewPermission.setId(1L);
        ViewPermissionDTO viewPermissionDTO = viewPermissionMapper.toDto(viewPermission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restViewPermissionMockMvc.perform(post("/api/view-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ViewPermission in the database
        List<ViewPermission> viewPermissionList = viewPermissionRepository.findAll();
        assertThat(viewPermissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllViewPermissions() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList
        restViewPermissionMockMvc.perform(get("/api/view-permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].i18n").value(hasItem(DEFAULT_I_18_N)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].externalLink").value(hasItem(DEFAULT_EXTERNAL_LINK)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].hide").value(hasItem(DEFAULT_HIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].hideInBreadcrumb").value(hasItem(DEFAULT_HIDE_IN_BREADCRUMB.booleanValue())))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.booleanValue())))
            .andExpect(jsonPath("$.[*].shortcutRoot").value(hasItem(DEFAULT_SHORTCUT_ROOT.booleanValue())))
            .andExpect(jsonPath("$.[*].reuse").value(hasItem(DEFAULT_REUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].apiPermissionCodes").value(hasItem(DEFAULT_API_PERMISSION_CODES)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllViewPermissionsWithEagerRelationshipsIsEnabled() throws Exception {
        ViewPermissionResource viewPermissionResource = new ViewPermissionResource(viewPermissionServiceMock, viewPermissionQueryService);
        when(viewPermissionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restViewPermissionMockMvc = MockMvcBuilders.standaloneSetup(viewPermissionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restViewPermissionMockMvc.perform(get("/api/view-permissions?eagerload=true"))
        .andExpect(status().isOk());

        verify(viewPermissionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllViewPermissionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ViewPermissionResource viewPermissionResource = new ViewPermissionResource(viewPermissionServiceMock, viewPermissionQueryService);
            when(viewPermissionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restViewPermissionMockMvc = MockMvcBuilders.standaloneSetup(viewPermissionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restViewPermissionMockMvc.perform(get("/api/view-permissions?eagerload=true"))
        .andExpect(status().isOk());

            verify(viewPermissionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getViewPermission() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get the viewPermission
        restViewPermissionMockMvc.perform(get("/api/view-permissions/{id}", viewPermission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viewPermission.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.i18n").value(DEFAULT_I_18_N))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.booleanValue()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.externalLink").value(DEFAULT_EXTERNAL_LINK))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.disabled").value(DEFAULT_DISABLED.booleanValue()))
            .andExpect(jsonPath("$.hide").value(DEFAULT_HIDE.booleanValue()))
            .andExpect(jsonPath("$.hideInBreadcrumb").value(DEFAULT_HIDE_IN_BREADCRUMB.booleanValue()))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.booleanValue()))
            .andExpect(jsonPath("$.shortcutRoot").value(DEFAULT_SHORTCUT_ROOT.booleanValue()))
            .andExpect(jsonPath("$.reuse").value(DEFAULT_REUSE.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.apiPermissionCodes").value(DEFAULT_API_PERMISSION_CODES));
    }


    @Test
    @Transactional
    public void getViewPermissionsByIdFiltering() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        Long id = viewPermission.getId();

        defaultViewPermissionShouldBeFound("id.equals=" + id);
        defaultViewPermissionShouldNotBeFound("id.notEquals=" + id);

        defaultViewPermissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultViewPermissionShouldNotBeFound("id.greaterThan=" + id);

        defaultViewPermissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultViewPermissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where text equals to DEFAULT_TEXT
        defaultViewPermissionShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the viewPermissionList where text equals to UPDATED_TEXT
        defaultViewPermissionShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where text not equals to DEFAULT_TEXT
        defaultViewPermissionShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the viewPermissionList where text not equals to UPDATED_TEXT
        defaultViewPermissionShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultViewPermissionShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the viewPermissionList where text equals to UPDATED_TEXT
        defaultViewPermissionShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where text is not null
        defaultViewPermissionShouldBeFound("text.specified=true");

        // Get all the viewPermissionList where text is null
        defaultViewPermissionShouldNotBeFound("text.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByTextContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where text contains DEFAULT_TEXT
        defaultViewPermissionShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the viewPermissionList where text contains UPDATED_TEXT
        defaultViewPermissionShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTextNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where text does not contain DEFAULT_TEXT
        defaultViewPermissionShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the viewPermissionList where text does not contain UPDATED_TEXT
        defaultViewPermissionShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByi18nIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where i18n equals to DEFAULT_I_18_N
        defaultViewPermissionShouldBeFound("i18n.equals=" + DEFAULT_I_18_N);

        // Get all the viewPermissionList where i18n equals to UPDATED_I_18_N
        defaultViewPermissionShouldNotBeFound("i18n.equals=" + UPDATED_I_18_N);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByi18nIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where i18n not equals to DEFAULT_I_18_N
        defaultViewPermissionShouldNotBeFound("i18n.notEquals=" + DEFAULT_I_18_N);

        // Get all the viewPermissionList where i18n not equals to UPDATED_I_18_N
        defaultViewPermissionShouldBeFound("i18n.notEquals=" + UPDATED_I_18_N);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByi18nIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where i18n in DEFAULT_I_18_N or UPDATED_I_18_N
        defaultViewPermissionShouldBeFound("i18n.in=" + DEFAULT_I_18_N + "," + UPDATED_I_18_N);

        // Get all the viewPermissionList where i18n equals to UPDATED_I_18_N
        defaultViewPermissionShouldNotBeFound("i18n.in=" + UPDATED_I_18_N);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByi18nIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where i18n is not null
        defaultViewPermissionShouldBeFound("i18n.specified=true");

        // Get all the viewPermissionList where i18n is null
        defaultViewPermissionShouldNotBeFound("i18n.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByi18nContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where i18n contains DEFAULT_I_18_N
        defaultViewPermissionShouldBeFound("i18n.contains=" + DEFAULT_I_18_N);

        // Get all the viewPermissionList where i18n contains UPDATED_I_18_N
        defaultViewPermissionShouldNotBeFound("i18n.contains=" + UPDATED_I_18_N);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByi18nNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where i18n does not contain DEFAULT_I_18_N
        defaultViewPermissionShouldNotBeFound("i18n.doesNotContain=" + DEFAULT_I_18_N);

        // Get all the viewPermissionList where i18n does not contain UPDATED_I_18_N
        defaultViewPermissionShouldBeFound("i18n.doesNotContain=" + UPDATED_I_18_N);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where group equals to DEFAULT_GROUP
        defaultViewPermissionShouldBeFound("group.equals=" + DEFAULT_GROUP);

        // Get all the viewPermissionList where group equals to UPDATED_GROUP
        defaultViewPermissionShouldNotBeFound("group.equals=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where group not equals to DEFAULT_GROUP
        defaultViewPermissionShouldNotBeFound("group.notEquals=" + DEFAULT_GROUP);

        // Get all the viewPermissionList where group not equals to UPDATED_GROUP
        defaultViewPermissionShouldBeFound("group.notEquals=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByGroupIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where group in DEFAULT_GROUP or UPDATED_GROUP
        defaultViewPermissionShouldBeFound("group.in=" + DEFAULT_GROUP + "," + UPDATED_GROUP);

        // Get all the viewPermissionList where group equals to UPDATED_GROUP
        defaultViewPermissionShouldNotBeFound("group.in=" + UPDATED_GROUP);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where group is not null
        defaultViewPermissionShouldBeFound("group.specified=true");

        // Get all the viewPermissionList where group is null
        defaultViewPermissionShouldNotBeFound("group.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where link equals to DEFAULT_LINK
        defaultViewPermissionShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the viewPermissionList where link equals to UPDATED_LINK
        defaultViewPermissionShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where link not equals to DEFAULT_LINK
        defaultViewPermissionShouldNotBeFound("link.notEquals=" + DEFAULT_LINK);

        // Get all the viewPermissionList where link not equals to UPDATED_LINK
        defaultViewPermissionShouldBeFound("link.notEquals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where link in DEFAULT_LINK or UPDATED_LINK
        defaultViewPermissionShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the viewPermissionList where link equals to UPDATED_LINK
        defaultViewPermissionShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where link is not null
        defaultViewPermissionShouldBeFound("link.specified=true");

        // Get all the viewPermissionList where link is null
        defaultViewPermissionShouldNotBeFound("link.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByLinkContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where link contains DEFAULT_LINK
        defaultViewPermissionShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the viewPermissionList where link contains UPDATED_LINK
        defaultViewPermissionShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where link does not contain DEFAULT_LINK
        defaultViewPermissionShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the viewPermissionList where link does not contain UPDATED_LINK
        defaultViewPermissionShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByExternalLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where externalLink equals to DEFAULT_EXTERNAL_LINK
        defaultViewPermissionShouldBeFound("externalLink.equals=" + DEFAULT_EXTERNAL_LINK);

        // Get all the viewPermissionList where externalLink equals to UPDATED_EXTERNAL_LINK
        defaultViewPermissionShouldNotBeFound("externalLink.equals=" + UPDATED_EXTERNAL_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByExternalLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where externalLink not equals to DEFAULT_EXTERNAL_LINK
        defaultViewPermissionShouldNotBeFound("externalLink.notEquals=" + DEFAULT_EXTERNAL_LINK);

        // Get all the viewPermissionList where externalLink not equals to UPDATED_EXTERNAL_LINK
        defaultViewPermissionShouldBeFound("externalLink.notEquals=" + UPDATED_EXTERNAL_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByExternalLinkIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where externalLink in DEFAULT_EXTERNAL_LINK or UPDATED_EXTERNAL_LINK
        defaultViewPermissionShouldBeFound("externalLink.in=" + DEFAULT_EXTERNAL_LINK + "," + UPDATED_EXTERNAL_LINK);

        // Get all the viewPermissionList where externalLink equals to UPDATED_EXTERNAL_LINK
        defaultViewPermissionShouldNotBeFound("externalLink.in=" + UPDATED_EXTERNAL_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByExternalLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where externalLink is not null
        defaultViewPermissionShouldBeFound("externalLink.specified=true");

        // Get all the viewPermissionList where externalLink is null
        defaultViewPermissionShouldNotBeFound("externalLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByExternalLinkContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where externalLink contains DEFAULT_EXTERNAL_LINK
        defaultViewPermissionShouldBeFound("externalLink.contains=" + DEFAULT_EXTERNAL_LINK);

        // Get all the viewPermissionList where externalLink contains UPDATED_EXTERNAL_LINK
        defaultViewPermissionShouldNotBeFound("externalLink.contains=" + UPDATED_EXTERNAL_LINK);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByExternalLinkNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where externalLink does not contain DEFAULT_EXTERNAL_LINK
        defaultViewPermissionShouldNotBeFound("externalLink.doesNotContain=" + DEFAULT_EXTERNAL_LINK);

        // Get all the viewPermissionList where externalLink does not contain UPDATED_EXTERNAL_LINK
        defaultViewPermissionShouldBeFound("externalLink.doesNotContain=" + UPDATED_EXTERNAL_LINK);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByTargetIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where target equals to DEFAULT_TARGET
        defaultViewPermissionShouldBeFound("target.equals=" + DEFAULT_TARGET);

        // Get all the viewPermissionList where target equals to UPDATED_TARGET
        defaultViewPermissionShouldNotBeFound("target.equals=" + UPDATED_TARGET);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTargetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where target not equals to DEFAULT_TARGET
        defaultViewPermissionShouldNotBeFound("target.notEquals=" + DEFAULT_TARGET);

        // Get all the viewPermissionList where target not equals to UPDATED_TARGET
        defaultViewPermissionShouldBeFound("target.notEquals=" + UPDATED_TARGET);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTargetIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where target in DEFAULT_TARGET or UPDATED_TARGET
        defaultViewPermissionShouldBeFound("target.in=" + DEFAULT_TARGET + "," + UPDATED_TARGET);

        // Get all the viewPermissionList where target equals to UPDATED_TARGET
        defaultViewPermissionShouldNotBeFound("target.in=" + UPDATED_TARGET);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTargetIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where target is not null
        defaultViewPermissionShouldBeFound("target.specified=true");

        // Get all the viewPermissionList where target is null
        defaultViewPermissionShouldNotBeFound("target.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where icon equals to DEFAULT_ICON
        defaultViewPermissionShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the viewPermissionList where icon equals to UPDATED_ICON
        defaultViewPermissionShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where icon not equals to DEFAULT_ICON
        defaultViewPermissionShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the viewPermissionList where icon not equals to UPDATED_ICON
        defaultViewPermissionShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByIconIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultViewPermissionShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the viewPermissionList where icon equals to UPDATED_ICON
        defaultViewPermissionShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where icon is not null
        defaultViewPermissionShouldBeFound("icon.specified=true");

        // Get all the viewPermissionList where icon is null
        defaultViewPermissionShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByIconContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where icon contains DEFAULT_ICON
        defaultViewPermissionShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the viewPermissionList where icon contains UPDATED_ICON
        defaultViewPermissionShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByIconNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where icon does not contain DEFAULT_ICON
        defaultViewPermissionShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the viewPermissionList where icon does not contain UPDATED_ICON
        defaultViewPermissionShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByDisabledIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where disabled equals to DEFAULT_DISABLED
        defaultViewPermissionShouldBeFound("disabled.equals=" + DEFAULT_DISABLED);

        // Get all the viewPermissionList where disabled equals to UPDATED_DISABLED
        defaultViewPermissionShouldNotBeFound("disabled.equals=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByDisabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where disabled not equals to DEFAULT_DISABLED
        defaultViewPermissionShouldNotBeFound("disabled.notEquals=" + DEFAULT_DISABLED);

        // Get all the viewPermissionList where disabled not equals to UPDATED_DISABLED
        defaultViewPermissionShouldBeFound("disabled.notEquals=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByDisabledIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where disabled in DEFAULT_DISABLED or UPDATED_DISABLED
        defaultViewPermissionShouldBeFound("disabled.in=" + DEFAULT_DISABLED + "," + UPDATED_DISABLED);

        // Get all the viewPermissionList where disabled equals to UPDATED_DISABLED
        defaultViewPermissionShouldNotBeFound("disabled.in=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByDisabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where disabled is not null
        defaultViewPermissionShouldBeFound("disabled.specified=true");

        // Get all the viewPermissionList where disabled is null
        defaultViewPermissionShouldNotBeFound("disabled.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hide equals to DEFAULT_HIDE
        defaultViewPermissionShouldBeFound("hide.equals=" + DEFAULT_HIDE);

        // Get all the viewPermissionList where hide equals to UPDATED_HIDE
        defaultViewPermissionShouldNotBeFound("hide.equals=" + UPDATED_HIDE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hide not equals to DEFAULT_HIDE
        defaultViewPermissionShouldNotBeFound("hide.notEquals=" + DEFAULT_HIDE);

        // Get all the viewPermissionList where hide not equals to UPDATED_HIDE
        defaultViewPermissionShouldBeFound("hide.notEquals=" + UPDATED_HIDE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hide in DEFAULT_HIDE or UPDATED_HIDE
        defaultViewPermissionShouldBeFound("hide.in=" + DEFAULT_HIDE + "," + UPDATED_HIDE);

        // Get all the viewPermissionList where hide equals to UPDATED_HIDE
        defaultViewPermissionShouldNotBeFound("hide.in=" + UPDATED_HIDE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hide is not null
        defaultViewPermissionShouldBeFound("hide.specified=true");

        // Get all the viewPermissionList where hide is null
        defaultViewPermissionShouldNotBeFound("hide.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideInBreadcrumbIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hideInBreadcrumb equals to DEFAULT_HIDE_IN_BREADCRUMB
        defaultViewPermissionShouldBeFound("hideInBreadcrumb.equals=" + DEFAULT_HIDE_IN_BREADCRUMB);

        // Get all the viewPermissionList where hideInBreadcrumb equals to UPDATED_HIDE_IN_BREADCRUMB
        defaultViewPermissionShouldNotBeFound("hideInBreadcrumb.equals=" + UPDATED_HIDE_IN_BREADCRUMB);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideInBreadcrumbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hideInBreadcrumb not equals to DEFAULT_HIDE_IN_BREADCRUMB
        defaultViewPermissionShouldNotBeFound("hideInBreadcrumb.notEquals=" + DEFAULT_HIDE_IN_BREADCRUMB);

        // Get all the viewPermissionList where hideInBreadcrumb not equals to UPDATED_HIDE_IN_BREADCRUMB
        defaultViewPermissionShouldBeFound("hideInBreadcrumb.notEquals=" + UPDATED_HIDE_IN_BREADCRUMB);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideInBreadcrumbIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hideInBreadcrumb in DEFAULT_HIDE_IN_BREADCRUMB or UPDATED_HIDE_IN_BREADCRUMB
        defaultViewPermissionShouldBeFound("hideInBreadcrumb.in=" + DEFAULT_HIDE_IN_BREADCRUMB + "," + UPDATED_HIDE_IN_BREADCRUMB);

        // Get all the viewPermissionList where hideInBreadcrumb equals to UPDATED_HIDE_IN_BREADCRUMB
        defaultViewPermissionShouldNotBeFound("hideInBreadcrumb.in=" + UPDATED_HIDE_IN_BREADCRUMB);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByHideInBreadcrumbIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where hideInBreadcrumb is not null
        defaultViewPermissionShouldBeFound("hideInBreadcrumb.specified=true");

        // Get all the viewPermissionList where hideInBreadcrumb is null
        defaultViewPermissionShouldNotBeFound("hideInBreadcrumb.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcut equals to DEFAULT_SHORTCUT
        defaultViewPermissionShouldBeFound("shortcut.equals=" + DEFAULT_SHORTCUT);

        // Get all the viewPermissionList where shortcut equals to UPDATED_SHORTCUT
        defaultViewPermissionShouldNotBeFound("shortcut.equals=" + UPDATED_SHORTCUT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcut not equals to DEFAULT_SHORTCUT
        defaultViewPermissionShouldNotBeFound("shortcut.notEquals=" + DEFAULT_SHORTCUT);

        // Get all the viewPermissionList where shortcut not equals to UPDATED_SHORTCUT
        defaultViewPermissionShouldBeFound("shortcut.notEquals=" + UPDATED_SHORTCUT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcut in DEFAULT_SHORTCUT or UPDATED_SHORTCUT
        defaultViewPermissionShouldBeFound("shortcut.in=" + DEFAULT_SHORTCUT + "," + UPDATED_SHORTCUT);

        // Get all the viewPermissionList where shortcut equals to UPDATED_SHORTCUT
        defaultViewPermissionShouldNotBeFound("shortcut.in=" + UPDATED_SHORTCUT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcut is not null
        defaultViewPermissionShouldBeFound("shortcut.specified=true");

        // Get all the viewPermissionList where shortcut is null
        defaultViewPermissionShouldNotBeFound("shortcut.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutRootIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcutRoot equals to DEFAULT_SHORTCUT_ROOT
        defaultViewPermissionShouldBeFound("shortcutRoot.equals=" + DEFAULT_SHORTCUT_ROOT);

        // Get all the viewPermissionList where shortcutRoot equals to UPDATED_SHORTCUT_ROOT
        defaultViewPermissionShouldNotBeFound("shortcutRoot.equals=" + UPDATED_SHORTCUT_ROOT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutRootIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcutRoot not equals to DEFAULT_SHORTCUT_ROOT
        defaultViewPermissionShouldNotBeFound("shortcutRoot.notEquals=" + DEFAULT_SHORTCUT_ROOT);

        // Get all the viewPermissionList where shortcutRoot not equals to UPDATED_SHORTCUT_ROOT
        defaultViewPermissionShouldBeFound("shortcutRoot.notEquals=" + UPDATED_SHORTCUT_ROOT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutRootIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcutRoot in DEFAULT_SHORTCUT_ROOT or UPDATED_SHORTCUT_ROOT
        defaultViewPermissionShouldBeFound("shortcutRoot.in=" + DEFAULT_SHORTCUT_ROOT + "," + UPDATED_SHORTCUT_ROOT);

        // Get all the viewPermissionList where shortcutRoot equals to UPDATED_SHORTCUT_ROOT
        defaultViewPermissionShouldNotBeFound("shortcutRoot.in=" + UPDATED_SHORTCUT_ROOT);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByShortcutRootIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where shortcutRoot is not null
        defaultViewPermissionShouldBeFound("shortcutRoot.specified=true");

        // Get all the viewPermissionList where shortcutRoot is null
        defaultViewPermissionShouldNotBeFound("shortcutRoot.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByReuseIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where reuse equals to DEFAULT_REUSE
        defaultViewPermissionShouldBeFound("reuse.equals=" + DEFAULT_REUSE);

        // Get all the viewPermissionList where reuse equals to UPDATED_REUSE
        defaultViewPermissionShouldNotBeFound("reuse.equals=" + UPDATED_REUSE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByReuseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where reuse not equals to DEFAULT_REUSE
        defaultViewPermissionShouldNotBeFound("reuse.notEquals=" + DEFAULT_REUSE);

        // Get all the viewPermissionList where reuse not equals to UPDATED_REUSE
        defaultViewPermissionShouldBeFound("reuse.notEquals=" + UPDATED_REUSE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByReuseIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where reuse in DEFAULT_REUSE or UPDATED_REUSE
        defaultViewPermissionShouldBeFound("reuse.in=" + DEFAULT_REUSE + "," + UPDATED_REUSE);

        // Get all the viewPermissionList where reuse equals to UPDATED_REUSE
        defaultViewPermissionShouldNotBeFound("reuse.in=" + UPDATED_REUSE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByReuseIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where reuse is not null
        defaultViewPermissionShouldBeFound("reuse.specified=true");

        // Get all the viewPermissionList where reuse is null
        defaultViewPermissionShouldNotBeFound("reuse.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where code equals to DEFAULT_CODE
        defaultViewPermissionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the viewPermissionList where code equals to UPDATED_CODE
        defaultViewPermissionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where code not equals to DEFAULT_CODE
        defaultViewPermissionShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the viewPermissionList where code not equals to UPDATED_CODE
        defaultViewPermissionShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultViewPermissionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the viewPermissionList where code equals to UPDATED_CODE
        defaultViewPermissionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where code is not null
        defaultViewPermissionShouldBeFound("code.specified=true");

        // Get all the viewPermissionList where code is null
        defaultViewPermissionShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where code contains DEFAULT_CODE
        defaultViewPermissionShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the viewPermissionList where code contains UPDATED_CODE
        defaultViewPermissionShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where code does not contain DEFAULT_CODE
        defaultViewPermissionShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the viewPermissionList where code does not contain UPDATED_CODE
        defaultViewPermissionShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where description equals to DEFAULT_DESCRIPTION
        defaultViewPermissionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the viewPermissionList where description equals to UPDATED_DESCRIPTION
        defaultViewPermissionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where description not equals to DEFAULT_DESCRIPTION
        defaultViewPermissionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the viewPermissionList where description not equals to UPDATED_DESCRIPTION
        defaultViewPermissionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultViewPermissionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the viewPermissionList where description equals to UPDATED_DESCRIPTION
        defaultViewPermissionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where description is not null
        defaultViewPermissionShouldBeFound("description.specified=true");

        // Get all the viewPermissionList where description is null
        defaultViewPermissionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where description contains DEFAULT_DESCRIPTION
        defaultViewPermissionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the viewPermissionList where description contains UPDATED_DESCRIPTION
        defaultViewPermissionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where description does not contain DEFAULT_DESCRIPTION
        defaultViewPermissionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the viewPermissionList where description does not contain UPDATED_DESCRIPTION
        defaultViewPermissionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where type equals to DEFAULT_TYPE
        defaultViewPermissionShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the viewPermissionList where type equals to UPDATED_TYPE
        defaultViewPermissionShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where type not equals to DEFAULT_TYPE
        defaultViewPermissionShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the viewPermissionList where type not equals to UPDATED_TYPE
        defaultViewPermissionShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultViewPermissionShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the viewPermissionList where type equals to UPDATED_TYPE
        defaultViewPermissionShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where type is not null
        defaultViewPermissionShouldBeFound("type.specified=true");

        // Get all the viewPermissionList where type is null
        defaultViewPermissionShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order equals to DEFAULT_ORDER
        defaultViewPermissionShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the viewPermissionList where order equals to UPDATED_ORDER
        defaultViewPermissionShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order not equals to DEFAULT_ORDER
        defaultViewPermissionShouldNotBeFound("order.notEquals=" + DEFAULT_ORDER);

        // Get all the viewPermissionList where order not equals to UPDATED_ORDER
        defaultViewPermissionShouldBeFound("order.notEquals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultViewPermissionShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the viewPermissionList where order equals to UPDATED_ORDER
        defaultViewPermissionShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order is not null
        defaultViewPermissionShouldBeFound("order.specified=true");

        // Get all the viewPermissionList where order is null
        defaultViewPermissionShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order is greater than or equal to DEFAULT_ORDER
        defaultViewPermissionShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the viewPermissionList where order is greater than or equal to UPDATED_ORDER
        defaultViewPermissionShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order is less than or equal to DEFAULT_ORDER
        defaultViewPermissionShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the viewPermissionList where order is less than or equal to SMALLER_ORDER
        defaultViewPermissionShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order is less than DEFAULT_ORDER
        defaultViewPermissionShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the viewPermissionList where order is less than UPDATED_ORDER
        defaultViewPermissionShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where order is greater than DEFAULT_ORDER
        defaultViewPermissionShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the viewPermissionList where order is greater than SMALLER_ORDER
        defaultViewPermissionShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByApiPermissionCodesIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where apiPermissionCodes equals to DEFAULT_API_PERMISSION_CODES
        defaultViewPermissionShouldBeFound("apiPermissionCodes.equals=" + DEFAULT_API_PERMISSION_CODES);

        // Get all the viewPermissionList where apiPermissionCodes equals to UPDATED_API_PERMISSION_CODES
        defaultViewPermissionShouldNotBeFound("apiPermissionCodes.equals=" + UPDATED_API_PERMISSION_CODES);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByApiPermissionCodesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where apiPermissionCodes not equals to DEFAULT_API_PERMISSION_CODES
        defaultViewPermissionShouldNotBeFound("apiPermissionCodes.notEquals=" + DEFAULT_API_PERMISSION_CODES);

        // Get all the viewPermissionList where apiPermissionCodes not equals to UPDATED_API_PERMISSION_CODES
        defaultViewPermissionShouldBeFound("apiPermissionCodes.notEquals=" + UPDATED_API_PERMISSION_CODES);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByApiPermissionCodesIsInShouldWork() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where apiPermissionCodes in DEFAULT_API_PERMISSION_CODES or UPDATED_API_PERMISSION_CODES
        defaultViewPermissionShouldBeFound("apiPermissionCodes.in=" + DEFAULT_API_PERMISSION_CODES + "," + UPDATED_API_PERMISSION_CODES);

        // Get all the viewPermissionList where apiPermissionCodes equals to UPDATED_API_PERMISSION_CODES
        defaultViewPermissionShouldNotBeFound("apiPermissionCodes.in=" + UPDATED_API_PERMISSION_CODES);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByApiPermissionCodesIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where apiPermissionCodes is not null
        defaultViewPermissionShouldBeFound("apiPermissionCodes.specified=true");

        // Get all the viewPermissionList where apiPermissionCodes is null
        defaultViewPermissionShouldNotBeFound("apiPermissionCodes.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewPermissionsByApiPermissionCodesContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where apiPermissionCodes contains DEFAULT_API_PERMISSION_CODES
        defaultViewPermissionShouldBeFound("apiPermissionCodes.contains=" + DEFAULT_API_PERMISSION_CODES);

        // Get all the viewPermissionList where apiPermissionCodes contains UPDATED_API_PERMISSION_CODES
        defaultViewPermissionShouldNotBeFound("apiPermissionCodes.contains=" + UPDATED_API_PERMISSION_CODES);
    }

    @Test
    @Transactional
    public void getAllViewPermissionsByApiPermissionCodesNotContainsSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        // Get all the viewPermissionList where apiPermissionCodes does not contain DEFAULT_API_PERMISSION_CODES
        defaultViewPermissionShouldNotBeFound("apiPermissionCodes.doesNotContain=" + DEFAULT_API_PERMISSION_CODES);

        // Get all the viewPermissionList where apiPermissionCodes does not contain UPDATED_API_PERMISSION_CODES
        defaultViewPermissionShouldBeFound("apiPermissionCodes.doesNotContain=" + UPDATED_API_PERMISSION_CODES);
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByChildrenIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);
        ViewPermission children = ViewPermissionResourceIT.createEntity(em);
        em.persist(children);
        em.flush();
        viewPermission.addChildren(children);
        viewPermissionRepository.saveAndFlush(viewPermission);
        Long childrenId = children.getId();

        // Get all the viewPermissionList where children equals to childrenId
        defaultViewPermissionShouldBeFound("childrenId.equals=" + childrenId);

        // Get all the viewPermissionList where children equals to childrenId + 1
        defaultViewPermissionShouldNotBeFound("childrenId.equals=" + (childrenId + 1));
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByApiPermissionsIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);
        ApiPermission apiPermissions = ApiPermissionResourceIT.createEntity(em);
        em.persist(apiPermissions);
        em.flush();
        viewPermission.addApiPermissions(apiPermissions);
        viewPermissionRepository.saveAndFlush(viewPermission);
        Long apiPermissionsId = apiPermissions.getId();

        // Get all the viewPermissionList where apiPermissions equals to apiPermissionsId
        defaultViewPermissionShouldBeFound("apiPermissionsId.equals=" + apiPermissionsId);

        // Get all the viewPermissionList where apiPermissions equals to apiPermissionsId + 1
        defaultViewPermissionShouldNotBeFound("apiPermissionsId.equals=" + (apiPermissionsId + 1));
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);
        ViewPermission parent = ViewPermissionResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        viewPermission.setParent(parent);
        viewPermissionRepository.saveAndFlush(viewPermission);
        Long parentId = parent.getId();

        // Get all the viewPermissionList where parent equals to parentId
        defaultViewPermissionShouldBeFound("parentId.equals=" + parentId);

        // Get all the viewPermissionList where parent equals to parentId + 1
        defaultViewPermissionShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllViewPermissionsByAuthoritiesIsEqualToSomething() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);
        Authority authorities = AuthorityResourceIT.createEntity(em);
        em.persist(authorities);
        em.flush();
        viewPermission.addAuthorities(authorities);
        viewPermissionRepository.saveAndFlush(viewPermission);
        Long authoritiesId = authorities.getId();

        // Get all the viewPermissionList where authorities equals to authoritiesId
        defaultViewPermissionShouldBeFound("authoritiesId.equals=" + authoritiesId);

        // Get all the viewPermissionList where authorities equals to authoritiesId + 1
        defaultViewPermissionShouldNotBeFound("authoritiesId.equals=" + (authoritiesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultViewPermissionShouldBeFound(String filter) throws Exception {
        restViewPermissionMockMvc.perform(get("/api/view-permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewPermission.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].i18n").value(hasItem(DEFAULT_I_18_N)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].externalLink").value(hasItem(DEFAULT_EXTERNAL_LINK)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].hide").value(hasItem(DEFAULT_HIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].hideInBreadcrumb").value(hasItem(DEFAULT_HIDE_IN_BREADCRUMB.booleanValue())))
            .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.booleanValue())))
            .andExpect(jsonPath("$.[*].shortcutRoot").value(hasItem(DEFAULT_SHORTCUT_ROOT.booleanValue())))
            .andExpect(jsonPath("$.[*].reuse").value(hasItem(DEFAULT_REUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].apiPermissionCodes").value(hasItem(DEFAULT_API_PERMISSION_CODES)));

        // Check, that the count call also returns 1
        restViewPermissionMockMvc.perform(get("/api/view-permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultViewPermissionShouldNotBeFound(String filter) throws Exception {
        restViewPermissionMockMvc.perform(get("/api/view-permissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restViewPermissionMockMvc.perform(get("/api/view-permissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingViewPermission() throws Exception {
        // Get the viewPermission
        restViewPermissionMockMvc.perform(get("/api/view-permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateViewPermission() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        int databaseSizeBeforeUpdate = viewPermissionRepository.findAll().size();

        // Update the viewPermission
        ViewPermission updatedViewPermission = viewPermissionRepository.findById(viewPermission.getId()).get();
        // Disconnect from session so that the updates on updatedViewPermission are not directly saved in db
        em.detach(updatedViewPermission);
        updatedViewPermission
            .text(UPDATED_TEXT)
            .i18n(UPDATED_I_18_N)
            .group(UPDATED_GROUP)
            .link(UPDATED_LINK)
            .externalLink(UPDATED_EXTERNAL_LINK)
            .target(UPDATED_TARGET)
            .icon(UPDATED_ICON)
            .disabled(UPDATED_DISABLED)
            .hide(UPDATED_HIDE)
            .hideInBreadcrumb(UPDATED_HIDE_IN_BREADCRUMB)
            .shortcut(UPDATED_SHORTCUT)
            .shortcutRoot(UPDATED_SHORTCUT_ROOT)
            .reuse(UPDATED_REUSE)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .order(UPDATED_ORDER)
            .apiPermissionCodes(UPDATED_API_PERMISSION_CODES);
        ViewPermissionDTO viewPermissionDTO = viewPermissionMapper.toDto(updatedViewPermission);

        restViewPermissionMockMvc.perform(put("/api/view-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewPermissionDTO)))
            .andExpect(status().isOk());

        // Validate the ViewPermission in the database
        List<ViewPermission> viewPermissionList = viewPermissionRepository.findAll();
        assertThat(viewPermissionList).hasSize(databaseSizeBeforeUpdate);
        ViewPermission testViewPermission = viewPermissionList.get(viewPermissionList.size() - 1);
        assertThat(testViewPermission.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testViewPermission.geti18n()).isEqualTo(UPDATED_I_18_N);
        assertThat(testViewPermission.isGroup()).isEqualTo(UPDATED_GROUP);
        assertThat(testViewPermission.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testViewPermission.getExternalLink()).isEqualTo(UPDATED_EXTERNAL_LINK);
        assertThat(testViewPermission.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testViewPermission.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testViewPermission.isDisabled()).isEqualTo(UPDATED_DISABLED);
        assertThat(testViewPermission.isHide()).isEqualTo(UPDATED_HIDE);
        assertThat(testViewPermission.isHideInBreadcrumb()).isEqualTo(UPDATED_HIDE_IN_BREADCRUMB);
        assertThat(testViewPermission.isShortcut()).isEqualTo(UPDATED_SHORTCUT);
        assertThat(testViewPermission.isShortcutRoot()).isEqualTo(UPDATED_SHORTCUT_ROOT);
        assertThat(testViewPermission.isReuse()).isEqualTo(UPDATED_REUSE);
        assertThat(testViewPermission.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testViewPermission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testViewPermission.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testViewPermission.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testViewPermission.getApiPermissionCodes()).isEqualTo(UPDATED_API_PERMISSION_CODES);
    }

    @Test
    @Transactional
    public void updateNonExistingViewPermission() throws Exception {
        int databaseSizeBeforeUpdate = viewPermissionRepository.findAll().size();

        // Create the ViewPermission
        ViewPermissionDTO viewPermissionDTO = viewPermissionMapper.toDto(viewPermission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViewPermissionMockMvc.perform(put("/api/view-permissions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewPermissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ViewPermission in the database
        List<ViewPermission> viewPermissionList = viewPermissionRepository.findAll();
        assertThat(viewPermissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteViewPermission() throws Exception {
        // Initialize the database
        viewPermissionRepository.saveAndFlush(viewPermission);

        int databaseSizeBeforeDelete = viewPermissionRepository.findAll().size();

        // Delete the viewPermission
        restViewPermissionMockMvc.perform(delete("/api/view-permissions/{id}", viewPermission.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ViewPermission> viewPermissionList = viewPermissionRepository.findAll();
        assertThat(viewPermissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
