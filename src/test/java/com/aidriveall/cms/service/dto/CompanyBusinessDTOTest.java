package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CompanyBusinessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyBusinessDTO.class);
        CompanyBusinessDTO companyBusinessDTO1 = new CompanyBusinessDTO();
        companyBusinessDTO1.setId(1L);
        CompanyBusinessDTO companyBusinessDTO2 = new CompanyBusinessDTO();
        assertThat(companyBusinessDTO1).isNotEqualTo(companyBusinessDTO2);
        companyBusinessDTO2.setId(companyBusinessDTO1.getId());
        assertThat(companyBusinessDTO1).isEqualTo(companyBusinessDTO2);
        companyBusinessDTO2.setId(2L);
        assertThat(companyBusinessDTO1).isNotEqualTo(companyBusinessDTO2);
        companyBusinessDTO1.setId(null);
        assertThat(companyBusinessDTO1).isNotEqualTo(companyBusinessDTO2);
    }
}
