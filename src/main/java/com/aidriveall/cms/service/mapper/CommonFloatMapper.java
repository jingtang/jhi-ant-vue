package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonFloatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonFloat} and its DTO {@link CommonFloatDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonFloatMapper extends EntityMapper<CommonFloatDTO, CommonFloat> {



    default CommonFloat fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonFloat commonFloat = new CommonFloat();
        commonFloat.setId(id);
        return commonFloat;
    }
}
