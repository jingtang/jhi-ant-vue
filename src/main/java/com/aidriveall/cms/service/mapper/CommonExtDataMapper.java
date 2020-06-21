package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonExtDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonExtData} and its DTO {@link CommonExtDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonExtDataMapper extends EntityMapper<CommonExtDataDTO, CommonExtData> {



    default CommonExtData fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonExtData commonExtData = new CommonExtData();
        commonExtData.setId(id);
        return commonExtData;
    }
}
