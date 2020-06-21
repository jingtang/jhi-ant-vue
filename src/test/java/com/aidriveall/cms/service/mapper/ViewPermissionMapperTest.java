package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ViewPermissionMapperTest {

    private ViewPermissionMapper viewPermissionMapper;

    @BeforeEach
    public void setUp() {
        viewPermissionMapper = new ViewPermissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(viewPermissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(viewPermissionMapper.fromId(null)).isNull();
    }
}
