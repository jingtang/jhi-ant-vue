package com.aidriveall.cms.web.rest;

import com.aidriveall.cms.JhiAntVueApp;
import com.aidriveall.cms.domain.GpsInfo;
import com.aidriveall.cms.repository.GpsInfoRepository;
import com.aidriveall.cms.service.GpsInfoService;
import com.aidriveall.cms.service.dto.GpsInfoDTO;
import com.aidriveall.cms.service.mapper.GpsInfoMapper;
import com.aidriveall.cms.web.rest.errors.ExceptionTranslator;
import com.aidriveall.cms.service.dto.GpsInfoCriteria;
import com.aidriveall.cms.service.GpsInfoQueryService;

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

import com.aidriveall.cms.domain.enumeration.GpsType;
/**
 * Integration tests for the {@link GpsInfoResource} REST controller.
 */
@SpringBootTest(classes = JhiAntVueApp.class)
public class GpsInfoResourceIT {

    private static final GpsType DEFAULT_TYPE = GpsType.BAIDU;
    private static final GpsType UPDATED_TYPE = GpsType.GOOGLE_MAP;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private GpsInfoRepository gpsInfoRepository;

    @Autowired
    private GpsInfoMapper gpsInfoMapper;

    @Autowired
    private GpsInfoService gpsInfoService;

    @Autowired
    private GpsInfoQueryService gpsInfoQueryService;

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

    private MockMvc restGpsInfoMockMvc;

    private GpsInfo gpsInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GpsInfoResource gpsInfoResource = new GpsInfoResource(gpsInfoService, gpsInfoQueryService);
        this.restGpsInfoMockMvc = MockMvcBuilders.standaloneSetup(gpsInfoResource)
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
    public static GpsInfo createEntity(EntityManager em) {
        GpsInfo gpsInfo = new GpsInfo()
            .type(DEFAULT_TYPE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .address(DEFAULT_ADDRESS);
        return gpsInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GpsInfo createUpdatedEntity(EntityManager em) {
        GpsInfo gpsInfo = new GpsInfo()
            .type(UPDATED_TYPE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS);
        return gpsInfo;
    }

    @BeforeEach
    public void initTest() {
        gpsInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createGpsInfo() throws Exception {
        int databaseSizeBeforeCreate = gpsInfoRepository.findAll().size();

        // Create the GpsInfo
        GpsInfoDTO gpsInfoDTO = gpsInfoMapper.toDto(gpsInfo);
        restGpsInfoMockMvc.perform(post("/api/gps-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gpsInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the GpsInfo in the database
        List<GpsInfo> gpsInfoList = gpsInfoRepository.findAll();
        assertThat(gpsInfoList).hasSize(databaseSizeBeforeCreate + 1);
        GpsInfo testGpsInfo = gpsInfoList.get(gpsInfoList.size() - 1);
        assertThat(testGpsInfo.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGpsInfo.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGpsInfo.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testGpsInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createGpsInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gpsInfoRepository.findAll().size();

        // Create the GpsInfo with an existing ID
        gpsInfo.setId(1L);
        GpsInfoDTO gpsInfoDTO = gpsInfoMapper.toDto(gpsInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGpsInfoMockMvc.perform(post("/api/gps-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gpsInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GpsInfo in the database
        List<GpsInfo> gpsInfoList = gpsInfoRepository.findAll();
        assertThat(gpsInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGpsInfos() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList
        restGpsInfoMockMvc.perform(get("/api/gps-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gpsInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getGpsInfo() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get the gpsInfo
        restGpsInfoMockMvc.perform(get("/api/gps-infos/{id}", gpsInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gpsInfo.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }


    @Test
    @Transactional
    public void getGpsInfosByIdFiltering() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        Long id = gpsInfo.getId();

        defaultGpsInfoShouldBeFound("id.equals=" + id);
        defaultGpsInfoShouldNotBeFound("id.notEquals=" + id);

        defaultGpsInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGpsInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultGpsInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGpsInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGpsInfosByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where type equals to DEFAULT_TYPE
        defaultGpsInfoShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the gpsInfoList where type equals to UPDATED_TYPE
        defaultGpsInfoShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where type not equals to DEFAULT_TYPE
        defaultGpsInfoShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the gpsInfoList where type not equals to UPDATED_TYPE
        defaultGpsInfoShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultGpsInfoShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the gpsInfoList where type equals to UPDATED_TYPE
        defaultGpsInfoShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where type is not null
        defaultGpsInfoShouldBeFound("type.specified=true");

        // Get all the gpsInfoList where type is null
        defaultGpsInfoShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude equals to DEFAULT_LATITUDE
        defaultGpsInfoShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the gpsInfoList where latitude equals to UPDATED_LATITUDE
        defaultGpsInfoShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude not equals to DEFAULT_LATITUDE
        defaultGpsInfoShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the gpsInfoList where latitude not equals to UPDATED_LATITUDE
        defaultGpsInfoShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultGpsInfoShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the gpsInfoList where latitude equals to UPDATED_LATITUDE
        defaultGpsInfoShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude is not null
        defaultGpsInfoShouldBeFound("latitude.specified=true");

        // Get all the gpsInfoList where latitude is null
        defaultGpsInfoShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultGpsInfoShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the gpsInfoList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultGpsInfoShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultGpsInfoShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the gpsInfoList where latitude is less than or equal to SMALLER_LATITUDE
        defaultGpsInfoShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude is less than DEFAULT_LATITUDE
        defaultGpsInfoShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the gpsInfoList where latitude is less than UPDATED_LATITUDE
        defaultGpsInfoShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where latitude is greater than DEFAULT_LATITUDE
        defaultGpsInfoShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the gpsInfoList where latitude is greater than SMALLER_LATITUDE
        defaultGpsInfoShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude equals to DEFAULT_LONGITUDE
        defaultGpsInfoShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the gpsInfoList where longitude equals to UPDATED_LONGITUDE
        defaultGpsInfoShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude not equals to DEFAULT_LONGITUDE
        defaultGpsInfoShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the gpsInfoList where longitude not equals to UPDATED_LONGITUDE
        defaultGpsInfoShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultGpsInfoShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the gpsInfoList where longitude equals to UPDATED_LONGITUDE
        defaultGpsInfoShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude is not null
        defaultGpsInfoShouldBeFound("longitude.specified=true");

        // Get all the gpsInfoList where longitude is null
        defaultGpsInfoShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultGpsInfoShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the gpsInfoList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultGpsInfoShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultGpsInfoShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the gpsInfoList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultGpsInfoShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude is less than DEFAULT_LONGITUDE
        defaultGpsInfoShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the gpsInfoList where longitude is less than UPDATED_LONGITUDE
        defaultGpsInfoShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where longitude is greater than DEFAULT_LONGITUDE
        defaultGpsInfoShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the gpsInfoList where longitude is greater than SMALLER_LONGITUDE
        defaultGpsInfoShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllGpsInfosByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where address equals to DEFAULT_ADDRESS
        defaultGpsInfoShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the gpsInfoList where address equals to UPDATED_ADDRESS
        defaultGpsInfoShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where address not equals to DEFAULT_ADDRESS
        defaultGpsInfoShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the gpsInfoList where address not equals to UPDATED_ADDRESS
        defaultGpsInfoShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultGpsInfoShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the gpsInfoList where address equals to UPDATED_ADDRESS
        defaultGpsInfoShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where address is not null
        defaultGpsInfoShouldBeFound("address.specified=true");

        // Get all the gpsInfoList where address is null
        defaultGpsInfoShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllGpsInfosByAddressContainsSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where address contains DEFAULT_ADDRESS
        defaultGpsInfoShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the gpsInfoList where address contains UPDATED_ADDRESS
        defaultGpsInfoShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllGpsInfosByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        // Get all the gpsInfoList where address does not contain DEFAULT_ADDRESS
        defaultGpsInfoShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the gpsInfoList where address does not contain UPDATED_ADDRESS
        defaultGpsInfoShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGpsInfoShouldBeFound(String filter) throws Exception {
        restGpsInfoMockMvc.perform(get("/api/gps-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gpsInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));

        // Check, that the count call also returns 1
        restGpsInfoMockMvc.perform(get("/api/gps-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGpsInfoShouldNotBeFound(String filter) throws Exception {
        restGpsInfoMockMvc.perform(get("/api/gps-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGpsInfoMockMvc.perform(get("/api/gps-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGpsInfo() throws Exception {
        // Get the gpsInfo
        restGpsInfoMockMvc.perform(get("/api/gps-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGpsInfo() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        int databaseSizeBeforeUpdate = gpsInfoRepository.findAll().size();

        // Update the gpsInfo
        GpsInfo updatedGpsInfo = gpsInfoRepository.findById(gpsInfo.getId()).get();
        // Disconnect from session so that the updates on updatedGpsInfo are not directly saved in db
        em.detach(updatedGpsInfo);
        updatedGpsInfo
            .type(UPDATED_TYPE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .address(UPDATED_ADDRESS);
        GpsInfoDTO gpsInfoDTO = gpsInfoMapper.toDto(updatedGpsInfo);

        restGpsInfoMockMvc.perform(put("/api/gps-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gpsInfoDTO)))
            .andExpect(status().isOk());

        // Validate the GpsInfo in the database
        List<GpsInfo> gpsInfoList = gpsInfoRepository.findAll();
        assertThat(gpsInfoList).hasSize(databaseSizeBeforeUpdate);
        GpsInfo testGpsInfo = gpsInfoList.get(gpsInfoList.size() - 1);
        assertThat(testGpsInfo.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGpsInfo.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGpsInfo.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testGpsInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingGpsInfo() throws Exception {
        int databaseSizeBeforeUpdate = gpsInfoRepository.findAll().size();

        // Create the GpsInfo
        GpsInfoDTO gpsInfoDTO = gpsInfoMapper.toDto(gpsInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGpsInfoMockMvc.perform(put("/api/gps-infos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gpsInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GpsInfo in the database
        List<GpsInfo> gpsInfoList = gpsInfoRepository.findAll();
        assertThat(gpsInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGpsInfo() throws Exception {
        // Initialize the database
        gpsInfoRepository.saveAndFlush(gpsInfo);

        int databaseSizeBeforeDelete = gpsInfoRepository.findAll().size();

        // Delete the gpsInfo
        restGpsInfoMockMvc.perform(delete("/api/gps-infos/{id}", gpsInfo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GpsInfo> gpsInfoList = gpsInfoRepository.findAll();
        assertThat(gpsInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
