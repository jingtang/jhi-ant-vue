package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonIntegerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonInteger} and its DTO {@link CommonIntegerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonIntegerMapper extends EntityMapper<CommonIntegerDTO, CommonInteger> {



    default CommonInteger fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonInteger commonInteger = new CommonInteger();
        commonInteger.setId(id);
        return commonInteger;
    }
}
