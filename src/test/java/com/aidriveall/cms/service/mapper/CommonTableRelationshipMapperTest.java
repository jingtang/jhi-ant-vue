package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonTableRelationshipMapperTest {

    private CommonTableRelationshipMapper commonTableRelationshipMapper;

    @BeforeEach
    public void setUp() {
        commonTableRelationshipMapper = new CommonTableRelationshipMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonTableRelationshipMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonTableRelationshipMapper.fromId(null)).isNull();
    }
}
