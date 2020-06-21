package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonTableMapperTest {

    private CommonTableMapper commonTableMapper;

    @BeforeEach
    public void setUp() {
        commonTableMapper = new CommonTableMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonTableMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonTableMapper.fromId(null)).isNull();
    }
}
