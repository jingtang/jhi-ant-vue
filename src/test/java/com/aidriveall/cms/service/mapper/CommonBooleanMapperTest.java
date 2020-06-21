package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonBooleanMapperTest {

    private CommonBooleanMapper commonBooleanMapper;

    @BeforeEach
    public void setUp() {
        commonBooleanMapper = new CommonBooleanMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonBooleanMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonBooleanMapper.fromId(null)).isNull();
    }
}
