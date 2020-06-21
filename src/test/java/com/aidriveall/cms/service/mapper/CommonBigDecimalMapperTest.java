package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonBigDecimalMapperTest {

    private CommonBigDecimalMapper commonBigDecimalMapper;

    @BeforeEach
    public void setUp() {
        commonBigDecimalMapper = new CommonBigDecimalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonBigDecimalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonBigDecimalMapper.fromId(null)).isNull();
    }
}
