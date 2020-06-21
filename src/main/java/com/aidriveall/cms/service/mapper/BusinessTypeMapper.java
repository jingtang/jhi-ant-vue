package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.BusinessTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessType} and its DTO {@link BusinessTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessTypeMapper extends EntityMapper<BusinessTypeDTO, BusinessType> {



    default BusinessType fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessType businessType = new BusinessType();
        businessType.setId(id);
        return businessType;
    }
}
