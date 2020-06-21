package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CompanyBusinessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyBusiness} and its DTO {@link CompanyBusinessDTO}.
 */
@Mapper(componentModel = "spring", uses = {BusinessTypeMapper.class, CompanyCustomerMapper.class})
public interface CompanyBusinessMapper extends EntityMapper<CompanyBusinessDTO, CompanyBusiness> {

    @Mapping(source = "businessType.id", target = "businessTypeId")
    @Mapping(source = "businessType.name", target = "businessTypeName")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    CompanyBusinessDTO toDto(CompanyBusiness companyBusiness);

    @Mapping(source = "businessTypeId", target = "businessType")
    @Mapping(source = "companyId", target = "company")
    CompanyBusiness toEntity(CompanyBusinessDTO companyBusinessDTO);

    default CompanyBusiness fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyBusiness companyBusiness = new CompanyBusiness();
        companyBusiness.setId(id);
        return companyBusiness;
    }
}
