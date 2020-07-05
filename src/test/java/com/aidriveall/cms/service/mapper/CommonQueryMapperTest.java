package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonQueryMapperTest {

    private CommonQueryMapper commonQueryMapper;

    @BeforeEach
    public void setUp() {
        commonQueryMapper = new CommonQueryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commonQueryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commonQueryMapper.fromId(null)).isNull();
    }
}
