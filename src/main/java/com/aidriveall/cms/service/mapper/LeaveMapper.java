package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.LeaveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Leave} and its DTO {@link LeaveDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface LeaveMapper extends EntityMapper<LeaveDTO, Leave> {

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.login", target = "creatorLogin")
    @Mapping(source = "creator.imageUrl", target = "creatorImageUrl")
    LeaveDTO toDto(Leave leave);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(source = "creatorId", target = "creator")
    Leave toEntity(LeaveDTO leaveDTO);

    default Leave fromId(Long id) {
        if (id == null) {
            return null;
        }
        Leave leave = new Leave();
        leave.setId(id);
        return leave;
    }
}
