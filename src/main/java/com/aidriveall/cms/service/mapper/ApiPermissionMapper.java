package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.ApiPermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiPermission} and its DTO {@link ApiPermissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiPermissionMapper extends EntityMapper<ApiPermissionDTO, ApiPermission> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    ApiPermissionDTO toDto(ApiPermission apiPermission);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "viewPermissions", ignore = true)
    @Mapping(target = "removeViewPermissions", ignore = true)
    ApiPermission toEntity(ApiPermissionDTO apiPermissionDTO);

    default ApiPermission fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApiPermission apiPermission = new ApiPermission();
        apiPermission.setId(id);
        return apiPermission;
    }
}
