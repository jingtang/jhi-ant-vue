package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonDouble;
import com.aidriveall.cms.repository.CommonDoubleRepository;
import com.aidriveall.cms.service.dto.CommonDoubleDTO;
import com.aidriveall.cms.service.mapper.CommonDoubleMapper;
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
 * Service Implementation for managing {@link CommonDouble}.
 */
@Service
@Transactional
public class CommonDoubleService {

    private final Logger log = LoggerFactory.getLogger(CommonDoubleService.class);

    private final CommonDoubleRepository commonDoubleRepository;

    private final CacheManager cacheManager;

    private final CommonDoubleMapper commonDoubleMapper;

    public CommonDoubleService(CommonDoubleRepository commonDoubleRepository, CacheManager cacheManager, CommonDoubleMapper commonDoubleMapper) {
        this.commonDoubleRepository = commonDoubleRepository;
        this.cacheManager = cacheManager;
        this.commonDoubleMapper = commonDoubleMapper;
    }

    /**
     * Save a commonDouble.
     *
     * @param commonDoubleDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonDoubleDTO save(CommonDoubleDTO commonDoubleDTO) {
        log.debug("Request to save CommonDouble : {}", commonDoubleDTO);
        CommonDouble commonDouble = commonDoubleMapper.toEntity(commonDoubleDTO);
        commonDouble = commonDoubleRepository.save(commonDouble);
        return commonDoubleMapper.toDto(commonDouble);
    }

    /**
     * Get all the commonDoubles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonDoubleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonDoubles");
        return commonDoubleRepository.findAll(pageable)
            .map(commonDoubleMapper::toDto);
    }

    /**
    * count all the commonDoubles.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonDoubles");
        return commonDoubleRepository.count();
    }

    /**
     * Get one commonDouble by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonDoubleDTO> findOne(Long id) {
        log.debug("Request to get CommonDouble : {}", id);
        return commonDoubleRepository.findById(id)
            .map(commonDoubleMapper::toDto);
    }

    /**
     * Delete the commonDouble by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonDouble : {}", id);
        commonDoubleRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonDouble
     */
    public CommonDoubleDTO updateBySpecifiedFields(CommonDoubleDTO changeCommonDoubleDTO, Set<String> unchangedFields) {
        CommonDoubleDTO commonDoubleDTO = findOne(changeCommonDoubleDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonDoubleDTO, commonDoubleDTO, unchangedFields.toArray(new String[0]));
        commonDoubleDTO = save(commonDoubleDTO);
        return commonDoubleDTO;
    }

    /**
     * Update specified field by commonDouble
     */
    public CommonDoubleDTO updateBySpecifiedField(CommonDoubleDTO changeCommonDoubleDTO, String fieldName) {
        CommonDoubleDTO commonDoubleDTO = findOne(changeCommonDoubleDTO.getId()).get();
        BeanUtil.setFieldValue(commonDoubleDTO, fieldName, BeanUtil.getFieldValue(changeCommonDoubleDTO,fieldName));
        commonDoubleDTO = save(commonDoubleDTO);
        return commonDoubleDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
