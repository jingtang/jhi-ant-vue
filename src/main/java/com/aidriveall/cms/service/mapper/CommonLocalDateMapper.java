package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonLocalDateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonLocalDate} and its DTO {@link CommonLocalDateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonLocalDateMapper extends EntityMapper<CommonLocalDateDTO, CommonLocalDate> {



    default CommonLocalDate fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonLocalDate commonLocalDate = new CommonLocalDate();
        commonLocalDate.setId(id);
        return commonLocalDate;
    }
}
