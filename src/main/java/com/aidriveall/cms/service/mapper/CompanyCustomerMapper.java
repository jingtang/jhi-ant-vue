package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CompanyCustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyCustomer} and its DTO {@link CompanyCustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyCustomerMapper extends EntityMapper<CompanyCustomerDTO, CompanyCustomer> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    CompanyCustomerDTO toDto(CompanyCustomer companyCustomer);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(target = "companyUsers", ignore = true)
    @Mapping(target = "removeCompanyUsers", ignore = true)
    @Mapping(target = "companyBusinesses", ignore = true)
    @Mapping(target = "removeCompanyBusinesses", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    CompanyCustomer toEntity(CompanyCustomerDTO companyCustomerDTO);

    default CompanyCustomer fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyCustomer companyCustomer = new CompanyCustomer();
        companyCustomer.setId(id);
        return companyCustomer;
    }
}
