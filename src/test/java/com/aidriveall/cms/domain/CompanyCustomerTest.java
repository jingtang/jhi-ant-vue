package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CompanyCustomerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCustomer.class);
        CompanyCustomer companyCustomer1 = new CompanyCustomer();
        companyCustomer1.setId(1L);
        CompanyCustomer companyCustomer2 = new CompanyCustomer();
        companyCustomer2.setId(companyCustomer1.getId());
        assertThat(companyCustomer1).isEqualTo(companyCustomer2);
        companyCustomer2.setId(2L);
        assertThat(companyCustomer1).isNotEqualTo(companyCustomer2);
        companyCustomer1.setId(null);
        assertThat(companyCustomer1).isNotEqualTo(companyCustomer2);
    }
}
