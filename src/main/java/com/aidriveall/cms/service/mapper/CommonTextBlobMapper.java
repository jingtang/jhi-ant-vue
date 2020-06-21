package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonTextBlobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonTextBlob} and its DTO {@link CommonTextBlobDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommonTextBlobMapper extends EntityMapper<CommonTextBlobDTO, CommonTextBlob> {



    default CommonTextBlob fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonTextBlob commonTextBlob = new CommonTextBlob();
        commonTextBlob.setId(id);
        return commonTextBlob;
    }
}
