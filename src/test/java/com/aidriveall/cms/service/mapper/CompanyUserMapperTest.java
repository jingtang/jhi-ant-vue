package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyUserMapperTest {

    private CompanyUserMapper companyUserMapper;

    @BeforeEach
    public void setUp() {
        companyUserMapper = new CompanyUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyUserMapper.fromId(null)).isNull();
    }
}
