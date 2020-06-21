package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.ProcessTableConfig;
import com.aidriveall.cms.repository.ProcessTableConfigRepository;
import com.aidriveall.cms.repository.UserRepository;
import com.aidriveall.cms.security.SecurityUtils;
import com.aidriveall.cms.service.dto.ProcessTableConfigDTO;
import com.aidriveall.cms.service.mapper.ProcessTableConfigMapper;
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
 * Service Implementation for managing {@link ProcessTableConfig}.
 */
@Service
@Transactional
public class ProcessTableConfigService {

    private final Logger log = LoggerFactory.getLogger(ProcessTableConfigService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.CommonTable.class.getName() + ".processTableConfig",
        com.aidriveall.cms.domain.User.class.getName() + ".processTableConfig"
        );

    private final ProcessTableConfigRepository processTableConfigRepository;

    private final CacheManager cacheManager;

    private final ProcessTableConfigMapper processTableConfigMapper;

    private final UserRepository userRepository;

    public ProcessTableConfigService(ProcessTableConfigRepository processTableConfigRepository, CacheManager cacheManager, ProcessTableConfigMapper processTableConfigMapper, UserRepository userRepository) {
        this.processTableConfigRepository = processTableConfigRepository;
        this.cacheManager = cacheManager;
        this.processTableConfigMapper = processTableConfigMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a processTableConfig.
     *
     * @param processTableConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessTableConfigDTO save(ProcessTableConfigDTO processTableConfigDTO) {
        log.debug("Request to save ProcessTableConfig : {}", processTableConfigDTO);
        ProcessTableConfig processTableConfig = processTableConfigMapper.toEntity(processTableConfigDTO);
        processTableConfig = processTableConfigRepository.save(processTableConfig);
        return processTableConfigMapper.toDto(processTableConfig);
    }

    /**
     * Get all the processTableConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessTableConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcessTableConfigs");
        return processTableConfigRepository.findAll(pageable)
            .map(processTableConfigMapper::toDto);
    }

    /**
    * count all the processTableConfigs.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all ProcessTableConfigs");
        return processTableConfigRepository.count();
    }

    /**
     * Get one processTableConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessTableConfigDTO> findOne(Long id) {
        log.debug("Request to get ProcessTableConfig : {}", id);
        return processTableConfigRepository.findById(id)
            .map(processTableConfigMapper::toDto);
    }

    /**
     * Delete the processTableConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessTableConfig : {}", id);
        processTableConfigRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<ProcessTableConfigDTO> findByCreatorIsCurrentUser() {
        return processTableConfigMapper.toDto(processTableConfigRepository.findByCreatorIsCurrentUser());
    }

    /**
     * Update specified fields by processTableConfig
     */
    public ProcessTableConfigDTO updateBySpecifiedFields(ProcessTableConfigDTO changeProcessTableConfigDTO, Set<String> unchangedFields) {
        ProcessTableConfigDTO processTableConfigDTO = findOne(changeProcessTableConfigDTO.getId()).get();
        BeanUtil.copyProperties(changeProcessTableConfigDTO, processTableConfigDTO, unchangedFields.toArray(new String[0]));
        processTableConfigDTO = save(processTableConfigDTO);
        return processTableConfigDTO;
    }

    /**
     * Update specified field by processTableConfig
     */
    public ProcessTableConfigDTO updateBySpecifiedField(ProcessTableConfigDTO changeProcessTableConfigDTO, String fieldName) {
        ProcessTableConfigDTO processTableConfigDTO = findOne(changeProcessTableConfigDTO.getId()).get();
        BeanUtil.setFieldValue(processTableConfigDTO, fieldName, BeanUtil.getFieldValue(changeProcessTableConfigDTO,fieldName));
        processTableConfigDTO = save(processTableConfigDTO);
        return processTableConfigDTO;
    }
    private void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> {
            if (cacheManager.getCache(cacheName) != null) {
                cacheManager.getCache(cacheName).clear();
            }
        });
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
