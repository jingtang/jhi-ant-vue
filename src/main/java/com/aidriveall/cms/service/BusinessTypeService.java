package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.BusinessType;
import com.aidriveall.cms.repository.BusinessTypeRepository;
import com.aidriveall.cms.service.dto.BusinessTypeDTO;
import com.aidriveall.cms.service.mapper.BusinessTypeMapper;
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
 * Service Implementation for managing {@link BusinessType}.
 */
@Service
@Transactional
public class BusinessTypeService {

    private final Logger log = LoggerFactory.getLogger(BusinessTypeService.class);

    private final BusinessTypeRepository businessTypeRepository;

    private final CacheManager cacheManager;

    private final BusinessTypeMapper businessTypeMapper;

    public BusinessTypeService(BusinessTypeRepository businessTypeRepository, CacheManager cacheManager, BusinessTypeMapper businessTypeMapper) {
        this.businessTypeRepository = businessTypeRepository;
        this.cacheManager = cacheManager;
        this.businessTypeMapper = businessTypeMapper;
    }

    /**
     * Save a businessType.
     *
     * @param businessTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessTypeDTO save(BusinessTypeDTO businessTypeDTO) {
        log.debug("Request to save BusinessType : {}", businessTypeDTO);
        BusinessType businessType = businessTypeMapper.toEntity(businessTypeDTO);
        businessType = businessTypeRepository.save(businessType);
        return businessTypeMapper.toDto(businessType);
    }

    /**
     * Get all the businessTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessTypes");
        return businessTypeRepository.findAll(pageable)
            .map(businessTypeMapper::toDto);
    }

    /**
    * count all the businessTypes.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all BusinessTypes");
        return businessTypeRepository.count();
    }

    /**
     * Get one businessType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessTypeDTO> findOne(Long id) {
        log.debug("Request to get BusinessType : {}", id);
        return businessTypeRepository.findById(id)
            .map(businessTypeMapper::toDto);
    }

    /**
     * Delete the businessType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessType : {}", id);
        businessTypeRepository.deleteById(id);
    }

    /**
     * Update specified fields by businessType
     */
    public BusinessTypeDTO updateBySpecifiedFields(BusinessTypeDTO changeBusinessTypeDTO, Set<String> unchangedFields) {
        BusinessTypeDTO businessTypeDTO = findOne(changeBusinessTypeDTO.getId()).get();
        BeanUtil.copyProperties(changeBusinessTypeDTO, businessTypeDTO, unchangedFields.toArray(new String[0]));
        businessTypeDTO = save(businessTypeDTO);
        return businessTypeDTO;
    }

    /**
     * Update specified field by businessType
     */
    public BusinessTypeDTO updateBySpecifiedField(BusinessTypeDTO changeBusinessTypeDTO, String fieldName) {
        BusinessTypeDTO businessTypeDTO = findOne(changeBusinessTypeDTO.getId()).get();
        BeanUtil.setFieldValue(businessTypeDTO, fieldName, BeanUtil.getFieldValue(changeBusinessTypeDTO,fieldName));
        businessTypeDTO = save(businessTypeDTO);
        return businessTypeDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
