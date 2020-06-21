package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonDoubleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonDouble} and its DTO {@link CommonDoubleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonDoubleMapper extends EntityMapper<CommonDoubleDTO, CommonDouble> {



    default CommonDouble fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonDouble commonDouble = new CommonDouble();
        commonDouble.setId(id);
        return commonDouble;
    }
}
