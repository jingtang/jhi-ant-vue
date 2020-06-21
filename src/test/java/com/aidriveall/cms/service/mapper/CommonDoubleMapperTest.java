package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonDoubleMapperTest {

    private CommonDoubleMapper commonDoubleMapper;

    @BeforeEach
    public void setUp() {
        commonDoubleMapper = new CommonDoubleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonDoubleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonDoubleMapper.fromId(null)).isNull();
    }
}
