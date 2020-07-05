package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonQueryItemMapperTest {

    private CommonQueryItemMapper commonQueryItemMapper;

    @BeforeEach
    public void setUp() {
        commonQueryItemMapper = new CommonQueryItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonQueryItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonQueryItemMapper.fromId(null)).isNull();
    }
}
