package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonZonedDateTimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonZonedDateTime} and its DTO {@link CommonZonedDateTimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonZonedDateTimeMapper extends EntityMapper<CommonZonedDateTimeDTO, CommonZonedDateTime> {



    default CommonZonedDateTime fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonZonedDateTime commonZonedDateTime = new CommonZonedDateTime();
        commonZonedDateTime.setId(id);
        return commonZonedDateTime;
    }
}
