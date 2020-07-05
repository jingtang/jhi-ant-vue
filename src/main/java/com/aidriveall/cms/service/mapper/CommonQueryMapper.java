package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonQueryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonQuery} and its DTO {@link CommonQueryDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CommonTableMapper.class})
public interface CommonQueryMapper extends EntityMapper<CommonQueryDTO, CommonQuery> {

    @Mapping(source = "modifier.id", target = "modifierId")
    @Mapping(source = "modifier.firstName", target = "modifierFirstName")
    @Mapping(source = "modifier.imageUrl", target = "modifierImageUrl")
    @Mapping(source = "commonTable.id", target = "commonTableId")
    @Mapping(source = "commonTable.name", target = "commonTableName")
    CommonQueryDTO toDto(CommonQuery commonQuery);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "removeItems", ignore = true)
    @Mapping(source = "modifierId", target = "modifier")
    @Mapping(source = "commonTableId", target = "commonTable")
    CommonQuery toEntity(CommonQueryDTO commonQueryDTO);

    default CommonQuery fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setId(id);
        return commonQuery;
    }
}
