package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DataDictionaryMapperTest {

    private DataDictionaryMapper dataDictionaryMapper;

    @BeforeEach
    public void setUp() {
        dataDictionaryMapper = new DataDictionaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dataDictionaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dataDictionaryMapper.fromId(null)).isNull();
    }
}
