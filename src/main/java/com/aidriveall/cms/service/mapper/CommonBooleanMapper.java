package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonBooleanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonBoolean} and its DTO {@link CommonBooleanDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonBooleanMapper extends EntityMapper<CommonBooleanDTO, CommonBoolean> {



    default CommonBoolean fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonBoolean commonBoolean = new CommonBoolean();
        commonBoolean.setId(id);
        return commonBoolean;
    }
}
