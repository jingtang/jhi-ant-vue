package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.UploadFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UploadFile} and its DTO {@link UploadFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UploadFileMapper extends EntityMapper<UploadFileDTO, UploadFile> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.imageUrl", target = "userImageUrl")
    UploadFileDTO toDto(UploadFile uploadFile);

    @Mapping(source = "userId", target = "user")
    UploadFile toEntity(UploadFileDTO uploadFileDTO);

    default UploadFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        UploadFile uploadFile = new UploadFile();
        uploadFile.setId(id);
        return uploadFile;
    }
}
