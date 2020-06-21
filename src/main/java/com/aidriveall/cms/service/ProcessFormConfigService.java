package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.ProcessFormConfig;
import com.aidriveall.cms.repository.ProcessFormConfigRepository;
import com.aidriveall.cms.service.dto.ProcessFormConfigDTO;
import com.aidriveall.cms.service.mapper.ProcessFormConfigMapper;
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
 * Service Implementation for managing {@link ProcessFormConfig}.
 */
@Service
@Transactional
public class ProcessFormConfigService {

    private final Logger log = LoggerFactory.getLogger(ProcessFormConfigService.class);

    private final ProcessFormConfigRepository processFormConfigRepository;

    private final CacheManager cacheManager;

    private final ProcessFormConfigMapper processFormConfigMapper;

    public ProcessFormConfigService(ProcessFormConfigRepository processFormConfigRepository, CacheManager cacheManager, ProcessFormConfigMapper processFormConfigMapper) {
        this.processFormConfigRepository = processFormConfigRepository;
        this.cacheManager = cacheManager;
        this.processFormConfigMapper = processFormConfigMapper;
    }

    /**
     * Save a processFormConfig.
     *
     * @param processFormConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessFormConfigDTO save(ProcessFormConfigDTO processFormConfigDTO) {
        log.debug("Request to save ProcessFormConfig : {}", processFormConfigDTO);
        ProcessFormConfig processFormConfig = processFormConfigMapper.toEntity(processFormConfigDTO);
        processFormConfig = processFormConfigRepository.save(processFormConfig);
        return processFormConfigMapper.toDto(processFormConfig);
    }

    /**
     * Get all the processFormConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessFormConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessFormConfigs");
        return processFormConfigRepository.findAll(pageable)
            .map(processFormConfigMapper::toDto);
    }

    /**
    * count all the processFormConfigs.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all ProcessFormConfigs");
        return processFormConfigRepository.count();
    }

    /**
     * Get one processFormConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessFormConfigDTO> findOne(Long id) {
        log.debug("Request to get ProcessFormConfig : {}", id);
        return processFormConfigRepository.findById(id)
            .map(processFormConfigMapper::toDto);
    }

    /**
     * Delete the processFormConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessFormConfig : {}", id);
        processFormConfigRepository.deleteById(id);
    }

    /**
     * Update specified fields by processFormConfig
     */
    public ProcessFormConfigDTO updateBySpecifiedFields(ProcessFormConfigDTO changeProcessFormConfigDTO, Set<String> unchangedFields) {
        ProcessFormConfigDTO processFormConfigDTO = findOne(changeProcessFormConfigDTO.getId()).get();
        BeanUtil.copyProperties(changeProcessFormConfigDTO, processFormConfigDTO, unchangedFields.toArray(new String[0]));
        processFormConfigDTO = save(processFormConfigDTO);
        return processFormConfigDTO;
    }

    /**
     * Update specified field by processFormConfig
     */
    public ProcessFormConfigDTO updateBySpecifiedField(ProcessFormConfigDTO changeProcessFormConfigDTO, String fieldName) {
        ProcessFormConfigDTO processFormConfigDTO = findOne(changeProcessFormConfigDTO.getId()).get();
        BeanUtil.setFieldValue(processFormConfigDTO, fieldName, BeanUtil.getFieldValue(changeProcessFormConfigDTO,fieldName));
        processFormConfigDTO = save(processFormConfigDTO);
        return processFormConfigDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
