package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonTableFieldMapperTest {

    private CommonTableFieldMapper commonTableFieldMapper;

    @BeforeEach
    public void setUp() {
        commonTableFieldMapper = new CommonTableFieldMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonTableFieldMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonTableFieldMapper.fromId(null)).isNull();
    }
}
