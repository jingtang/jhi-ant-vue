package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonLongDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonLong} and its DTO {@link CommonLongDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonLongMapper extends EntityMapper<CommonLongDTO, CommonLong> {



    default CommonLong fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonLong commonLong = new CommonLong();
        commonLong.setId(id);
        return commonLong;
    }
}
