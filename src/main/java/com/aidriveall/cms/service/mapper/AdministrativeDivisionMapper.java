package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.AdministrativeDivisionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdministrativeDivision} and its DTO {@link AdministrativeDivisionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdministrativeDivisionMapper extends EntityMapper<AdministrativeDivisionDTO, AdministrativeDivision> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    AdministrativeDivisionDTO toDto(AdministrativeDivision administrativeDivision);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    AdministrativeDivision toEntity(AdministrativeDivisionDTO administrativeDivisionDTO);

    default AdministrativeDivision fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdministrativeDivision administrativeDivision = new AdministrativeDivision();
        administrativeDivision.setId(id);
        return administrativeDivision;
    }
}
