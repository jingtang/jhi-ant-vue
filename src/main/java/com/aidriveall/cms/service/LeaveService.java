package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.Leave;
import com.aidriveall.cms.repository.LeaveRepository;
import com.aidriveall.cms.repository.UserRepository;
import com.aidriveall.cms.security.SecurityUtils;
import com.aidriveall.cms.service.dto.LeaveDTO;
import com.aidriveall.cms.service.mapper.LeaveMapper;
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
 * Service Implementation for managing {@link Leave}.
 */
@Service
@Transactional
public class LeaveService {

    private final Logger log = LoggerFactory.getLogger(LeaveService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.UploadImage.class.getName() + ".leave",
        com.aidriveall.cms.domain.User.class.getName() + ".leave"
        );

    private final LeaveRepository leaveRepository;

    private final CacheManager cacheManager;

    private final LeaveMapper leaveMapper;

    private final UserRepository userRepository;

    public LeaveService(LeaveRepository leaveRepository, CacheManager cacheManager, LeaveMapper leaveMapper, UserRepository userRepository) {
        this.leaveRepository = leaveRepository;
        this.cacheManager = cacheManager;
        this.leaveMapper = leaveMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a leave.
     *
     * @param leaveDTO the entity to save.
     * @return the persisted entity.
     */
    public LeaveDTO save(LeaveDTO leaveDTO) {
        log.debug("Request to save Leave : {}", leaveDTO);
        Leave leave = leaveMapper.toEntity(leaveDTO);
        leave = leaveRepository.save(leave);
        return leaveMapper.toDto(leave);
    }

    /**
     * Get all the leaves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leaves");
        return leaveRepository.findAll(pageable)
            .map(leaveMapper::toDto);
    }

    /**
    * count all the leaves.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all Leaves");
        return leaveRepository.count();
    }

    /**
     * Get one leave by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveDTO> findOne(Long id) {
        log.debug("Request to get Leave : {}", id);
        return leaveRepository.findById(id)
            .map(leaveMapper::toDto);
    }

    /**
     * Delete the leave by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Leave : {}", id);
        leaveRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<LeaveDTO> findByCreatorIsCurrentUser() {
        return leaveMapper.toDto(leaveRepository.findByCreatorIsCurrentUser());
    }

    /**
     * Update specified fields by leave
     */
    public LeaveDTO updateBySpecifiedFields(LeaveDTO changeLeaveDTO, Set<String> unchangedFields) {
        LeaveDTO leaveDTO = findOne(changeLeaveDTO.getId()).get();
        BeanUtil.copyProperties(changeLeaveDTO, leaveDTO, unchangedFields.toArray(new String[0]));
        leaveDTO = save(leaveDTO);
        return leaveDTO;
    }

    /**
     * Update specified field by leave
     */
    public LeaveDTO updateBySpecifiedField(LeaveDTO changeLeaveDTO, String fieldName) {
        LeaveDTO leaveDTO = findOne(changeLeaveDTO.getId()).get();
        BeanUtil.setFieldValue(leaveDTO, fieldName, BeanUtil.getFieldValue(changeLeaveDTO,fieldName));
        leaveDTO = save(leaveDTO);
        return leaveDTO;
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
