package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonStringMapperTest {

    private CommonStringMapper commonStringMapper;

    @BeforeEach
    public void setUp() {
        commonStringMapper = new CommonStringMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonStringMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonStringMapper.fromId(null)).isNull();
    }
}
