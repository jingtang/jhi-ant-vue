package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.DataDictionary;
import com.aidriveall.cms.repository.DataDictionaryRepository;
import com.aidriveall.cms.service.dto.DataDictionaryDTO;
import com.aidriveall.cms.service.mapper.DataDictionaryMapper;
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
 * Service Implementation for managing {@link DataDictionary}.
 */
@Service
@Transactional
public class DataDictionaryService {

    private final Logger log = LoggerFactory.getLogger(DataDictionaryService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.DataDictionary.class.getName() + ".parent",
        com.aidriveall.cms.domain.DataDictionary.class.getName() + ".children"
        );

    private final DataDictionaryRepository dataDictionaryRepository;

    private final CacheManager cacheManager;

    private final DataDictionaryMapper dataDictionaryMapper;

    public DataDictionaryService(DataDictionaryRepository dataDictionaryRepository, CacheManager cacheManager, DataDictionaryMapper dataDictionaryMapper) {
        this.dataDictionaryRepository = dataDictionaryRepository;
        this.cacheManager = cacheManager;
        this.dataDictionaryMapper = dataDictionaryMapper;
    }

    /**
     * Save a dataDictionary.
     *
     * @param dataDictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public DataDictionaryDTO save(DataDictionaryDTO dataDictionaryDTO) {
        log.debug("Request to save DataDictionary : {}", dataDictionaryDTO);
        DataDictionary dataDictionary = dataDictionaryMapper.toEntity(dataDictionaryDTO);
        clearChildrenCache();
        dataDictionary = dataDictionaryRepository.save(dataDictionary);
        // 更新缓存
        if (dataDictionary.getParent() != null) {
            dataDictionary.getParent().addChildren(dataDictionary);
        }
        return dataDictionaryMapper.toDto(dataDictionary);
    }

    /**
     * Get all the dataDictionaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DataDictionaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataDictionaries");
        return dataDictionaryRepository.findAll(pageable)
            .map(dataDictionaryMapper::toDto);
    }

    /**
    * Get all the dataDictionaries for parent is null.
    *
    * @param pageable the pagination information
    * @return the list of entities
    */
    @Transactional(readOnly = true)
    public Page<DataDictionaryDTO> findAllTop(Pageable pageable) {
        log.debug("Request to get all DataDictionaries for parent is null");
            return dataDictionaryRepository.findAllByParentIsNull(pageable)
                .map(dataDictionaryMapper::toDto);
    }
    /**
    * count all the dataDictionaries.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all DataDictionaries");
        return dataDictionaryRepository.count();
    }

    /**
     * Get one dataDictionary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataDictionaryDTO> findOne(Long id) {
        log.debug("Request to get DataDictionary : {}", id);
        return dataDictionaryRepository.findById(id)
            .map(dataDictionaryMapper::toDto);
    }

    /**
     * Delete the dataDictionary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DataDictionary : {}", id);
        DataDictionary dataDictionary = dataDictionaryRepository.getOne(id);
        if (dataDictionary.getParent() != null) {
            dataDictionary.getParent().removeChildren(dataDictionary);
        }
        if ( dataDictionary.getChildren() != null) {
            dataDictionary.getChildren().forEach(subDataDictionary -> {
                subDataDictionary.setParent(null);
            });
        }
        dataDictionaryRepository.deleteById(id);
    }

    /**
     * Update specified fields by dataDictionary
     */
    public DataDictionaryDTO updateBySpecifiedFields(DataDictionaryDTO changeDataDictionaryDTO, Set<String> unchangedFields) {
        DataDictionaryDTO dataDictionaryDTO = findOne(changeDataDictionaryDTO.getId()).get();
        BeanUtil.copyProperties(changeDataDictionaryDTO, dataDictionaryDTO, unchangedFields.toArray(new String[0]));
        dataDictionaryDTO = save(dataDictionaryDTO);
        return dataDictionaryDTO;
    }

    /**
     * Update specified field by dataDictionary
     */
    public DataDictionaryDTO updateBySpecifiedField(DataDictionaryDTO changeDataDictionaryDTO, String fieldName) {
        DataDictionaryDTO dataDictionaryDTO = findOne(changeDataDictionaryDTO.getId()).get();
        BeanUtil.setFieldValue(dataDictionaryDTO, fieldName, BeanUtil.getFieldValue(changeDataDictionaryDTO,fieldName));
        dataDictionaryDTO = save(dataDictionaryDTO);
        return dataDictionaryDTO;
    }
    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.aidriveall.cms.domain.DataDictionary.class.getName() + ".children"))
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
