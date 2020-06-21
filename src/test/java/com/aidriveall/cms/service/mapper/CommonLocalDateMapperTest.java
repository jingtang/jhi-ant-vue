package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonLocalDateMapperTest {

    private CommonLocalDateMapper commonLocalDateMapper;

    @BeforeEach
    public void setUp() {
        commonLocalDateMapper = new CommonLocalDateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonLocalDateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonLocalDateMapper.fromId(null)).isNull();
    }
}
