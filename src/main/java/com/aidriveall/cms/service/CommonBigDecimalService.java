package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonBigDecimal;
import com.aidriveall.cms.repository.CommonBigDecimalRepository;
import com.aidriveall.cms.service.dto.CommonBigDecimalDTO;
import com.aidriveall.cms.service.mapper.CommonBigDecimalMapper;
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
 * Service Implementation for managing {@link CommonBigDecimal}.
 */
@Service
@Transactional
public class CommonBigDecimalService {

    private final Logger log = LoggerFactory.getLogger(CommonBigDecimalService.class);

    private final CommonBigDecimalRepository commonBigDecimalRepository;

    private final CacheManager cacheManager;

    private final CommonBigDecimalMapper commonBigDecimalMapper;

    public CommonBigDecimalService(CommonBigDecimalRepository commonBigDecimalRepository, CacheManager cacheManager, CommonBigDecimalMapper commonBigDecimalMapper) {
        this.commonBigDecimalRepository = commonBigDecimalRepository;
        this.cacheManager = cacheManager;
        this.commonBigDecimalMapper = commonBigDecimalMapper;
    }

    /**
     * Save a commonBigDecimal.
     *
     * @param commonBigDecimalDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonBigDecimalDTO save(CommonBigDecimalDTO commonBigDecimalDTO) {
        log.debug("Request to save CommonBigDecimal : {}", commonBigDecimalDTO);
        CommonBigDecimal commonBigDecimal = commonBigDecimalMapper.toEntity(commonBigDecimalDTO);
        commonBigDecimal = commonBigDecimalRepository.save(commonBigDecimal);
        return commonBigDecimalMapper.toDto(commonBigDecimal);
    }

    /**
     * Get all the commonBigDecimals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonBigDecimalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonBigDecimals");
        return commonBigDecimalRepository.findAll(pageable)
            .map(commonBigDecimalMapper::toDto);
    }

    /**
    * count all the commonBigDecimals.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonBigDecimals");
        return commonBigDecimalRepository.count();
    }

    /**
     * Get one commonBigDecimal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonBigDecimalDTO> findOne(Long id) {
        log.debug("Request to get CommonBigDecimal : {}", id);
        return commonBigDecimalRepository.findById(id)
            .map(commonBigDecimalMapper::toDto);
    }

    /**
     * Delete the commonBigDecimal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonBigDecimal : {}", id);
        commonBigDecimalRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonBigDecimal
     */
    public CommonBigDecimalDTO updateBySpecifiedFields(CommonBigDecimalDTO changeCommonBigDecimalDTO, Set<String> unchangedFields) {
        CommonBigDecimalDTO commonBigDecimalDTO = findOne(changeCommonBigDecimalDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonBigDecimalDTO, commonBigDecimalDTO, unchangedFields.toArray(new String[0]));
        commonBigDecimalDTO = save(commonBigDecimalDTO);
        return commonBigDecimalDTO;
    }

    /**
     * Update specified field by commonBigDecimal
     */
    public CommonBigDecimalDTO updateBySpecifiedField(CommonBigDecimalDTO changeCommonBigDecimalDTO, String fieldName) {
        CommonBigDecimalDTO commonBigDecimalDTO = findOne(changeCommonBigDecimalDTO.getId()).get();
        BeanUtil.setFieldValue(commonBigDecimalDTO, fieldName, BeanUtil.getFieldValue(changeCommonBigDecimalDTO,fieldName));
        commonBigDecimalDTO = save(commonBigDecimalDTO);
        return commonBigDecimalDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
