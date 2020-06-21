package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonLong;
import com.aidriveall.cms.repository.CommonLongRepository;
import com.aidriveall.cms.service.dto.CommonLongDTO;
import com.aidriveall.cms.service.mapper.CommonLongMapper;
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
 * Service Implementation for managing {@link CommonLong}.
 */
@Service
@Transactional
public class CommonLongService {

    private final Logger log = LoggerFactory.getLogger(CommonLongService.class);

    private final CommonLongRepository commonLongRepository;

    private final CacheManager cacheManager;

    private final CommonLongMapper commonLongMapper;

    public CommonLongService(CommonLongRepository commonLongRepository, CacheManager cacheManager, CommonLongMapper commonLongMapper) {
        this.commonLongRepository = commonLongRepository;
        this.cacheManager = cacheManager;
        this.commonLongMapper = commonLongMapper;
    }

    /**
     * Save a commonLong.
     *
     * @param commonLongDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonLongDTO save(CommonLongDTO commonLongDTO) {
        log.debug("Request to save CommonLong : {}", commonLongDTO);
        CommonLong commonLong = commonLongMapper.toEntity(commonLongDTO);
        commonLong = commonLongRepository.save(commonLong);
        return commonLongMapper.toDto(commonLong);
    }

    /**
     * Get all the commonLongs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonLongDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonLongs");
        return commonLongRepository.findAll(pageable)
            .map(commonLongMapper::toDto);
    }

    /**
    * count all the commonLongs.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonLongs");
        return commonLongRepository.count();
    }

    /**
     * Get one commonLong by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonLongDTO> findOne(Long id) {
        log.debug("Request to get CommonLong : {}", id);
        return commonLongRepository.findById(id)
            .map(commonLongMapper::toDto);
    }

    /**
     * Delete the commonLong by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonLong : {}", id);
        commonLongRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonLong
     */
    public CommonLongDTO updateBySpecifiedFields(CommonLongDTO changeCommonLongDTO, Set<String> unchangedFields) {
        CommonLongDTO commonLongDTO = findOne(changeCommonLongDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonLongDTO, commonLongDTO, unchangedFields.toArray(new String[0]));
        commonLongDTO = save(commonLongDTO);
        return commonLongDTO;
    }

    /**
     * Update specified field by commonLong
     */
    public CommonLongDTO updateBySpecifiedField(CommonLongDTO changeCommonLongDTO, String fieldName) {
        CommonLongDTO commonLongDTO = findOne(changeCommonLongDTO.getId()).get();
        BeanUtil.setFieldValue(commonLongDTO, fieldName, BeanUtil.getFieldValue(changeCommonLongDTO,fieldName));
        commonLongDTO = save(commonLongDTO);
        return commonLongDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
