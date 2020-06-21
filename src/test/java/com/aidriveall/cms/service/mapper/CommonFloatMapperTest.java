package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonFloatMapperTest {

    private CommonFloatMapper commonFloatMapper;

    @BeforeEach
    public void setUp() {
        commonFloatMapper = new CommonFloatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonFloatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonFloatMapper.fromId(null)).isNull();
    }
}
