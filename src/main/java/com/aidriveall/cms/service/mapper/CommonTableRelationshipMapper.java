package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonTableRelationshipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonTableRelationship} and its DTO {@link CommonTableRelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommonTableMapper.class, DataDictionaryMapper.class})
public interface CommonTableRelationshipMapper extends EntityMapper<CommonTableRelationshipDTO, CommonTableRelationship> {

    @Mapping(source = "relationEntity.id", target = "relationEntityId")
    @Mapping(source = "relationEntity.name", target = "relationEntityName")
    @Mapping(source = "dataDictionaryNode.id", target = "dataDictionaryNodeId")
    @Mapping(source = "dataDictionaryNode.name", target = "dataDictionaryNodeName")
    @Mapping(source = "commonTable.id", target = "commonTableId")
    @Mapping(source = "commonTable.name", target = "commonTableName")
    CommonTableRelationshipDTO toDto(CommonTableRelationship commonTableRelationship);

    @Mapping(source = "relationEntityId", target = "relationEntity")
    @Mapping(source = "dataDictionaryNodeId", target = "dataDictionaryNode")
    @Mapping(source = "commonTableId", target = "commonTable")
    CommonTableRelationship toEntity(CommonTableRelationshipDTO commonTableRelationshipDTO);

    default CommonTableRelationship fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonTableRelationship commonTableRelationship = new CommonTableRelationship();
        commonTableRelationship.setId(id);
        return commonTableRelationship;
    }
}
