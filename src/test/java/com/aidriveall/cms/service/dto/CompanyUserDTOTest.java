package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CompanyUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyUserDTO.class);
        CompanyUserDTO companyUserDTO1 = new CompanyUserDTO();
        companyUserDTO1.setId(1L);
        CompanyUserDTO companyUserDTO2 = new CompanyUserDTO();
        assertThat(companyUserDTO1).isNotEqualTo(companyUserDTO2);
        companyUserDTO2.setId(companyUserDTO1.getId());
        assertThat(companyUserDTO1).isEqualTo(companyUserDTO2);
        companyUserDTO2.setId(2L);
        assertThat(companyUserDTO1).isNotEqualTo(companyUserDTO2);
        companyUserDTO1.setId(null);
        assertThat(companyUserDTO1).isNotEqualTo(companyUserDTO2);
    }
}
