package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.GpsInfo;
import com.aidriveall.cms.repository.GpsInfoRepository;
import com.aidriveall.cms.service.dto.GpsInfoDTO;
import com.aidriveall.cms.service.mapper.GpsInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.beans.PropertyDescriptor;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link GpsInfo}.
 */
@Service
@Transactional
public class GpsInfoService {

    private final Logger log = LoggerFactory.getLogger(GpsInfoService.class);

    private final GpsInfoRepository gpsInfoRepository;

    private final CacheManager cacheManager;

    private final GpsInfoMapper gpsInfoMapper;

    public GpsInfoService(GpsInfoRepository gpsInfoRepository, CacheManager cacheManager, GpsInfoMapper gpsInfoMapper) {
        this.gpsInfoRepository = gpsInfoRepository;
        this.cacheManager = cacheManager;
        this.gpsInfoMapper = gpsInfoMapper;
    }

    /**
     * Save a gpsInfo.
     *
     * @param gpsInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public GpsInfoDTO save(GpsInfoDTO gpsInfoDTO) {
        log.debug("Request to save GpsInfo : {}", gpsInfoDTO);
        GpsInfo gpsInfo = gpsInfoMapper.toEntity(gpsInfoDTO);
        gpsInfo = gpsInfoRepository.save(gpsInfo);
        return gpsInfoMapper.toDto(gpsInfo);
    }

    /**
     * Get all the gpsInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GpsInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GpsInfos");
        return gpsInfoRepository.findAll(pageable)
            .map(gpsInfoMapper::toDto);
    }

    /**
    * count all the gpsInfos.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all GpsInfos");
        return gpsInfoRepository.count();
    }

    /**
     * Get one gpsInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GpsInfoDTO> findOne(Long id) {
        log.debug("Request to get GpsInfo : {}", id);
        return gpsInfoRepository.findById(id)
            .map(gpsInfoMapper::toDto);
    }

    /**
     * Delete the gpsInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GpsInfo : {}", id);
        gpsInfoRepository.deleteById(id);
    }

    /**
     * Update specified fields by gpsInfo
     */
    public GpsInfoDTO updateBySpecifiedFields(GpsInfoDTO changeGpsInfoDTO, Set<String> unchangedFields) {
        GpsInfoDTO gpsInfoDTO = findOne(changeGpsInfoDTO.getId()).get();
        BeanUtil.copyProperties(changeGpsInfoDTO, gpsInfoDTO, unchangedFields.toArray(new String[0]));
        gpsInfoDTO = save(gpsInfoDTO);
        return gpsInfoDTO;
    }

    /**
     * Update specified field by gpsInfo
     */
    public GpsInfoDTO updateBySpecifiedField(GpsInfoDTO changeGpsInfoDTO, String fieldName) {
        GpsInfoDTO gpsInfoDTO = findOne(changeGpsInfoDTO.getId()).get();
        BeanUtil.setFieldValue(gpsInfoDTO, fieldName, BeanUtil.getFieldValue(changeGpsInfoDTO,fieldName));
        gpsInfoDTO = save(gpsInfoDTO);
        return gpsInfoDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
