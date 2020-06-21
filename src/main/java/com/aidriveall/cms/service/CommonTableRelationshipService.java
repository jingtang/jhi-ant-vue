package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonTableRelationship;
import com.aidriveall.cms.repository.CommonTableRelationshipRepository;
import com.aidriveall.cms.service.dto.CommonTableRelationshipDTO;
import com.aidriveall.cms.service.mapper.CommonTableRelationshipMapper;
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
 * Service Implementation for managing {@link CommonTableRelationship}.
 */
@Service
@Transactional
public class CommonTableRelationshipService {

    private final Logger log = LoggerFactory.getLogger(CommonTableRelationshipService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.CommonTable.class.getName() + ".commonTableRelationship",
        com.aidriveall.cms.domain.DataDictionary.class.getName() + ".commonTableRelationship",
        com.aidriveall.cms.domain.CommonTable.class.getName() + ".relationships"
        );

    private final CommonTableRelationshipRepository commonTableRelationshipRepository;

    private final CacheManager cacheManager;

    private final CommonTableRelationshipMapper commonTableRelationshipMapper;

    public CommonTableRelationshipService(CommonTableRelationshipRepository commonTableRelationshipRepository, CacheManager cacheManager, CommonTableRelationshipMapper commonTableRelationshipMapper) {
        this.commonTableRelationshipRepository = commonTableRelationshipRepository;
        this.cacheManager = cacheManager;
        this.commonTableRelationshipMapper = commonTableRelationshipMapper;
    }

    /**
     * Save a commonTableRelationship.
     *
     * @param commonTableRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonTableRelationshipDTO save(CommonTableRelationshipDTO commonTableRelationshipDTO) {
        log.debug("Request to save CommonTableRelationship : {}", commonTableRelationshipDTO);
        CommonTableRelationship commonTableRelationship = commonTableRelationshipMapper.toEntity(commonTableRelationshipDTO);
        commonTableRelationship = commonTableRelationshipRepository.save(commonTableRelationship);
        return commonTableRelationshipMapper.toDto(commonTableRelationship);
    }

    /**
     * Get all the commonTableRelationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonTableRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonTableRelationships");
        return commonTableRelationshipRepository.findAll(pageable)
            .map(commonTableRelationshipMapper::toDto);
    }

    /**
    * count all the commonTableRelationships.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonTableRelationships");
        return commonTableRelationshipRepository.count();
    }

    /**
     * Get one commonTableRelationship by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonTableRelationshipDTO> findOne(Long id) {
        log.debug("Request to get CommonTableRelationship : {}", id);
        return commonTableRelationshipRepository.findById(id)
            .map(commonTableRelationshipMapper::toDto);
    }

    /**
     * Delete the commonTableRelationship by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonTableRelationship : {}", id);
        commonTableRelationshipRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonTableRelationship
     */
    public CommonTableRelationshipDTO updateBySpecifiedFields(CommonTableRelationshipDTO changeCommonTableRelationshipDTO, Set<String> unchangedFields) {
        CommonTableRelationshipDTO commonTableRelationshipDTO = findOne(changeCommonTableRelationshipDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonTableRelationshipDTO, commonTableRelationshipDTO, unchangedFields.toArray(new String[0]));
        commonTableRelationshipDTO = save(commonTableRelationshipDTO);
        return commonTableRelationshipDTO;
    }

    /**
     * Update specified field by commonTableRelationship
     */
    public CommonTableRelationshipDTO updateBySpecifiedField(CommonTableRelationshipDTO changeCommonTableRelationshipDTO, String fieldName) {
        CommonTableRelationshipDTO commonTableRelationshipDTO = findOne(changeCommonTableRelationshipDTO.getId()).get();
        BeanUtil.setFieldValue(commonTableRelationshipDTO, fieldName, BeanUtil.getFieldValue(changeCommonTableRelationshipDTO,fieldName));
        commonTableRelationshipDTO = save(commonTableRelationshipDTO);
        return commonTableRelationshipDTO;
    }
    private void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> {
            if (cacheManager.getCache(cacheName) != null) {
                cacheManager.getCache(cacheName).clear();
            }
        });
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
