package com.aidriveall.cms.service;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.repository.CommonExtDataRepository;
import com.aidriveall.cms.repository.CommonTableFieldRepository;
import com.aidriveall.cms.service.dto.CommonExtDataDTO;
import com.aidriveall.cms.service.mapper.CommonExtDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link CommonExtData}.
 */
@Service
@Transactional
public class CommonExtDataService {

    private final Logger log = LoggerFactory.getLogger(CommonExtDataService.class);

    private final CommonExtDataRepository commonExtDataRepository;

    private final CacheManager cacheManager;

    private final CommonExtDataMapper commonExtDataMapper;

    private final CommonTableFieldRepository commonTableFieldRepository;

    public CommonExtDataService(CommonExtDataRepository commonExtDataRepository, CacheManager cacheManager, CommonExtDataMapper commonExtDataMapper, CommonTableFieldRepository commonTableFieldRepository) {
        this.commonExtDataRepository = commonExtDataRepository;
        this.cacheManager = cacheManager;
        this.commonExtDataMapper = commonExtDataMapper;
        this.commonTableFieldRepository = commonTableFieldRepository;
    }

    public Owner getExtData(Owner source) {
        Map<String, Object> extData = source.getExtData();
        // 如果支持多模型就不能用getEntityName来获得类型的。要通过ContentModelId来获得。要考虑。如果不影响。save有影响。
        commonExtDataRepository.findByOwnerIdAndOwnerType(source.getId(), source.getEntityName()).forEach(
            commonExtData -> {
                switch (commonExtData.getCommonFieldType()) {
                    case "CommonBoolean":
                        CommonBoolean commonBoolean = (CommonBoolean) commonExtData.getValue();
                        extData.put(commonExtData.getFieldName(), commonBoolean.isValue());
                        break;
                    case "CommonString":
                        CommonString commonString = (CommonString) commonExtData.getValue();
                        extData.put(commonExtData.getFieldName(), commonString.getValue());
                        break;
                    case "CommonLong":
                        CommonLong commonLong = (CommonLong) commonExtData.getValue();
                        extData.put(commonExtData.getFieldName(), commonLong.getValue());
                        break;
                    case "CommonInteger":
                        CommonInteger commonInteger = (CommonInteger) commonExtData.getValue();
                        extData.put(commonExtData.getFieldName(), commonInteger.getValue());
                        break;
                    case "CommonZonedDateTime":
                        CommonZonedDateTime commonZonedDateTime = (CommonZonedDateTime) commonExtData.getValue();
                        extData.put(commonExtData.getFieldName(), commonZonedDateTime.getValue());
                        break;
                    case "CommonFloat":
                        CommonFloat commonFloat = (CommonFloat) commonExtData.getValue();
                        extData.put(commonExtData.getFieldName(), commonFloat.getValue());
                        break;
                    case "CommonDouble":
                        CommonDouble commonDouble = (CommonDouble) commonExtData.getValue();
                        extData.put(commonExtData.getFieldName(), commonDouble.getValue());
                        break;
                }
            });
        return  source;
    }

    public void saveExtData(Owner source) {
        Map<String, Object> extData = source.getExtData();
        Object contentModel = BeanUtil.getFieldValue(source, "contentModel");
        CommonTable multiModel = null;
        if (contentModel instanceof CommonTable) {
            multiModel = (CommonTable) contentModel;
        }
        // todo 如果支持多模型就不能用getEntityName来获得类型的,这只是原始类型。要通过contentModel来获得。。save有影响。
        CommonTable finalMultiModel = multiModel;
        extData.forEach((key, value) -> {
            CommonTableField field;
            if (finalMultiModel != null) {
                field = commonTableFieldRepository.findOneByCommonTableIdAndEntityFieldName(finalMultiModel.getId(), key);
            } else {
                field = commonTableFieldRepository.findOneByCommonTableEntityNameAndEntityFieldName(source.getEntityName(), key);
            }
            if (field != null) {
                switch (field.getType()) {
                    case STRING:
                        saveString(source,key,value);
                        break;
                    case ZONED_DATE_TIME:
                        saveZonedDataTime(source,key,value);
                        break;
                    case DOUBLE:
                        saveDouble(source,key,value);
                        break;
                    case LONG:
                        saveLong(source,key,value);
                        break;
                    case FLOAT:
                        saveFloat(source,key,value);
                        break;
                    case INTEGER:
                        saveInteger(source,key,value);
                        break;
                    case BOOLEAN:
                        saveBoolean(source,key,value);
                }
            }
        });
    }

    /**
     * 通过source中包含的modelId来获得相应的commonTable及commonField.
     * @param source 源数据
     * @param modelId 模型Id
     */
    public void saveExtDataByModelId(Owner source, Long modelId) {
        Map<String, Object> extData = source.getExtData();
        extData.forEach((key, value) -> {
            CommonTableField field = commonTableFieldRepository.findOneByCommonTableIdAndEntityFieldName(modelId, key);
            if (field != null) {
                switch (field.getType()) {
                    case STRING:
                        saveString(source,key,value);
                        break;
                    case ZONED_DATE_TIME:
                        saveZonedDataTime(source,key,value);
                        break;
                    case DOUBLE:
                        saveDouble(source,key,value);
                        break;
                    case LONG:
                        saveLong(source,key,value);
                        break;
                    case FLOAT:
                        saveFloat(source,key,value);
                        break;
                    case INTEGER:
                        saveInteger(source,key,value);
                        break;
                    case BOOLEAN:
                        saveBoolean(source,key,value);
                }
            }
        });
    }

    private void saveString(Owner source, String key, Object value) {
        CommonExtData commonExtData = commonExtDataRepository.findByOwnerIdAndOwnerTypeAndFieldName(source.getId(),source.getEntityName(), key).orElse(new CommonExtData());
        if (commonExtData.getId() != null) {
            boolean update = false;
            CommonString commonString = (CommonString) commonExtData.getValue();
            if (commonString == null ) {
                commonString = new CommonString().value((String) value);
                update = true;
            } else if (!commonString.getValue().equals(value)) {
                update = true;
                commonString.setValue((String) value);
            }
            if (update) {
                commonExtData.setValue(commonString);
                commonExtDataRepository.save(commonExtData);
            }
        } else {
            commonExtData.setValue(new CommonString().value((String) value));
            commonExtData.setFieldName(key);
            commonExtData.setOwner(source);
            commonExtDataRepository.save(commonExtData);
        }
    }

    private void saveBoolean(Owner source, String key, Object value) {
        CommonExtData commonExtData = commonExtDataRepository.findByOwnerIdAndOwnerTypeAndFieldName(source.getId(),source.getEntityName(), key).orElse(new CommonExtData());
        if (commonExtData.getId() != null) {
            boolean update = false;
            CommonBoolean commonBoolean = (CommonBoolean) commonExtData.getValue();
            if (commonBoolean == null ) {
                commonBoolean = new CommonBoolean().value((Boolean) value);
                update = true;
            } else if (!commonBoolean.isValue().equals(value)) {
                update = true;
                commonBoolean.setValue((Boolean) value);
            }
            if (update) {
                commonExtData.setValue(commonBoolean);
                commonExtDataRepository.save(commonExtData);
            }
        } else {
            commonExtData.setValue(new CommonBoolean().value((Boolean) value));
            commonExtData.setOwner(source);
            commonExtData.setFieldName(key);
            commonExtDataRepository.save(commonExtData);
        }
    }

    private void saveZonedDataTime(Owner source, String key, Object value) {
        CommonExtData commonExtData = commonExtDataRepository.findByOwnerIdAndOwnerTypeAndFieldName(source.getId(),source.getEntityName(), key).orElse(new CommonExtData());
        if (commonExtData.getId() != null) {
            boolean update = false;
            CommonZonedDateTime commonZonedDateTime = (CommonZonedDateTime) commonExtData.getValue();
            if (commonZonedDateTime == null ) {
                commonZonedDateTime = new CommonZonedDateTime().value((ZonedDateTime) value);
                update = true;
            } else if (!commonZonedDateTime.getValue().equals(value)) {
                update = true;
                commonZonedDateTime.setValue((ZonedDateTime) value);
            }
            if (update) {
                commonExtData.setValue(commonZonedDateTime);
                commonExtDataRepository.save(commonExtData);
            }
        } else {
            commonExtData.setValue(new CommonZonedDateTime().value((ZonedDateTime) value));
            commonExtData.setOwner(source);
            commonExtData.setFieldName(key);
            commonExtDataRepository.save(commonExtData);
        }
    }

    private void saveDouble(Owner source, String key, Object value) {
        CommonExtData commonExtData = commonExtDataRepository.findByOwnerIdAndOwnerTypeAndFieldName(source.getId(),source.getEntityName(), key).orElse(new CommonExtData());
        if (commonExtData.getId() != null) {
            boolean update = false;
            CommonDouble commonDouble = (CommonDouble) commonExtData.getValue();
            if (commonDouble == null ) {
                commonDouble = new CommonDouble().value((Double) value);
                update = true;
            } else if (!commonDouble.getValue().equals(value)) {
                update = true;
                commonDouble.setValue((Double) value);
            }
            if (update) {
                commonExtData.setValue(commonDouble);
                commonExtDataRepository.save(commonExtData);
            }
        } else {
            commonExtData.setValue(new CommonDouble().value((Double) value));
            commonExtData.setOwner(source);
            commonExtData.setFieldName(key);
            commonExtDataRepository.save(commonExtData);
        }
    }

    private void saveLong(Owner source, String key, Object value) {
        CommonExtData commonExtData = commonExtDataRepository.findByOwnerIdAndOwnerTypeAndFieldName(source.getId(),source.getEntityName(), key).orElse(new CommonExtData());
        if (commonExtData.getId() != null) {
            boolean update = false;
            CommonLong commonLong = (CommonLong) commonExtData.getValue();
            if (commonLong == null ) {
                commonLong = new CommonLong().value((Long) value);
                update = true;
            } else if (!commonLong.getValue().equals(value)) {
                update = true;
                commonLong.setValue((Long) value);
            }
            if (update) {
                commonExtData.setValue(commonLong);
                commonExtDataRepository.save(commonExtData);
            }
        } else {
            commonExtData.setValue(new CommonLong().value((Long) value));
            commonExtData.setOwner(source);
            commonExtData.setFieldName(key);
            commonExtDataRepository.save(commonExtData);
        }
    }

    private void saveFloat(Owner source, String key, Object value) {
        CommonExtData commonExtData = commonExtDataRepository.findByOwnerIdAndOwnerTypeAndFieldName(source.getId(),source.getEntityName(), key).orElse(new CommonExtData());
        if (commonExtData.getId() != null) {
            boolean update = false;
            CommonFloat commonFloat = (CommonFloat) commonExtData.getValue();
            if (commonFloat == null ) {
                commonFloat = new CommonFloat().value((Float) value);
                update = true;
            } else if (!commonFloat.getValue().equals(value)) {
                update = true;
                commonFloat.setValue((Float) value);
            }
            if (update) {
                commonExtData.setValue(commonFloat);
                commonExtDataRepository.save(commonExtData);
            }
        } else {
            commonExtData.setValue(new CommonFloat().value((Float) value));
            commonExtData.setOwner(source);
            commonExtData.setFieldName(key);
            commonExtDataRepository.save(commonExtData);
        }
    }

    private void saveInteger(Owner source, String key, Object value) {
        CommonExtData commonExtData = commonExtDataRepository.findByOwnerIdAndOwnerTypeAndFieldName(source.getId(),source.getEntityName(), key).orElse(new CommonExtData());
        if (commonExtData.getId() != null) {
            boolean update = false;
            CommonInteger commonInteger = (CommonInteger) commonExtData.getValue();
            if (commonInteger == null ) {
                commonInteger = new CommonInteger().value((Integer) value);
                update = true;
            } else if (!commonInteger.getValue().equals(value)) {
                update = true;
                commonInteger.setValue((Integer) value);
            }
            if (update) {
                commonExtData.setValue(commonInteger);
                commonExtDataRepository.save(commonExtData);
            }
        } else {
            commonExtData.setValue(new CommonInteger().value((Integer) value));
            commonExtData.setOwner(source);
            commonExtData.setFieldName(key);
            commonExtDataRepository.save(commonExtData);
        }
    }

    /**
     * Save a commonExtData.
     *
     * @param commonExtDataDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonExtDataDTO save(CommonExtDataDTO commonExtDataDTO) {
        log.debug("Request to save CommonExtData : {}", commonExtDataDTO);
        CommonExtData commonExtData = commonExtDataMapper.toEntity(commonExtDataDTO);
        commonExtData = commonExtDataRepository.save(commonExtData);
        return commonExtDataMapper.toDto(commonExtData);
    }

    /**
     * Get all the commonExtData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonExtDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonExtData");
        return commonExtDataRepository.findAll(pageable)
            .map(commonExtDataMapper::toDto);
    }

    /**
    * count all the commonExtData.
    *
    * @return the count of entities
    * by wangxin
    */
    @Transactional(readOnly = true)
    public long count() {
        log.debug("Request to count all CommonExtData");
        return commonExtDataRepository.count();
    }

    /**
     * Get one commonExtData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonExtDataDTO> findOne(Long id) {
        log.debug("Request to get CommonExtData : {}", id);
        return commonExtDataRepository.findById(id)
            .map(commonExtDataMapper::toDto);
    }

    /**
     * Delete the commonExtData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommonExtData : {}", id);
        commonExtDataRepository.deleteById(id);
    }

    /**
     * Update specified fields by commonExtData
     */
    public CommonExtDataDTO updateBySpecifiedFields(CommonExtDataDTO changeCommonExtDataDTO, Set<String> unchangedFields) {
        CommonExtDataDTO commonExtDataDTO = findOne(changeCommonExtDataDTO.getId()).get();
        BeanUtil.copyProperties(changeCommonExtDataDTO, commonExtDataDTO, unchangedFields.toArray(new String[0]));
        commonExtDataDTO = save(commonExtDataDTO);
        return commonExtDataDTO;
    }

    /**
     * Update specified field by commonExtData
     */
    public CommonExtDataDTO updateBySpecifiedField(CommonExtDataDTO changeCommonExtDataDTO, String fieldName) {
        CommonExtDataDTO commonExtDataDTO = findOne(changeCommonExtDataDTO.getId()).get();
        BeanUtil.setFieldValue(commonExtDataDTO, fieldName, BeanUtil.getFieldValue(changeCommonExtDataDTO,fieldName));
        commonExtDataDTO = save(commonExtDataDTO);
        return commonExtDataDTO;
    }

    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
