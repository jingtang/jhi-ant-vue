package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.AdministrativeDivision;
import com.aidriveall.cms.repository.AdministrativeDivisionRepository;
import com.aidriveall.cms.service.dto.AdministrativeDivisionDTO;
import com.aidriveall.cms.service.mapper.AdministrativeDivisionMapper;
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
 * Service Implementation for managing {@link AdministrativeDivision}.
 */
@Service
@Transactional
public class AdministrativeDivisionService {

    private final Logger log = LoggerFactory.getLogger(AdministrativeDivisionService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.AdministrativeDivision.class.getName() + ".parent",
        com.aidriveall.cms.domain.AdministrativeDivision.class.getName() + ".children"
        );

    private final AdministrativeDivisionRepository administrativeDivisionRepository;

    private final CacheManager cacheManager;

    private final AdministrativeDivisionMapper administrativeDivisionMapper;

    public AdministrativeDivisionService(AdministrativeDivisionRepository administrativeDivisionRepository, CacheManager cacheManager, AdministrativeDivisionMapper administrativeDivisionMapper) {
        this.administrativeDivisionRepository = administrativeDivisionRepository;
        this.cacheManager = cacheManager;
        this.administrativeDivisionMapper = administrativeDivisionMapper;
    }

    /**
     * Save a administrativeDivision.
     *
     * @param administrativeDivisionDTO the entity to save.
     * @return the persisted entity.
     */
    public AdministrativeDivisionDTO save(AdministrativeDivisionDTO administrativeDivisionDTO) {
        log.debug("Request to save AdministrativeDivision : {}", administrativeDivisionDTO);
        AdministrativeDivision administrativeDivision = administrativeDivisionMapper.toEntity(administrativeDivisionDTO);
        clearChildrenCache();
        administrativeDivision = administrativeDivisionRepository.save(administrativeDivision);
        // 更新缓存
        if (administrativeDivision.getParent() != null) {
            administrativeDivision.getParent().addChildren(administrativeDivision);
        }
        return administrativeDivisionMapper.toDto(administrativeDivision);
    }

    /**
     * Get all the administrativeDivisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdministrativeDivisionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdministrativeDivisions");
        return administrativeDivisionRepository.findAll(pageable)
            .map(administrativeDivisionMapper::toDto);
    }

    /**
    * Get all the administrativeDivisions for parent is null.
    *
    * @param pageable the pagination information
    * @return the list of entities
    */
    @Transactional(readOnly = true)
    public Page<AdministrativeDivisionDTO> findAllTop(Pageable pageable) {
        log.debug("Request to get all AdministrativeDivisions for parent is null");
            return administrativeDivisionRepository.findAllByParentIsNull(pageable)
                .map(administrativeDivisionMapper::toDto);
    }
    /**
    * count all the administrativeDivisions.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all AdministrativeDivisions");
        return administrativeDivisionRepository.count();
    }

    /**
     * Get one administrativeDivision by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdministrativeDivisionDTO> findOne(Long id) {
        log.debug("Request to get AdministrativeDivision : {}", id);
        return administrativeDivisionRepository.findById(id)
            .map(administrativeDivisionMapper::toDto);
    }

    /**
     * Delete the administrativeDivision by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdministrativeDivision : {}", id);
        AdministrativeDivision administrativeDivision = administrativeDivisionRepository.getOne(id);
        if (administrativeDivision.getParent() != null) {
            administrativeDivision.getParent().removeChildren(administrativeDivision);
        }
        if ( administrativeDivision.getChildren() != null) {
            administrativeDivision.getChildren().forEach(subAdministrativeDivision -> {
                subAdministrativeDivision.setParent(null);
            });
        }
        administrativeDivisionRepository.deleteById(id);
    }

    /**
     * Update specified fields by administrativeDivision
     */
    public AdministrativeDivisionDTO updateBySpecifiedFields(AdministrativeDivisionDTO changeAdministrativeDivisionDTO, Set<String> unchangedFields) {
        AdministrativeDivisionDTO administrativeDivisionDTO = findOne(changeAdministrativeDivisionDTO.getId()).get();
        BeanUtil.copyProperties(changeAdministrativeDivisionDTO, administrativeDivisionDTO, unchangedFields.toArray(new String[0]));
        administrativeDivisionDTO = save(administrativeDivisionDTO);
        return administrativeDivisionDTO;
    }

    /**
     * Update specified field by administrativeDivision
     */
    public AdministrativeDivisionDTO updateBySpecifiedField(AdministrativeDivisionDTO changeAdministrativeDivisionDTO, String fieldName) {
        AdministrativeDivisionDTO administrativeDivisionDTO = findOne(changeAdministrativeDivisionDTO.getId()).get();
        BeanUtil.setFieldValue(administrativeDivisionDTO, fieldName, BeanUtil.getFieldValue(changeAdministrativeDivisionDTO,fieldName));
        administrativeDivisionDTO = save(administrativeDivisionDTO);
        return administrativeDivisionDTO;
    }
    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.aidriveall.cms.domain.AdministrativeDivision.class.getName() + ".children"))
            .clear();
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
