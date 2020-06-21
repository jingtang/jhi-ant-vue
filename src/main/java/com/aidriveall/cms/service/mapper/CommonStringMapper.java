package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonStringDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonString} and its DTO {@link CommonStringDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonStringMapper extends EntityMapper<CommonStringDTO, CommonString> {



    default CommonString fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonString commonString = new CommonString();
        commonString.setId(id);
        return commonString;
    }
}
