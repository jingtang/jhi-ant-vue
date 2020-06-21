package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.ProcessTableConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessTableConfig} and its DTO {@link ProcessTableConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommonTableMapper.class, UserMapper.class})
public interface ProcessTableConfigMapper extends EntityMapper<ProcessTableConfigDTO, ProcessTableConfig> {

    @Mapping(source = "commonTable.id", target = "commonTableId")
    @Mapping(source = "commonTable.name", target = "commonTableName")
    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.login", target = "creatorLogin")
    @Mapping(source = "creator.imageUrl", target = "creatorImageUrl")
    ProcessTableConfigDTO toDto(ProcessTableConfig processTableConfig);

    @Mapping(source = "commonTableId", target = "commonTable")
    @Mapping(source = "creatorId", target = "creator")
    ProcessTableConfig toEntity(ProcessTableConfigDTO processTableConfigDTO);

    default ProcessTableConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProcessTableConfig processTableConfig = new ProcessTableConfig();
        processTableConfig.setId(id);
        return processTableConfig;
    }
}
