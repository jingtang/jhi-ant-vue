package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonIntegerMapperTest {

    private CommonIntegerMapper commonIntegerMapper;

    @BeforeEach
    public void setUp() {
        commonIntegerMapper = new CommonIntegerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonIntegerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonIntegerMapper.fromId(null)).isNull();
    }
}
