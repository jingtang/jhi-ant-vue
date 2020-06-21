package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonFloat;
import com.aidriveall.cms.repository.CommonFloatRepository;
import com.aidriveall.cms.service.dto.CommonFloatDTO;
import com.aidriveall.cms.service.mapper.CommonFloatMapper;
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
 * Service Implementation for managing {@link CommonFloat}.
 */
@Service
@Transactional
public class CommonFloatService {

    private final Logger log = LoggerFactory.getLogger(CommonFloatService.class);

    private final CommonFloatRepository commonFloatRepository;

    private final CacheManager cacheManager;

    private final CommonFloatMapper commonFloatMapper;

    public CommonFloatService(CommonFloatRepository commonFloatRepository, CacheManager cacheManager, CommonFloatMapper commonFloatMapper) {
        this.commonFloatRepository = commonFloatRepository;
        this.cacheManager = cacheManager;
        this.commonFloatMapper = commonFloatMapper;
    }

    /**
     * Save a commonFloat.
     *
     * @param commonFloatDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonFloatDTO save(CommonFloatDTO commonFloatDTO) {
        log.debug("Request to save CommonFloat : {}", commonFloatDTO);
        CommonFloat commonFloat = commonFloatMapper.toEntity(commonFloatDTO);
        commonFloat = commonFloatRepository.save(commonFloat);
        return commonFloatMapper.toDto(commonFloat);
    }

    /**
     * Get all the commonFloats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonFloatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonFloats");
        return commonFloatRepository.findAll(pageable)
            .map(commonFloatMapper::toDto);
    }

    /**
    * count all the commonFloats.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonFloats");
        return commonFloatRepository.count();
    }

    /**
     * Get one commonFloat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonFloatDTO> findOne(Long id) {
        log.debug("Request to get CommonFloat : {}", id);
        return commonFloatRepository.findById(id)
            .map(commonFloatMapper::toDto);
    }

    /**
     * Delete the commonFloat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonFloat : {}", id);
        commonFloatRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonFloat
     */
    public CommonFloatDTO updateBySpecifiedFields(CommonFloatDTO changeCommonFloatDTO, Set<String> unchangedFields) {
        CommonFloatDTO commonFloatDTO = findOne(changeCommonFloatDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonFloatDTO, commonFloatDTO, unchangedFields.toArray(new String[0]));
        commonFloatDTO = save(commonFloatDTO);
        return commonFloatDTO;
    }

    /**
     * Update specified field by commonFloat
     */
    public CommonFloatDTO updateBySpecifiedField(CommonFloatDTO changeCommonFloatDTO, String fieldName) {
        CommonFloatDTO commonFloatDTO = findOne(changeCommonFloatDTO.getId()).get();
        BeanUtil.setFieldValue(commonFloatDTO, fieldName, BeanUtil.getFieldValue(changeCommonFloatDTO,fieldName));
        commonFloatDTO = save(commonFloatDTO);
        return commonFloatDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
