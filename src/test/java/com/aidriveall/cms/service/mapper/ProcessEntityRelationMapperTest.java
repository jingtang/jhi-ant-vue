package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProcessEntityRelationMapperTest {

    private ProcessEntityRelationMapper processEntityRelationMapper;

    @BeforeEach
    public void setUp() {
        processEntityRelationMapper = new ProcessEntityRelationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(processEntityRelationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(processEntityRelationMapper.fromId(null)).isNull();
    }
}
