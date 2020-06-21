package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiPermissionMapperTest {

    private ApiPermissionMapper apiPermissionMapper;

    @BeforeEach
    public void setUp() {
        apiPermissionMapper = new ApiPermissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(apiPermissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(apiPermissionMapper.fromId(null)).isNull();
    }
}
