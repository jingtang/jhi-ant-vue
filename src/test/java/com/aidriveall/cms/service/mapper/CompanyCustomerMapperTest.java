package com.aidriveall.cms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyCustomerMapperTest {

    private CompanyCustomerMapper companyCustomerMapper;

    @BeforeEach
    public void setUp() {
        companyCustomerMapper = new CompanyCustomerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyCustomerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyCustomerMapper.fromId(null)).isNull();
    }
}
