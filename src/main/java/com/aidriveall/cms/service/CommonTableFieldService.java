package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonTableField;
import com.aidriveall.cms.repository.CommonTableFieldRepository;
import com.aidriveall.cms.service.dto.CommonTableFieldDTO;
import com.aidriveall.cms.service.mapper.CommonTableFieldMapper;
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
 * Service Implementation for managing {@link CommonTableField}.
 */
@Service
@Transactional
public class CommonTableFieldService {

    private final Logger log = LoggerFactory.getLogger(CommonTableFieldService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.CommonTable.class.getName() + ".commonTableFields"
        );

    private final CommonTableFieldRepository commonTableFieldRepository;

    private final CacheManager cacheManager;

    private final CommonTableFieldMapper commonTableFieldMapper;

    public CommonTableFieldService(CommonTableFieldRepository commonTableFieldRepository, CacheManager cacheManager, CommonTableFieldMapper commonTableFieldMapper) {
        this.commonTableFieldRepository = commonTableFieldRepository;
        this.cacheManager = cacheManager;
        this.commonTableFieldMapper = commonTableFieldMapper;
    }

    /**
     * Save a commonTableField.
     *
     * @param commonTableFieldDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonTableFieldDTO save(CommonTableFieldDTO commonTableFieldDTO) {
        log.debug("Request to save CommonTableField : {}", commonTableFieldDTO);
        CommonTableField commonTableField = commonTableFieldMapper.toEntity(commonTableFieldDTO);
        commonTableField = commonTableFieldRepository.save(commonTableField);
        return commonTableFieldMapper.toDto(commonTableField);
    }

    /**
     * Get all the commonTableFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonTableFieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonTableFields");
        return commonTableFieldRepository.findAll(pageable)
            .map(commonTableFieldMapper::toDto);
    }

    /**
    * count all the commonTableFields.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonTableFields");
        return commonTableFieldRepository.count();
    }

    /**
     * Get one commonTableField by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonTableFieldDTO> findOne(Long id) {
        log.debug("Request to get CommonTableField : {}", id);
        return commonTableFieldRepository.findById(id)
            .map(commonTableFieldMapper::toDto);
    }

    /**
     * Delete the commonTableField by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonTableField : {}", id);
        commonTableFieldRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonTableField
     */
    public CommonTableFieldDTO updateBySpecifiedFields(CommonTableFieldDTO changeCommonTableFieldDTO, Set<String> unchangedFields) {
        CommonTableFieldDTO commonTableFieldDTO = findOne(changeCommonTableFieldDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonTableFieldDTO, commonTableFieldDTO, unchangedFields.toArray(new String[0]));
        commonTableFieldDTO = save(commonTableFieldDTO);
        return commonTableFieldDTO;
    }

    /**
     * Update specified field by commonTableField
     */
    public CommonTableFieldDTO updateBySpecifiedField(CommonTableFieldDTO changeCommonTableFieldDTO, String fieldName) {
        CommonTableFieldDTO commonTableFieldDTO = findOne(changeCommonTableFieldDTO.getId()).get();
        BeanUtil.setFieldValue(commonTableFieldDTO, fieldName, BeanUtil.getFieldValue(changeCommonTableFieldDTO,fieldName));
        commonTableFieldDTO = save(commonTableFieldDTO);
        return commonTableFieldDTO;
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
