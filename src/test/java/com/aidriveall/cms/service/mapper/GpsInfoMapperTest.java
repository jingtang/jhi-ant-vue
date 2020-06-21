package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GpsInfoMapperTest {

    private GpsInfoMapper gpsInfoMapper;

    @BeforeEach
    public void setUp() {
        gpsInfoMapper = new GpsInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gpsInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gpsInfoMapper.fromId(null)).isNull();
    }
}
