package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonBigDecimalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonBigDecimal} and its DTO {@link CommonBigDecimalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonBigDecimalMapper extends EntityMapper<CommonBigDecimalDTO, CommonBigDecimal> {



    default CommonBigDecimal fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonBigDecimal commonBigDecimal = new CommonBigDecimal();
        commonBigDecimal.setId(id);
        return commonBigDecimal;
    }
}
