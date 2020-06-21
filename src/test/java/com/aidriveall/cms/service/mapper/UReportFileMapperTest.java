package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UReportFileMapperTest {

    private UReportFileMapper uReportFileMapper;

    @BeforeEach
    public void setUp() {
        uReportFileMapper = new UReportFileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(uReportFileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(uReportFileMapper.fromId(null)).isNull();
    }
}
