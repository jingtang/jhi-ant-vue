package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.UploadImage;
import com.aidriveall.cms.repository.UploadImageRepository;
import com.aidriveall.cms.repository.UserRepository;
import com.aidriveall.cms.security.SecurityUtils;
import com.aidriveall.cms.service.dto.UploadImageDTO;
import com.aidriveall.cms.service.mapper.UploadImageMapper;
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


    import org.apache.commons.io.FilenameUtils;
    import org.apache.commons.io.FileUtils;
    import java.util.UUID;
    import java.io.IOException;
    import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
    import com.aidriveall.cms.security.SecurityUtils;
    import com.aidriveall.cms.config.ApplicationProperties;
    import com.aidriveall.cms.config.Constants;
    import com.aidriveall.cms.repository.UserRepository;
    import java.time.ZonedDateTime;
    import java.time.format.DateTimeFormatter;
    import java.io.File;
// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link UploadImage}.
 */
@Service
@Transactional
public class UploadImageService {

    private final Logger log = LoggerFactory.getLogger(UploadImageService.class);
    private final List<String> relationCacheNames =
        Arrays.asList(
        com.aidriveall.cms.domain.User.class.getName() + ".uploadImage"
        );

    private final UploadImageRepository uploadImageRepository;

    private final CacheManager cacheManager;

    private final UploadImageMapper uploadImageMapper;

    private final ApplicationProperties applicationProperties;

    private final UserRepository userRepository;

    public UploadImageService(UploadImageRepository uploadImageRepository, CacheManager cacheManager, UploadImageMapper uploadImageMapper, ApplicationProperties applicationProperties, UserRepository userRepository) {
        this.uploadImageRepository = uploadImageRepository;
        this.cacheManager = cacheManager;
        this.uploadImageMapper = uploadImageMapper;
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
    }

    /**
     * Save a uploadImage.
     *
     * @param uploadImageDTO the entity to save.
     * @return the persisted entity.
     */
    public UploadImageDTO save(UploadImageDTO uploadImageDTO) {
        log.debug("Request to save UploadImage : {}", uploadImageDTO);
        UploadImage uploadImage = uploadImageMapper.toEntity(uploadImageDTO);
        uploadImage = uploadImageRepository.save(uploadImage);
        return uploadImageMapper.toDto(uploadImage);
    }

    /**
     * Get all the uploadImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UploadImages");
        return uploadImageRepository.findAll(pageable)
            .map(uploadImageMapper::toDto);
    }

    /**
    * count all the uploadImages.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all UploadImages");
        return uploadImageRepository.count();
    }

    /**
     * Get one uploadImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UploadImageDTO> findOne(Long id) {
        log.debug("Request to get UploadImage : {}", id);
        return uploadImageRepository.findById(id)
            .map(uploadImageMapper::toDto);
    }

    /**
     * Delete the uploadImage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UploadImage : {}", id);
        uploadImageRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<UploadImageDTO> findByUserIsCurrentUser() {
        return uploadImageMapper.toDto(uploadImageRepository.findByUserIsCurrentUser());
    }

    /**
     * Update specified fields by uploadImage
     */
    public UploadImageDTO updateBySpecifiedFields(UploadImageDTO changeUploadImageDTO, Set<String> unchangedFields) {
        UploadImageDTO uploadImageDTO = findOne(changeUploadImageDTO.getId()).get();
        BeanUtil.copyProperties(changeUploadImageDTO, uploadImageDTO, unchangedFields.toArray(new String[0]));
        uploadImageDTO = save(uploadImageDTO);
        return uploadImageDTO;
    }

    /**
     * Update specified field by uploadImage
     */
    public UploadImageDTO updateBySpecifiedField(UploadImageDTO changeUploadImageDTO, String fieldName) {
        UploadImageDTO uploadImageDTO = findOne(changeUploadImageDTO.getId()).get();
        BeanUtil.setFieldValue(uploadImageDTO, fieldName, BeanUtil.getFieldValue(changeUploadImageDTO,fieldName));
        uploadImageDTO = save(uploadImageDTO);
        return uploadImageDTO;
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
