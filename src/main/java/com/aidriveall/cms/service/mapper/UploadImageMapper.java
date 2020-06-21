package com.aidriveall.cms.service.mapper;

import com.aidriveall.cms.domain.*;
import com.aidriveall.cms.service.dto.UploadImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UploadImage} and its DTO {@link UploadImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UploadImageMapper extends EntityMapper<UploadImageDTO, UploadImage> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.imageUrl", target = "userImageUrl")
    UploadImageDTO toDto(UploadImage uploadImage);

    @Mapping(source = "userId", target = "user")
    UploadImage toEntity(UploadImageDTO uploadImageDTO);

    default UploadImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        UploadImage uploadImage = new UploadImage();
        uploadImage.setId(id);
        return uploadImage;
    }
}
