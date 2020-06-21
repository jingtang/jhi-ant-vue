package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CompanyCustomer;
import com.aidriveall.cms.repository.CompanyCustomerRepository;
import com.aidriveall.cms.service.dto.CompanyCustomerDTO;
import com.aidriveall.cms.service.mapper.CompanyCustomerMapper;
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
 * Service Implementation for managing {@link CompanyCustomer}.
 */
@Service
@Transactional
public class CompanyCustomerService {

    private final Logger log = LoggerFactory.getLogger(CompanyCustomerService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".parent",
        com.aidriveall.cms.domain.CompanyUser.class.getName() + ".company",
        com.aidriveall.cms.domain.CompanyBusiness.class.getName() + ".company",
        com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".children"
        );

    private final CompanyCustomerRepository companyCustomerRepository;

    private final CacheManager cacheManager;

    private final CompanyCustomerMapper companyCustomerMapper;

    public CompanyCustomerService(CompanyCustomerRepository companyCustomerRepository, CacheManager cacheManager, CompanyCustomerMapper companyCustomerMapper) {
        this.companyCustomerRepository = companyCustomerRepository;
        this.cacheManager = cacheManager;
        this.companyCustomerMapper = companyCustomerMapper;
    }

    /**
     * Save a companyCustomer.
     *
     * @param companyCustomerDTO the entity to save.
     * @return the persisted entity.
     */
    public CompanyCustomerDTO save(CompanyCustomerDTO companyCustomerDTO) {
        log.debug("Request to save CompanyCustomer : {}", companyCustomerDTO);
        CompanyCustomer companyCustomer = companyCustomerMapper.toEntity(companyCustomerDTO);
        clearChildrenCache();
        companyCustomer = companyCustomerRepository.save(companyCustomer);
        // 更新缓存
        if (companyCustomer.getParent() != null) {
            companyCustomer.getParent().addChildren(companyCustomer);
        }
        return companyCustomerMapper.toDto(companyCustomer);
    }

    /**
     * Get all the companyCustomers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyCustomerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyCustomers");
        return companyCustomerRepository.findAll(pageable)
            .map(companyCustomerMapper::toDto);
    }

    /**
    * Get all the companyCustomers for parent is null.
    *
    * @param pageable the pagination information
    * @return the list of entities
    */
    @Transactional(readOnly = true)
    public Page<CompanyCustomerDTO> findAllTop(Pageable pageable) {
        log.debug("Request to get all CompanyCustomers for parent is null");
            return companyCustomerRepository.findAllByParentIsNull(pageable)
                .map(companyCustomerMapper::toDto);
    }
    /**
    * count all the companyCustomers.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CompanyCustomers");
        return companyCustomerRepository.count();
    }

    /**
     * Get one companyCustomer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyCustomerDTO> findOne(Long id) {
        log.debug("Request to get CompanyCustomer : {}", id);
        return companyCustomerRepository.findById(id)
            .map(companyCustomerMapper::toDto);
    }

    /**
     * Delete the companyCustomer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyCustomer : {}", id);
        CompanyCustomer companyCustomer = companyCustomerRepository.getOne(id);
        if (companyCustomer.getParent() != null) {
            companyCustomer.getParent().removeChildren(companyCustomer);
        }
        if ( companyCustomer.getChildren() != null) {
            companyCustomer.getChildren().forEach(subCompanyCustomer -> {
                subCompanyCustomer.setParent(null);
            });
        }
        companyCustomerRepository.deleteById(id);
    }

    /**
     * Update specified fields by companyCustomer
     */
    public CompanyCustomerDTO updateBySpecifiedFields(CompanyCustomerDTO changeCompanyCustomerDTO, Set<String> unchangedFields) {
        CompanyCustomerDTO companyCustomerDTO = findOne(changeCompanyCustomerDTO.getId()).get();
        BeanUtil.copyProperties(changeCompanyCustomerDTO, companyCustomerDTO, unchangedFields.toArray(new String[0]));
        companyCustomerDTO = save(companyCustomerDTO);
        return companyCustomerDTO;
    }

    /**
     * Update specified field by companyCustomer
     */
    public CompanyCustomerDTO updateBySpecifiedField(CompanyCustomerDTO changeCompanyCustomerDTO, String fieldName) {
        CompanyCustomerDTO companyCustomerDTO = findOne(changeCompanyCustomerDTO.getId()).get();
        BeanUtil.setFieldValue(companyCustomerDTO, fieldName, BeanUtil.getFieldValue(changeCompanyCustomerDTO,fieldName));
        companyCustomerDTO = save(companyCustomerDTO);
        return companyCustomerDTO;
    }
    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".children"))
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
