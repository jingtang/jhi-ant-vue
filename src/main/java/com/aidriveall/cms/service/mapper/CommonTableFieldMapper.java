package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonTableFieldDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonTableField} and its DTO {@link CommonTableFieldDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommonTableMapper.class})
public interface CommonTableFieldMapper extends EntityMapper<CommonTableFieldDTO, CommonTableField> {

    @Mapping(source = "commonTable.id", target = "commonTableId")
    @Mapping(source = "commonTable.name", target = "commonTableName")
    CommonTableFieldDTO toDto(CommonTableField commonTableField);

    @Mapping(source = "commonTableId", target = "commonTable")
    CommonTableField toEntity(CommonTableFieldDTO commonTableFieldDTO);

    default CommonTableField fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonTableField commonTableField = new CommonTableField();
        commonTableField.setId(id);
        return commonTableField;
    }
}
