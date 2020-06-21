package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonInteger;
import com.aidriveall.cms.repository.CommonIntegerRepository;
import com.aidriveall.cms.service.dto.CommonIntegerDTO;
import com.aidriveall.cms.service.mapper.CommonIntegerMapper;
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
 * Service Implementation for managing {@link CommonInteger}.
 */
@Service
@Transactional
public class CommonIntegerService {

    private final Logger log = LoggerFactory.getLogger(CommonIntegerService.class);

    private final CommonIntegerRepository commonIntegerRepository;

    private final CacheManager cacheManager;

    private final CommonIntegerMapper commonIntegerMapper;

    public CommonIntegerService(CommonIntegerRepository commonIntegerRepository, CacheManager cacheManager, CommonIntegerMapper commonIntegerMapper) {
        this.commonIntegerRepository = commonIntegerRepository;
        this.cacheManager = cacheManager;
        this.commonIntegerMapper = commonIntegerMapper;
    }

    /**
     * Save a commonInteger.
     *
     * @param commonIntegerDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonIntegerDTO save(CommonIntegerDTO commonIntegerDTO) {
        log.debug("Request to save CommonInteger : {}", commonIntegerDTO);
        CommonInteger commonInteger = commonIntegerMapper.toEntity(commonIntegerDTO);
        commonInteger = commonIntegerRepository.save(commonInteger);
        return commonIntegerMapper.toDto(commonInteger);
    }

    /**
     * Get all the commonIntegers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonIntegerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonIntegers");
        return commonIntegerRepository.findAll(pageable)
            .map(commonIntegerMapper::toDto);
    }

    /**
    * count all the commonIntegers.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonIntegers");
        return commonIntegerRepository.count();
    }

    /**
     * Get one commonInteger by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonIntegerDTO> findOne(Long id) {
        log.debug("Request to get CommonInteger : {}", id);
        return commonIntegerRepository.findById(id)
            .map(commonIntegerMapper::toDto);
    }

    /**
     * Delete the commonInteger by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonInteger : {}", id);
        commonIntegerRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonInteger
     */
    public CommonIntegerDTO updateBySpecifiedFields(CommonIntegerDTO changeCommonIntegerDTO, Set<String> unchangedFields) {
        CommonIntegerDTO commonIntegerDTO = findOne(changeCommonIntegerDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonIntegerDTO, commonIntegerDTO, unchangedFields.toArray(new String[0]));
        commonIntegerDTO = save(commonIntegerDTO);
        return commonIntegerDTO;
    }

    /**
     * Update specified field by commonInteger
     */
    public CommonIntegerDTO updateBySpecifiedField(CommonIntegerDTO changeCommonIntegerDTO, String fieldName) {
        CommonIntegerDTO commonIntegerDTO = findOne(changeCommonIntegerDTO.getId()).get();
        BeanUtil.setFieldValue(commonIntegerDTO, fieldName, BeanUtil.getFieldValue(changeCommonIntegerDTO,fieldName));
        commonIntegerDTO = save(commonIntegerDTO);
        return commonIntegerDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
