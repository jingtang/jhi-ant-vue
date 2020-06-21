package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.ViewPermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ViewPermission} and its DTO {@link ViewPermissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApiPermissionMapper.class})
public interface ViewPermissionMapper extends EntityMapper<ViewPermissionDTO, ViewPermission> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.text", target = "parentText")
    ViewPermissionDTO toDto(ViewPermission viewPermission);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(target = "removeApiPermissions", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "removeAuthorities", ignore = true)
    ViewPermission toEntity(ViewPermissionDTO viewPermissionDTO);

    default ViewPermission fromId(Long id) {
        if (id == null) {
            return null;
        }
        ViewPermission viewPermission = new ViewPermission();
        viewPermission.setId(id);
        return viewPermission;
    }
}
