package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.ViewPermission;
import com.aidriveall.cms.repository.ViewPermissionRepository;
import com.aidriveall.cms.service.dto.ViewPermissionDTO;
import com.aidriveall.cms.service.mapper.ViewPermissionMapper;
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
 * Service Implementation for managing {@link ViewPermission}.
 */
@Service
@Transactional
public class ViewPermissionService {

    private final Logger log = LoggerFactory.getLogger(ViewPermissionService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.ViewPermission.class.getName() + ".parent",
        com.aidriveall.cms.domain.ApiPermission.class.getName() + ".viewPermissions",
        com.aidriveall.cms.domain.ViewPermission.class.getName() + ".children",
        com.aidriveall.cms.domain.Authority.class.getName() + ".viewPermission"
        );

    private final ViewPermissionRepository viewPermissionRepository;

    private final CacheManager cacheManager;

    private final ViewPermissionMapper viewPermissionMapper;

    public ViewPermissionService(ViewPermissionRepository viewPermissionRepository, CacheManager cacheManager, ViewPermissionMapper viewPermissionMapper) {
        this.viewPermissionRepository = viewPermissionRepository;
        this.cacheManager = cacheManager;
        this.viewPermissionMapper = viewPermissionMapper;
    }

    /**
     * Save a viewPermission.
     *
     * @param viewPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewPermissionDTO save(ViewPermissionDTO viewPermissionDTO) {
        log.debug("Request to save ViewPermission : {}", viewPermissionDTO);
        ViewPermission viewPermission = viewPermissionMapper.toEntity(viewPermissionDTO);
        clearChildrenCache();
        viewPermission = viewPermissionRepository.save(viewPermission);
        // 更新缓存
        if (viewPermission.getParent() != null) {
            viewPermission.getParent().addChildren(viewPermission);
        }
        return viewPermissionMapper.toDto(viewPermission);
    }

    /**
     * Get all the viewPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ViewPermissions");
        return viewPermissionRepository.findAll(pageable)
            .map(viewPermissionMapper::toDto);
    }

    /**
    * Get all the viewPermissions for parent is null.
    *
    * @param pageable the pagination information
    * @return the list of entities
    */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> findAllTop(Pageable pageable) {
        log.debug("Request to get all ViewPermissions for parent is null");
            return viewPermissionRepository.findAllByParentIsNull(pageable)
                .map(viewPermissionMapper::toDto);
    }
    /**
    * count all the viewPermissions.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all ViewPermissions");
        return viewPermissionRepository.count();
    }

    /**
     * Get all the viewPermissions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ViewPermissionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return viewPermissionRepository.findAllWithEagerRelationships(pageable).map(viewPermissionMapper::toDto);
    }

    /**
     * Get one viewPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViewPermissionDTO> findOne(Long id) {
        log.debug("Request to get ViewPermission : {}", id);
        return viewPermissionRepository.findOneWithEagerRelationships(id)
            .map(viewPermissionMapper::toDto);
    }

    /**
     * Delete the viewPermission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ViewPermission : {}", id);
        ViewPermission viewPermission = viewPermissionRepository.getOne(id);
        if (viewPermission.getParent() != null) {
            viewPermission.getParent().removeChildren(viewPermission);
        }
        if ( viewPermission.getChildren() != null) {
            viewPermission.getChildren().forEach(subViewPermission -> {
                subViewPermission.setParent(null);
            });
        }
        viewPermissionRepository.deleteById(id);
    }

    /**
     * Update specified fields by viewPermission
     */
    public ViewPermissionDTO updateBySpecifiedFields(ViewPermissionDTO changeViewPermissionDTO, Set<String> unchangedFields) {
        ViewPermissionDTO viewPermissionDTO = findOne(changeViewPermissionDTO.getId()).get();
        BeanUtil.copyProperties(changeViewPermissionDTO, viewPermissionDTO, unchangedFields.toArray(new String[0]));
        viewPermissionDTO = save(viewPermissionDTO);
        return viewPermissionDTO;
    }

    /**
     * Update specified field by viewPermission
     */
    public ViewPermissionDTO updateBySpecifiedField(ViewPermissionDTO changeViewPermissionDTO, String fieldName) {
        ViewPermissionDTO viewPermissionDTO = findOne(changeViewPermissionDTO.getId()).get();
        BeanUtil.setFieldValue(viewPermissionDTO, fieldName, BeanUtil.getFieldValue(changeViewPermissionDTO,fieldName));
        viewPermissionDTO = save(viewPermissionDTO);
        return viewPermissionDTO;
    }
    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.aidriveall.cms.domain.ViewPermission.class.getName() + ".children"))
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
