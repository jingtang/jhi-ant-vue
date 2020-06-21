package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcessFormConfigMapperTest {

    private ProcessFormConfigMapper processFormConfigMapper;

    @BeforeEach
    public void setUp() {
        processFormConfigMapper = new ProcessFormConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(processFormConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(processFormConfigMapper.fromId(null)).isNull();
    }
}
