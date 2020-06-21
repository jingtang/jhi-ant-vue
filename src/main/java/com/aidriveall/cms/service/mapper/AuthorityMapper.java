package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.AuthorityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Authority} and its DTO {@link AuthorityDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ViewPermissionMapper.class})
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, Authority> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    AuthorityDTO toDto(Authority authority);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    @Mapping(target = "removeViewPermission", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    Authority toEntity(AuthorityDTO authorityDTO);

    default Authority fromId(Long id) {
        if (id == null) {
            return null;
        }
        Authority authority = new Authority();
        authority.setId(id);
        return authority;
    }
}
