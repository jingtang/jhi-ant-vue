package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.UReportFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UReportFile} and its DTO {@link UReportFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {CommonTableMapper.class})
public interface UReportFileMapper extends EntityMapper<UReportFileDTO, UReportFile> {

    @Mapping(source = "commonTable.id", target = "commonTableId")
    @Mapping(source = "commonTable.name", target = "commonTableName")
    UReportFileDTO toDto(UReportFile uReportFile);

    @Mapping(source = "commonTableId", target = "commonTable")
    UReportFile toEntity(UReportFileDTO uReportFileDTO);

    default UReportFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        UReportFile uReportFile = new UReportFile();
        uReportFile.setId(id);
        return uReportFile;
    }
}
