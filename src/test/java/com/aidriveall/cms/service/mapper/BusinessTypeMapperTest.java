package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BusinessTypeMapperTest {

    private BusinessTypeMapper businessTypeMapper;

    @BeforeEach
    public void setUp() {
        businessTypeMapper = new BusinessTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(businessTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(businessTypeMapper.fromId(null)).isNull();
    }
}
