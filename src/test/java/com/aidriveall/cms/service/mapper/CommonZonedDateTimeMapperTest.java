package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonZonedDateTimeMapperTest {

    private CommonZonedDateTimeMapper commonZonedDateTimeMapper;

    @BeforeEach
    public void setUp() {
        commonZonedDateTimeMapper = new CommonZonedDateTimeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonZonedDateTimeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonZonedDateTimeMapper.fromId(null)).isNull();
    }
}
