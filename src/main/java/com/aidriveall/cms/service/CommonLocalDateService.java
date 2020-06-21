package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonLocalDate;
import com.aidriveall.cms.repository.CommonLocalDateRepository;
import com.aidriveall.cms.service.dto.CommonLocalDateDTO;
import com.aidriveall.cms.service.mapper.CommonLocalDateMapper;
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
 * Service Implementation for managing {@link CommonLocalDate}.
 */
@Service
@Transactional
public class CommonLocalDateService {

    private final Logger log = LoggerFactory.getLogger(CommonLocalDateService.class);

    private final CommonLocalDateRepository commonLocalDateRepository;

    private final CacheManager cacheManager;

    private final CommonLocalDateMapper commonLocalDateMapper;

    public CommonLocalDateService(CommonLocalDateRepository commonLocalDateRepository, CacheManager cacheManager, CommonLocalDateMapper commonLocalDateMapper) {
        this.commonLocalDateRepository = commonLocalDateRepository;
        this.cacheManager = cacheManager;
        this.commonLocalDateMapper = commonLocalDateMapper;
    }

    /**
     * Save a commonLocalDate.
     *
     * @param commonLocalDateDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonLocalDateDTO save(CommonLocalDateDTO commonLocalDateDTO) {
        log.debug("Request to save CommonLocalDate : {}", commonLocalDateDTO);
        CommonLocalDate commonLocalDate = commonLocalDateMapper.toEntity(commonLocalDateDTO);
        commonLocalDate = commonLocalDateRepository.save(commonLocalDate);
        return commonLocalDateMapper.toDto(commonLocalDate);
    }

    /**
     * Get all the commonLocalDates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonLocalDateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonLocalDates");
        return commonLocalDateRepository.findAll(pageable)
            .map(commonLocalDateMapper::toDto);
    }

    /**
    * count all the commonLocalDates.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonLocalDates");
        return commonLocalDateRepository.count();
    }

    /**
     * Get one commonLocalDate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonLocalDateDTO> findOne(Long id) {
        log.debug("Request to get CommonLocalDate : {}", id);
        return commonLocalDateRepository.findById(id)
            .map(commonLocalDateMapper::toDto);
    }

    /**
     * Delete the commonLocalDate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonLocalDate : {}", id);
        commonLocalDateRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonLocalDate
     */
    public CommonLocalDateDTO updateBySpecifiedFields(CommonLocalDateDTO changeCommonLocalDateDTO, Set<String> unchangedFields) {
        CommonLocalDateDTO commonLocalDateDTO = findOne(changeCommonLocalDateDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonLocalDateDTO, commonLocalDateDTO, unchangedFields.toArray(new String[0]));
        commonLocalDateDTO = save(commonLocalDateDTO);
        return commonLocalDateDTO;
    }

    /**
     * Update specified field by commonLocalDate
     */
    public CommonLocalDateDTO updateBySpecifiedField(CommonLocalDateDTO changeCommonLocalDateDTO, String fieldName) {
        CommonLocalDateDTO commonLocalDateDTO = findOne(changeCommonLocalDateDTO.getId()).get();
        BeanUtil.setFieldValue(commonLocalDateDTO, fieldName, BeanUtil.getFieldValue(changeCommonLocalDateDTO,fieldName));
        commonLocalDateDTO = save(commonLocalDateDTO);
        return commonLocalDateDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
