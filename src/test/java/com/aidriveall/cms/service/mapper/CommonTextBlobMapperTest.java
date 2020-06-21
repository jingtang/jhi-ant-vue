package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonTextBlobMapperTest {

    private CommonTextBlobMapper commonTextBlobMapper;

    @BeforeEach
    public void setUp() {
        commonTextBlobMapper = new CommonTextBlobMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonTextBlobMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonTextBlobMapper.fromId(null)).isNull();
    }
}
