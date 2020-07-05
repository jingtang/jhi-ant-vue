package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonQueryItem;
import com.aidriveall.cms.repository.CommonQueryItemRepository;
import com.aidriveall.cms.service.dto.CommonQueryItemDTO;
import com.aidriveall.cms.service.mapper.CommonQueryItemMapper;
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
 * Service Implementation for managing {@link CommonQueryItem}.
 */
@Service
@Transactional
public class CommonQueryItemService {

    private final Logger log = LoggerFactory.getLogger(CommonQueryItemService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.CommonQuery.class.getName() + ".items"
        );

    private final CommonQueryItemRepository commonQueryItemRepository;

    private final CacheManager cacheManager;

    private final CommonQueryItemMapper commonQueryItemMapper;

    public CommonQueryItemService(CommonQueryItemRepository commonQueryItemRepository, CacheManager cacheManager, CommonQueryItemMapper commonQueryItemMapper) {
        this.commonQueryItemRepository = commonQueryItemRepository;
        this.cacheManager = cacheManager;
        this.commonQueryItemMapper = commonQueryItemMapper;
    }

    /**
     * Save a commonQueryItem.
     *
     * @param commonQueryItemDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonQueryItemDTO save(CommonQueryItemDTO commonQueryItemDTO) {
        log.debug("Request to save CommonQueryItem : {}", commonQueryItemDTO);
        CommonQueryItem commonQueryItem = commonQueryItemMapper.toEntity(commonQueryItemDTO);
        commonQueryItem = commonQueryItemRepository.save(commonQueryItem);
        return commonQueryItemMapper.toDto(commonQueryItem);
    }

    /**
     * Get all the commonQueryItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonQueryItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonQueryItems");
        return commonQueryItemRepository.findAll(pageable)
            .map(commonQueryItemMapper::toDto);
    }

    /**
    * count all the commonQueryItems.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonQueryItems");
        return commonQueryItemRepository.count();
    }

    /**
     * Get one commonQueryItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonQueryItemDTO> findOne(Long id) {
        log.debug("Request to get CommonQueryItem : {}", id);
        return commonQueryItemRepository.findById(id)
            .map(commonQueryItemMapper::toDto);
    }

    /**
     * Delete the commonQueryItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonQueryItem : {}", id);
        commonQueryItemRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonQueryItem
     */
    public CommonQueryItemDTO updateBySpecifiedFields(CommonQueryItemDTO changeCommonQueryItemDTO, Set<String> unchangedFields) {
        CommonQueryItemDTO commonQueryItemDTO = findOne(changeCommonQueryItemDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonQueryItemDTO, commonQueryItemDTO, unchangedFields.toArray(new String[0]));
        commonQueryItemDTO = save(commonQueryItemDTO);
        return commonQueryItemDTO;
    }

    /**
     * Update specified field by commonQueryItem
     */
    public CommonQueryItemDTO updateBySpecifiedField(CommonQueryItemDTO changeCommonQueryItemDTO, String fieldName) {
        CommonQueryItemDTO commonQueryItemDTO = findOne(changeCommonQueryItemDTO.getId()).get();
        BeanUtil.setFieldValue(commonQueryItemDTO, fieldName, BeanUtil.getFieldValue(changeCommonQueryItemDTO,fieldName));
        commonQueryItemDTO = save(commonQueryItemDTO);
        return commonQueryItemDTO;
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
