package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonQuery;
import com.aidriveall.cms.repository.CommonQueryRepository;
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.service.dto.CommonQueryDTO;
import com.aidriveall.cms.service.mapper.CommonQueryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link CommonQuery}.
 */
@Service
@Transactional
public class CommonQueryService {

    private final Logger log = LoggerFactory.getLogger(CommonQueryService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.CommonQueryItem.class.getName() + ".query",
        com.aidriveall.cms.domain.User.class.getName() + ".commonQuery",
        com.aidriveall.cms.domain.CommonTable.class.getName() + ".commonQuery"
        );

    private final CommonQueryRepository commonQueryRepository;

    private final EntityManager em;

    private final CommonTableRepository commonTableRepository;

    private final CommonQueryMapper commonQueryMapper;

    private final CacheManager cacheManager;

    public CommonQueryService(CommonQueryRepository commonQueryRepository, EntityManager em, CommonTableRepository commonTableRepository, CommonQueryMapper commonQueryMapper, CacheManager cacheManager) {
        this.commonQueryRepository = commonQueryRepository;
        this.em = em;
        this.commonTableRepository = commonTableRepository;
        this.commonQueryMapper = commonQueryMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * Save a commonQuery.
     *
     * @param commonQueryDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonQueryDTO save(CommonQueryDTO commonQueryDTO) {
        log.debug("Request to save CommonQuery : {}", commonQueryDTO);
        CommonQuery commonQuery = commonQueryMapper.toEntity(commonQueryDTO);
        commonQuery = commonQueryRepository.save(commonQuery);
        return commonQueryMapper.toDto(commonQuery);
    }

    /**
     * Get all the commonQueries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonQueryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonQueries");
        return commonQueryRepository.findAll(pageable)
            .map(commonQueryMapper::toDto);
    }

    /**
    * count all the commonQueries.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonQueries");
        return commonQueryRepository.count();
    }

    /**
     * Get one commonQuery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonQueryDTO> findOne(Long id) {
        log.debug("Request to get CommonQuery : {}", id);
        return commonQueryRepository.findById(id)
            .map(commonQueryMapper::toDto);
    }

    /**
     * Delete the commonQuery by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonQuery : {}", id);
        commonQueryRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<CommonQueryDTO> findByModifierIsCurrentUser() {
        return commonQueryMapper.toDto(commonQueryRepository.findByModifierIsCurrentUser());
    }

    /**
     * Update specified fields by commonQuery
     */
    public CommonQueryDTO updateBySpecifiedFields(CommonQueryDTO changeCommonQueryDTO, Set<String> unchangedFields) {
        CommonQueryDTO commonQueryDTO = findOne(changeCommonQueryDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonQueryDTO, commonQueryDTO, unchangedFields.toArray(new String[0]));
        commonQueryDTO = save(commonQueryDTO);
        return commonQueryDTO;
    }

    /**
     * Update specified field by commonQuery
     */
    public CommonQueryDTO updateBySpecifiedField(CommonQueryDTO changeCommonQueryDTO, String fieldName) {
        CommonQueryDTO commonQueryDTO = findOne(changeCommonQueryDTO.getId()).get();
        BeanUtil.setFieldValue(commonQueryDTO, fieldName, BeanUtil.getFieldValue(changeCommonQueryDTO,fieldName));
        commonQueryDTO = save(commonQueryDTO);
        return commonQueryDTO;
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
