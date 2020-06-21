package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonTable} and its DTO {@link CommonTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, BusinessTypeMapper.class})
public interface CommonTableMapper extends EntityMapper<CommonTableDTO, CommonTable> {

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.login", target = "creatorLogin")
    @Mapping(source = "creator.imageUrl", target = "creatorImageUrl")
    @Mapping(source = "businessType.id", target = "businessTypeId")
    @Mapping(source = "businessType.name", target = "businessTypeName")
    CommonTableDTO toDto(CommonTable commonTable);

    @Mapping(target = "commonTableFields", ignore = true)
    @Mapping(target = "removeCommonTableFields", ignore = true)
    @Mapping(target = "relationships", ignore = true)
    @Mapping(target = "removeRelationships", ignore = true)
    @Mapping(source = "creatorId", target = "creator")
    @Mapping(source = "businessTypeId", target = "businessType")
    CommonTable toEntity(CommonTableDTO commonTableDTO);

    default CommonTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonTable commonTable = new CommonTable();
        commonTable.setId(id);
        return commonTable;
    }
}
