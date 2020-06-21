package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CompanyCustomerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCustomerDTO.class);
        CompanyCustomerDTO companyCustomerDTO1 = new CompanyCustomerDTO();
        companyCustomerDTO1.setId(1L);
        CompanyCustomerDTO companyCustomerDTO2 = new CompanyCustomerDTO();
        assertThat(companyCustomerDTO1).isNotEqualTo(companyCustomerDTO2);
        companyCustomerDTO2.setId(companyCustomerDTO1.getId());
        assertThat(companyCustomerDTO1).isEqualTo(companyCustomerDTO2);
        companyCustomerDTO2.setId(2L);
        assertThat(companyCustomerDTO1).isNotEqualTo(companyCustomerDTO2);
        companyCustomerDTO1.setId(null);
        assertThat(companyCustomerDTO1).isNotEqualTo(companyCustomerDTO2);
    }
}
