package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UploadImageMapperTest {

    private UploadImageMapper uploadImageMapper;

    @BeforeEach
    public void setUp() {
        uploadImageMapper = new UploadImageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(uploadImageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uploadImageMapper.fromId(null)).isNull();
    }
}
