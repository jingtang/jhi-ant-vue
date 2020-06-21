package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.ProcessEntityRelation;
import com.aidriveall.cms.repository.ProcessEntityRelationRepository;
import com.aidriveall.cms.service.dto.ProcessEntityRelationDTO;
import com.aidriveall.cms.service.mapper.ProcessEntityRelationMapper;
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
 * Service Implementation for managing {@link ProcessEntityRelation}.
 */
@Service
@Transactional
public class ProcessEntityRelationService {

    private final Logger log = LoggerFactory.getLogger(ProcessEntityRelationService.class);

    private final ProcessEntityRelationRepository processEntityRelationRepository;

    private final CacheManager cacheManager;

    private final ProcessEntityRelationMapper processEntityRelationMapper;

    public ProcessEntityRelationService(ProcessEntityRelationRepository processEntityRelationRepository, CacheManager cacheManager, ProcessEntityRelationMapper processEntityRelationMapper) {
        this.processEntityRelationRepository = processEntityRelationRepository;
        this.cacheManager = cacheManager;
        this.processEntityRelationMapper = processEntityRelationMapper;
    }

    /**
     * Save a processEntityRelation.
     *
     * @param processEntityRelationDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessEntityRelationDTO save(ProcessEntityRelationDTO processEntityRelationDTO) {
        log.debug("Request to save ProcessEntityRelation : {}", processEntityRelationDTO);
        ProcessEntityRelation processEntityRelation = processEntityRelationMapper.toEntity(processEntityRelationDTO);
        processEntityRelation = processEntityRelationRepository.save(processEntityRelation);
        return processEntityRelationMapper.toDto(processEntityRelation);
    }

    /**
     * Get all the processEntityRelations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessEntityRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessEntityRelations");
        return processEntityRelationRepository.findAll(pageable)
            .map(processEntityRelationMapper::toDto);
    }

    /**
    * count all the processEntityRelations.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all ProcessEntityRelations");
        return processEntityRelationRepository.count();
    }

    /**
     * Get one processEntityRelation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessEntityRelationDTO> findOne(Long id) {
        log.debug("Request to get ProcessEntityRelation : {}", id);
        return processEntityRelationRepository.findById(id)
            .map(processEntityRelationMapper::toDto);
    }

    /**
     * Delete the processEntityRelation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessEntityRelation : {}", id);
        processEntityRelationRepository.deleteById(id);
    }

    /**
     * Update specified fields by processEntityRelation
     */
    public ProcessEntityRelationDTO updateBySpecifiedFields(ProcessEntityRelationDTO changeProcessEntityRelationDTO, Set<String> unchangedFields) {
        ProcessEntityRelationDTO processEntityRelationDTO = findOne(changeProcessEntityRelationDTO.getId()).get();
        BeanUtil.copyProperties(changeProcessEntityRelationDTO, processEntityRelationDTO, unchangedFields.toArray(new String[0]));
        processEntityRelationDTO = save(processEntityRelationDTO);
        return processEntityRelationDTO;
    }

    /**
     * Update specified field by processEntityRelation
     */
    public ProcessEntityRelationDTO updateBySpecifiedField(ProcessEntityRelationDTO changeProcessEntityRelationDTO, String fieldName) {
        ProcessEntityRelationDTO processEntityRelationDTO = findOne(changeProcessEntityRelationDTO.getId()).get();
        BeanUtil.setFieldValue(processEntityRelationDTO, fieldName, BeanUtil.getFieldValue(changeProcessEntityRelationDTO,fieldName));
        processEntityRelationDTO = save(processEntityRelationDTO);
        return processEntityRelationDTO;
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
