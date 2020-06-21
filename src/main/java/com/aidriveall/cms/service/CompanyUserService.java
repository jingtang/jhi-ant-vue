package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CompanyUser;
import com.aidriveall.cms.repository.CompanyUserRepository;
import com.aidriveall.cms.repository.UserRepository;
import com.aidriveall.cms.security.SecurityUtils;
import com.aidriveall.cms.service.dto.CompanyUserDTO;
import com.aidriveall.cms.service.mapper.CompanyUserMapper;
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
 * Service Implementation for managing {@link CompanyUser}.
 */
@Service
@Transactional
public class CompanyUserService {

    private final Logger log = LoggerFactory.getLogger(CompanyUserService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.User.class.getName() + ".companyUser",
        com.aidriveall.cms.domain.CompanyCustomer.class.getName() + ".companyUsers"
        );

    private final CompanyUserRepository companyUserRepository;

    private final CacheManager cacheManager;

    private final CompanyUserMapper companyUserMapper;

    private final UserRepository userRepository;

    public CompanyUserService(CompanyUserRepository companyUserRepository, CacheManager cacheManager, CompanyUserMapper companyUserMapper, UserRepository userRepository) {
        this.companyUserRepository = companyUserRepository;
        this.cacheManager = cacheManager;
        this.companyUserMapper = companyUserMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a companyUser.
     *
     * @param companyUserDTO the entity to save.
     * @return the persisted entity.
     */
    public CompanyUserDTO save(CompanyUserDTO companyUserDTO) {
        log.debug("Request to save CompanyUser : {}", companyUserDTO);
        CompanyUser companyUser = companyUserMapper.toEntity(companyUserDTO);
        companyUser = companyUserRepository.save(companyUser);
        return companyUserMapper.toDto(companyUser);
    }

    /**
     * Get all the companyUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyUsers");
        return companyUserRepository.findAll(pageable)
            .map(companyUserMapper::toDto);
    }

    /**
    * count all the companyUsers.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CompanyUsers");
        return companyUserRepository.count();
    }

    /**
     * Get one companyUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyUserDTO> findOne(Long id) {
        log.debug("Request to get CompanyUser : {}", id);
        return companyUserRepository.findById(id)
            .map(companyUserMapper::toDto);
    }

    /**
     * Delete the companyUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyUser : {}", id);
        companyUserRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<CompanyUserDTO> findByUserIsCurrentUser() {
        return companyUserMapper.toDto(companyUserRepository.findByUserIsCurrentUser());
    }

    /**
     * Update specified fields by companyUser
     */
    public CompanyUserDTO updateBySpecifiedFields(CompanyUserDTO changeCompanyUserDTO, Set<String> unchangedFields) {
        CompanyUserDTO companyUserDTO = findOne(changeCompanyUserDTO.getId()).get();
        BeanUtil.copyProperties(changeCompanyUserDTO, companyUserDTO, unchangedFields.toArray(new String[0]));
        companyUserDTO = save(companyUserDTO);
        return companyUserDTO;
    }

    /**
     * Update specified field by companyUser
     */
    public CompanyUserDTO updateBySpecifiedField(CompanyUserDTO changeCompanyUserDTO, String fieldName) {
        CompanyUserDTO companyUserDTO = findOne(changeCompanyUserDTO.getId()).get();
        BeanUtil.setFieldValue(companyUserDTO, fieldName, BeanUtil.getFieldValue(changeCompanyUserDTO,fieldName));
        companyUserDTO = save(companyUserDTO);
        return companyUserDTO;
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
