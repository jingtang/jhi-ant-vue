package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.GpsInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GpsInfo} and its DTO {@link GpsInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GpsInfoMapper extends EntityMapper<GpsInfoDTO, GpsInfo> {



    default GpsInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        GpsInfo gpsInfo = new GpsInfo();
        gpsInfo.setId(id);
        return gpsInfo;
    }
}
