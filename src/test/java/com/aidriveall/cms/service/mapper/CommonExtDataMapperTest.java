package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonExtDataMapperTest {

    private CommonExtDataMapper commonExtDataMapper;

    @BeforeEach
    public void setUp() {
        commonExtDataMapper = new CommonExtDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonExtDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonExtDataMapper.fromId(null)).isNull();
    }
}
