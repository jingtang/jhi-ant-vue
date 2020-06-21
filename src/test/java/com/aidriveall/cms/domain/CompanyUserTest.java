package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CompanyUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyUser.class);
        CompanyUser companyUser1 = new CompanyUser();
        companyUser1.setId(1L);
        CompanyUser companyUser2 = new CompanyUser();
        companyUser2.setId(companyUser1.getId());
        assertThat(companyUser1).isEqualTo(companyUser2);
        companyUser2.setId(2L);
        assertThat(companyUser1).isNotEqualTo(companyUser2);
        companyUser1.setId(null);
        assertThat(companyUser1).isNotEqualTo(companyUser2);
    }
}
