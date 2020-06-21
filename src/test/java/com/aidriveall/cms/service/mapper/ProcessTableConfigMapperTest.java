package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcessTableConfigMapperTest {

    private ProcessTableConfigMapper processTableConfigMapper;

    @BeforeEach
    public void setUp() {
        processTableConfigMapper = new ProcessTableConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(processTableConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(processTableConfigMapper.fromId(null)).isNull();
    }
}
