package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.CommonTable;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.repository.UserRepository;
import com.aidriveall.cms.security.SecurityUtils;
import com.aidriveall.cms.service.dto.CommonTableDTO;
import com.aidriveall.cms.service.dto.CommonTableFieldDTO;
import com.aidriveall.cms.service.dto.CommonTableRelationshipDTO;
import com.aidriveall.cms.service.mapper.CommonTableMapper;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link CommonTable}.
 */
@Service
@Transactional
public class CommonTableService {

    private final Logger log = LoggerFactory.getLogger(CommonTableService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.honmtech.cms.domain.CommonTableField.class.getName() + ".commonTable",
        com.honmtech.cms.domain.CommonTableRelationship.class.getName() + ".commonTable",
        com.honmtech.cms.domain.User.class.getName() + ".commonTable",
        com.honmtech.cms.domain.BusinessType.class.getName() + ".commonTable"
        );

    private final CommonTableRepository commonTableRepository;

    private final CacheManager cacheManager;

    private final CommonTableMapper commonTableMapper;

    private final UserRepository userRepository;

    private final CommonTableFieldService commonTableFieldService;

    private final CommonTableRelationshipService commonTableRelationshipService;

    public CommonTableService(CommonTableRepository commonTableRepository, CacheManager cacheManager, CommonTableMapper commonTableMapper, UserRepository userRepository, CommonTableFieldService commonTableFieldService, CommonTableRelationshipService commonTableRelationshipService) {
        this.commonTableRepository = commonTableRepository;
        this.cacheManager = cacheManager;
        this.commonTableMapper = commonTableMapper;
        this.userRepository = userRepository;
        this.commonTableFieldService = commonTableFieldService;
        this.commonTableRelationshipService = commonTableRelationshipService;
    }

    /**
     * Save a commonTable.
     *
     * @param commonTableDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonTableDTO save(CommonTableDTO commonTableDTO) {
        log.debug("Request to save CommonTable : {}", commonTableDTO);
        CommonTable commonTable = commonTableMapper.toEntity(commonTableDTO);
        commonTable = commonTableRepository.save(commonTable);
        return commonTableMapper.toDto(commonTable);
    }

    /**
     * Get all the commonTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonTables");
        return commonTableRepository.findAll(pageable)
            .map(commonTableMapper::toDto);
    }

    /**
    * count all the commonTables.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonTables");
        return commonTableRepository.count();
    }

    /**
     * Get one commonTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonTableDTO> findOne(Long id) {
        log.debug("Request to get CommonTable : {}", id);
        return commonTableRepository.findById(id)
.map(commonTableMapper::toDto);
    }

    /**
     * Delete the commonTable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonTable : {}", id);
        commonTableRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<CommonTableDTO> findByCreatorIsCurrentUser() {
        return commonTableMapper.toDto(commonTableRepository.findByCreatorIsCurrentUser());
    }

    /**
     * Update specified fields by commonTable
     */
    public CommonTableDTO updateBySpecifiedFields(CommonTableDTO changeCommonTableDTO, Set<String> unchangedFields) {
        CommonTableDTO commonTableDTO = findOne(changeCommonTableDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonTableDTO, commonTableDTO, unchangedFields.toArray(new String[0]));
        commonTableDTO =  save(commonTableDTO);
        return commonTableDTO;
    }

    public Optional<CommonTableDTO> copyFromId(Long commonTableId) {
        CommonTableDTO source = findOne(commonTableId).get();
        if (source != null) {
            CommonTableDTO target = new CommonTableDTO();
            BeanUtil.copyProperties(source, target,"creatorId","relationships","commonTableFields");
            target.setName("copy" +target.getName());
            target.setEntityName("copy" + target.getEntityName());
            target.setId(null);
            target.setBaseTableId(source.getId());
            target.setSystem(false);
            target.setCreatorId(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse("admin")).orElse(new User()).getId());
//            target.setCreatAt(ZonedDateTime.now());
            target = save(target);
            CommonTableDTO finalTarget = target;
            source.getCommonTableFields().forEach(commonTableFieldDTO -> {
                CommonTableFieldDTO newFieldDTO = new CommonTableFieldDTO();
                BeanUtil.copyProperties(commonTableFieldDTO,newFieldDTO,"commonTableId");
                newFieldDTO.setCommonTableId(finalTarget.getId());
                newFieldDTO.setId(null);
                commonTableFieldService.save(newFieldDTO);
            });
            source.getRelationships().forEach(commonTableRelationshipDTO -> {
                CommonTableRelationshipDTO newRelationshipDTO = new CommonTableRelationshipDTO();
                BeanUtil.copyProperties(commonTableRelationshipDTO,newRelationshipDTO,"commonTableId");
                newRelationshipDTO.setCommonTableId(finalTarget.getId());
                newRelationshipDTO.setId(null);
                commonTableRelationshipService.save(newRelationshipDTO);
            });
            return findOne(target.getId());
        } else {
            throw new BadRequestAlertException("Invalid commonTableId", "CommonTable", "IdNotFound");
        }
    }

    /**
     * Update specified field by commonTable
     */
    public CommonTableDTO updateBySpecifiedField(CommonTableDTO changeCommonTableDTO, String fieldName) {
        CommonTableDTO commonTableDTO = findOne(changeCommonTableDTO.getId()).get();
        BeanUtil.setFieldValue(commonTableDTO, fieldName, BeanUtil.getFieldValue(changeCommonTableDTO,fieldName));
        commonTableDTO = save(commonTableDTO);
        return commonTableDTO;
    }


    public Optional<CommonTableDTO> findOneByEntityName(String entityName) {
        return commonTableRepository.findOneByEntityName(entityName)
            .map(commonTableMapper::toDto);
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
