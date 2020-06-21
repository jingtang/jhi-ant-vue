package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonBoolean;
import com.aidriveall.cms.repository.CommonBooleanRepository;
import com.aidriveall.cms.service.dto.CommonBooleanDTO;
import com.aidriveall.cms.service.mapper.CommonBooleanMapper;
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
 * Service Implementation for managing {@link CommonBoolean}.
 */
@Service
@Transactional
public class CommonBooleanService {

    private final Logger log = LoggerFactory.getLogger(CommonBooleanService.class);

    private final CommonBooleanRepository commonBooleanRepository;

    private final CacheManager cacheManager;

    private final CommonBooleanMapper commonBooleanMapper;

    public CommonBooleanService(CommonBooleanRepository commonBooleanRepository, CacheManager cacheManager, CommonBooleanMapper commonBooleanMapper) {
        this.commonBooleanRepository = commonBooleanRepository;
        this.cacheManager = cacheManager;
        this.commonBooleanMapper = commonBooleanMapper;
    }

    /**
     * Save a commonBoolean.
     *
     * @param commonBooleanDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonBooleanDTO save(CommonBooleanDTO commonBooleanDTO) {
        log.debug("Request to save CommonBoolean : {}", commonBooleanDTO);
        CommonBoolean commonBoolean = commonBooleanMapper.toEntity(commonBooleanDTO);
        commonBoolean = commonBooleanRepository.save(commonBoolean);
        return commonBooleanMapper.toDto(commonBoolean);
    }

    /**
     * Get all the commonBooleans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonBooleanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonBooleans");
        return commonBooleanRepository.findAll(pageable)
            .map(commonBooleanMapper::toDto);
    }

    /**
    * count all the commonBooleans.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonBooleans");
        return commonBooleanRepository.count();
    }

    /**
     * Get one commonBoolean by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonBooleanDTO> findOne(Long id) {
        log.debug("Request to get CommonBoolean : {}", id);
        return commonBooleanRepository.findById(id)
            .map(commonBooleanMapper::toDto);
    }

    /**
     * Delete the commonBoolean by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonBoolean : {}", id);
        commonBooleanRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonBoolean
     */
    public CommonBooleanDTO updateBySpecifiedFields(CommonBooleanDTO changeCommonBooleanDTO, Set<String> unchangedFields) {
        CommonBooleanDTO commonBooleanDTO = findOne(changeCommonBooleanDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonBooleanDTO, commonBooleanDTO, unchangedFields.toArray(new String[0]));
        commonBooleanDTO = save(commonBooleanDTO);
        return commonBooleanDTO;
    }

    /**
     * Update specified field by commonBoolean
     */
    public CommonBooleanDTO updateBySpecifiedField(CommonBooleanDTO changeCommonBooleanDTO, String fieldName) {
        CommonBooleanDTO commonBooleanDTO = findOne(changeCommonBooleanDTO.getId()).get();
        BeanUtil.setFieldValue(commonBooleanDTO, fieldName, BeanUtil.getFieldValue(changeCommonBooleanDTO,fieldName));
        commonBooleanDTO = save(commonBooleanDTO);
        return commonBooleanDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
