package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonLongMapperTest {

    private CommonLongMapper commonLongMapper;

    @BeforeEach
    public void setUp() {
        commonLongMapper = new CommonLongMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonLongMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonLongMapper.fromId(null)).isNull();
    }
}
