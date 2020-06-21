package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.ApiPermission;
import com.aidriveall.cms.repository.ApiPermissionRepository;
import com.aidriveall.cms.service.dto.ApiPermissionDTO;
import com.aidriveall.cms.service.mapper.ApiPermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.beans.PropertyDescriptor;
import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.security.SecurityUtils;
import com.aidriveall.cms.security.annotation.PermissionDefine;
import com.aidriveall.cms.domain.enumeration.ApiPermissionType;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link ApiPermission}.
 */
@Service
@Transactional
public class ApiPermissionService {

    private final Logger log = LoggerFactory.getLogger(ApiPermissionService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.ApiPermission.class.getName() + ".parent",
        com.aidriveall.cms.domain.ApiPermission.class.getName() + ".children",
        com.aidriveall.cms.domain.ViewPermission.class.getName() + ".apiPermissions"
        );

    private final ApiPermissionRepository apiPermissionRepository;

    private final CacheManager cacheManager;

    private final ApiPermissionMapper apiPermissionMapper;

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public ApiPermissionService(ApiPermissionRepository apiPermissionRepository, CacheManager cacheManager, ApiPermissionMapper apiPermissionMapper, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.apiPermissionRepository = apiPermissionRepository;
        this.cacheManager = cacheManager;
        this.apiPermissionMapper = apiPermissionMapper;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    /**
     * Save a apiPermission.
     *
     * @param apiPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public ApiPermissionDTO save(ApiPermissionDTO apiPermissionDTO) {
        log.debug("Request to save ApiPermission : {}", apiPermissionDTO);
        ApiPermission apiPermission = apiPermissionMapper.toEntity(apiPermissionDTO);
        clearChildrenCache();
        apiPermission = apiPermissionRepository.save(apiPermission);
        // 更新缓存
        if (apiPermission.getParent() != null) {
            apiPermission.getParent().addChildren(apiPermission);
        }
        return apiPermissionMapper.toDto(apiPermission);
    }

    /**
     * Get all the apiPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApiPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiPermissions");
        return apiPermissionRepository.findAll(pageable)
            .map(apiPermissionMapper::toDto);
    }

    /**
    * Get all the apiPermissions for parent is null.
    *
    * @param pageable the pagination information
    * @return the list of entities
    */
    @Transactional(readOnly = true)
    public Page<ApiPermissionDTO> findAllTop(Pageable pageable) {
        log.debug("Request to get all ApiPermissions for parent is null");
            return apiPermissionRepository.findAllByParentIsNull(pageable)
                .map(apiPermissionMapper::toDto);
    }
    /**
    * count all the apiPermissions.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all ApiPermissions");
        return apiPermissionRepository.count();
    }

    /**
     * Get one apiPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApiPermissionDTO> findOne(Long id) {
        log.debug("Request to get ApiPermission : {}", id);
        return apiPermissionRepository.findById(id)
.map(apiPermissionMapper::toDto);
    }

    /**
     * Delete the apiPermission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ApiPermission : {}", id);
        ApiPermission apiPermission = apiPermissionRepository.getOne(id);
        if (apiPermission.getParent() != null) {
            apiPermission.getParent().removeChildren(apiPermission);
        }
        if ( apiPermission.getChildren() != null) {
            apiPermission.getChildren().forEach(subApiPermission -> {
                subApiPermission.setParent(null);
            });
        }
        apiPermissionRepository.deleteById(id);
    }

    /**
     * Update specified fields by apiPermission
     */
    public ApiPermissionDTO updateBySpecifiedFields(ApiPermissionDTO changeApiPermissionDTO, Set<String> unchangedFields) {
        ApiPermissionDTO apiPermissionDTO = findOne(changeApiPermissionDTO.getId()).get();
        BeanUtil.copyProperties(changeApiPermissionDTO, apiPermissionDTO, unchangedFields.toArray(new String[0]));
        apiPermissionDTO =  save(apiPermissionDTO);
        return apiPermissionDTO;
    }

    /**
     * Update specified field by apiPermission
     */
    public ApiPermissionDTO updateBySpecifiedField(ApiPermissionDTO changeApiPermissionDTO, String fieldName) {
        ApiPermissionDTO apiPermissionDTO = findOne(changeApiPermissionDTO.getId()).get();
        BeanUtil.setFieldValue(apiPermissionDTO, fieldName, BeanUtil.getFieldValue(changeApiPermissionDTO,fieldName));
        apiPermissionDTO = save(apiPermissionDTO);
        return apiPermissionDTO;
    }
    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.aidriveall.cms.domain.ApiPermission.class.getName() + ".children"))
            .clear();
    }
    private void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> {
            if (cacheManager.getCache(cacheName) != null) {
                cacheManager.getCache(cacheName).clear();
            }
        });
    }
    /**
     * regenerate ApiPermissions from Annotation
     */
    public void regenerateApiPermissions() {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            HandlerMethod method = m.getValue();
            PermissionDefine permissionDefineAnnotation = method.getMethodAnnotation(PermissionDefine.class);
            if (permissionDefineAnnotation != null) {
                // 处理group
                ApiPermission apiPermissionGroup = apiPermissionRepository.findOneByCode(permissionDefineAnnotation.groupCode())
                    .orElse(apiPermissionRepository.save(
                        new ApiPermission()
                            .code(permissionDefineAnnotation.groupCode())
                            .name(permissionDefineAnnotation.groupName())
                            .type(ApiPermissionType.BUSINESS)));
                // 处理permission
                ApiPermission apiPermission = apiPermissionRepository.findOneByCode(permissionDefineAnnotation.permissionCode())
                    .orElse(apiPermissionRepository.save(
                        new ApiPermission()
                            .code(permissionDefineAnnotation.groupCode())
                            .name(permissionDefineAnnotation.groupName())
                            .type(ApiPermissionType.BUSINESS)));
                // 设置parent并保存
                apiPermissionRepository.save(apiPermission.parent(apiPermissionGroup));
            }
        }
    }
    public List<ApiPermissionDTO> findAllApiPermissionsByCurrentUser() {
        if (SecurityUtils.isAuthenticated()) {
            return apiPermissionMapper.toDto(apiPermissionRepository.findAllApiPermissionsByCurrentUser());
        } else {
            return new ArrayList<>();
        }
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
