package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.CommonQueryItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonQueryItem} and its DTO {@link CommonQueryItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommonQueryMapper.class})
public interface CommonQueryItemMapper extends EntityMapper<CommonQueryItemDTO, CommonQueryItem> {

    @Mapping(source = "query.id", target = "queryId")
    @Mapping(source = "query.name", target = "queryName")
    CommonQueryItemDTO toDto(CommonQueryItem commonQueryItem);

    @Mapping(source = "queryId", target = "query")
    CommonQueryItem toEntity(CommonQueryItemDTO commonQueryItemDTO);

    default CommonQueryItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommonQueryItem commonQueryItem = new CommonQueryItem();
        commonQueryItem.setId(id);
        return commonQueryItem;
    }
}
