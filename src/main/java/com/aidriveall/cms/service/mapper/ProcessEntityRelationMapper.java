package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.ProcessEntityRelationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessEntityRelation} and its DTO {@link ProcessEntityRelationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessEntityRelationMapper extends EntityMapper<ProcessEntityRelationDTO, ProcessEntityRelation> {



    default ProcessEntityRelation fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProcessEntityRelation processEntityRelation = new ProcessEntityRelation();
        processEntityRelation.setId(id);
        return processEntityRelation;
    }
}
