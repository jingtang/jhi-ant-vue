package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyBusinessMapperTest {

    private CompanyBusinessMapper companyBusinessMapper;

    @BeforeEach
    public void setUp() {
        companyBusinessMapper = new CompanyBusinessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyBusinessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyBusinessMapper.fromId(null)).isNull();
    }
}
