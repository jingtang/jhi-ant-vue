package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CompanyBusiness;
import com.aidriveall.cms.repository.CompanyBusinessRepository;
import com.aidriveall.cms.service.dto.CompanyBusinessDTO;
import com.aidriveall.cms.service.mapper.CompanyBusinessMapper;
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
 * Service Implementation for managing {@link CompanyBusiness}.
 */
@Service
@Transactional
public class CompanyBusinessService {

    private final Logger log = LoggerFactory.getLogger(CompanyBusinessService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.BusinessType.class.getName() + ".companyBusiness",
        com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".companyBusinesses"
        );

    private final CompanyBusinessRepository companyBusinessRepository;

    private final CacheManager cacheManager;

    private final CompanyBusinessMapper companyBusinessMapper;

    public CompanyBusinessService(CompanyBusinessRepository companyBusinessRepository, CacheManager cacheManager, CompanyBusinessMapper companyBusinessMapper) {
        this.companyBusinessRepository = companyBusinessRepository;
        this.cacheManager = cacheManager;
        this.companyBusinessMapper = companyBusinessMapper;
    }

    /**
     * Save a companyBusiness.
     *
     * @param companyBusinessDTO the entity to save.
     * @return the persisted entity.
     */
    public CompanyBusinessDTO save(CompanyBusinessDTO companyBusinessDTO) {
        log.debug("Request to save CompanyBusiness : {}", companyBusinessDTO);
        CompanyBusiness companyBusiness = companyBusinessMapper.toEntity(companyBusinessDTO);
        companyBusiness = companyBusinessRepository.save(companyBusiness);
        return companyBusinessMapper.toDto(companyBusiness);
    }

    /**
     * Get all the companyBusinesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyBusinessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyBusinesses");
        return companyBusinessRepository.findAll(pageable)
            .map(companyBusinessMapper::toDto);
    }

    /**
    * count all the companyBusinesses.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CompanyBusinesses");
        return companyBusinessRepository.count();
    }

    /**
     * Get one companyBusiness by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyBusinessDTO> findOne(Long id) {
        log.debug("Request to get CompanyBusiness : {}", id);
        return companyBusinessRepository.findById(id)
            .map(companyBusinessMapper::toDto);
    }

    /**
     * Delete the companyBusiness by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyBusiness : {}", id);
        companyBusinessRepository.deleteById(id);
    }

    /**
     * Update specified fields by companyBusiness
     */
    public CompanyBusinessDTO updateBySpecifiedFields(CompanyBusinessDTO changeCompanyBusinessDTO, Set<String> unchangedFields) {
        CompanyBusinessDTO companyBusinessDTO = findOne(changeCompanyBusinessDTO.getId()).get();
        BeanUtil.copyProperties(changeCompanyBusinessDTO, companyBusinessDTO, unchangedFields.toArray(new String[0]));
        companyBusinessDTO = save(companyBusinessDTO);
        return companyBusinessDTO;
    }

    /**
     * Update specified field by companyBusiness
     */
    public CompanyBusinessDTO updateBySpecifiedField(CompanyBusinessDTO changeCompanyBusinessDTO, String fieldName) {
        CompanyBusinessDTO companyBusinessDTO = findOne(changeCompanyBusinessDTO.getId()).get();
        BeanUtil.setFieldValue(companyBusinessDTO, fieldName, BeanUtil.getFieldValue(changeCompanyBusinessDTO,fieldName));
        companyBusinessDTO = save(companyBusinessDTO);
        return companyBusinessDTO;
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
