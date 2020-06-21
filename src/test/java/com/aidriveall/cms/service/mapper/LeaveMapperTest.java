package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LeaveMapperTest {

    private LeaveMapper leaveMapper;

    @BeforeEach
    public void setUp() {
        leaveMapper = new LeaveMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(leaveMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(leaveMapper.fromId(null)).isNull();
    }
}
