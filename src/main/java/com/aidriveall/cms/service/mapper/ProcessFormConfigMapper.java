package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.ProcessFormConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessFormConfig} and its DTO {@link ProcessFormConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessFormConfigMapper extends EntityMapper<ProcessFormConfigDTO, ProcessFormConfig> {



    default ProcessFormConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProcessFormConfig processFormConfig = new ProcessFormConfig();
        processFormConfig.setId(id);
        return processFormConfig;
    }
}
