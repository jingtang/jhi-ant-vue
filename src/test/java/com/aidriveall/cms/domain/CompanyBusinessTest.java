package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CompanyBusinessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyBusiness.class);
        CompanyBusiness companyBusiness1 = new CompanyBusiness();
        companyBusiness1.setId(1L);
        CompanyBusiness companyBusiness2 = new CompanyBusiness();
        companyBusiness2.setId(companyBusiness1.getId());
        assertThat(companyBusiness1).isEqualTo(companyBusiness2);
        companyBusiness2.setId(2L);
        assertThat(companyBusiness1).isNotEqualTo(companyBusiness2);
        companyBusiness1.setId(null);
        assertThat(companyBusiness1).isNotEqualTo(companyBusiness2);
    }
}
