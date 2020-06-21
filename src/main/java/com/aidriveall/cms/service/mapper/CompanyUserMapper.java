package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CompanyUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyUser} and its DTO {@link CompanyUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompanyCustomerMapper.class})
public interface CompanyUserMapper extends EntityMapper<CompanyUserDTO, CompanyUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.imageUrl", target = "userImageUrl")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    CompanyUserDTO toDto(CompanyUser companyUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "companyId", target = "company")
    CompanyUser toEntity(CompanyUserDTO companyUserDTO);

    default CompanyUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyUser companyUser = new CompanyUser();
        companyUser.setId(id);
        return companyUser;
    }
}
