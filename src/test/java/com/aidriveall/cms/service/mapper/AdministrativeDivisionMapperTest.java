package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdministrativeDivisionMapperTest {

    private AdministrativeDivisionMapper administrativeDivisionMapper;

    @BeforeEach
    public void setUp() {
        administrativeDivisionMapper = new AdministrativeDivisionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(administrativeDivisionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(administrativeDivisionMapper.fromId(null)).isNull();
    }
}
