package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.DataDictionaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataDictionary} and its DTO {@link DataDictionaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataDictionaryMapper extends EntityMapper<DataDictionaryDTO, DataDictionary> {

    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    DataDictionaryDTO toDto(DataDictionary dataDictionary);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    DataDictionary toEntity(DataDictionaryDTO dataDictionaryDTO);

    default DataDictionary fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setId(id);
        return dataDictionary;
    }
}
