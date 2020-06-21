package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonZonedDateTime;
import com.aidriveall.cms.repository.CommonZonedDateTimeRepository;
import com.aidriveall.cms.service.dto.CommonZonedDateTimeDTO;
import com.aidriveall.cms.service.mapper.CommonZonedDateTimeMapper;
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
 * Service Implementation for managing {@link CommonZonedDateTime}.
 */
@Service
@Transactional
public class CommonZonedDateTimeService {

    private final Logger log = LoggerFactory.getLogger(CommonZonedDateTimeService.class);

    private final CommonZonedDateTimeRepository commonZonedDateTimeRepository;

    private final CacheManager cacheManager;

    private final CommonZonedDateTimeMapper commonZonedDateTimeMapper;

    public CommonZonedDateTimeService(CommonZonedDateTimeRepository commonZonedDateTimeRepository, CacheManager cacheManager, CommonZonedDateTimeMapper commonZonedDateTimeMapper) {
        this.commonZonedDateTimeRepository = commonZonedDateTimeRepository;
        this.cacheManager = cacheManager;
        this.commonZonedDateTimeMapper = commonZonedDateTimeMapper;
    }

    /**
     * Save a commonZonedDateTime.
     *
     * @param commonZonedDateTimeDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonZonedDateTimeDTO save(CommonZonedDateTimeDTO commonZonedDateTimeDTO) {
        log.debug("Request to save CommonZonedDateTime : {}", commonZonedDateTimeDTO);
        CommonZonedDateTime commonZonedDateTime = commonZonedDateTimeMapper.toEntity(commonZonedDateTimeDTO);
        commonZonedDateTime = commonZonedDateTimeRepository.save(commonZonedDateTime);
        return commonZonedDateTimeMapper.toDto(commonZonedDateTime);
    }

    /**
     * Get all the commonZonedDateTimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonZonedDateTimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonZonedDateTimes");
        return commonZonedDateTimeRepository.findAll(pageable)
            .map(commonZonedDateTimeMapper::toDto);
    }

    /**
    * count all the commonZonedDateTimes.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonZonedDateTimes");
        return commonZonedDateTimeRepository.count();
    }

    /**
     * Get one commonZonedDateTime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonZonedDateTimeDTO> findOne(Long id) {
        log.debug("Request to get CommonZonedDateTime : {}", id);
        return commonZonedDateTimeRepository.findById(id)
            .map(commonZonedDateTimeMapper::toDto);
    }

    /**
     * Delete the commonZonedDateTime by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonZonedDateTime : {}", id);
        commonZonedDateTimeRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonZonedDateTime
     */
    public CommonZonedDateTimeDTO updateBySpecifiedFields(CommonZonedDateTimeDTO changeCommonZonedDateTimeDTO, Set<String> unchangedFields) {
        CommonZonedDateTimeDTO commonZonedDateTimeDTO = findOne(changeCommonZonedDateTimeDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonZonedDateTimeDTO, commonZonedDateTimeDTO, unchangedFields.toArray(new String[0]));
        commonZonedDateTimeDTO = save(commonZonedDateTimeDTO);
        return commonZonedDateTimeDTO;
    }

    /**
     * Update specified field by commonZonedDateTime
     */
    public CommonZonedDateTimeDTO updateBySpecifiedField(CommonZonedDateTimeDTO changeCommonZonedDateTimeDTO, String fieldName) {
        CommonZonedDateTimeDTO commonZonedDateTimeDTO = findOne(changeCommonZonedDateTimeDTO.getId()).get();
        BeanUtil.setFieldValue(commonZonedDateTimeDTO, fieldName, BeanUtil.getFieldValue(changeCommonZonedDateTimeDTO,fieldName));
        commonZonedDateTimeDTO = save(commonZonedDateTimeDTO);
        return commonZonedDateTimeDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
