package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class BusinessTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessType.class);
        BusinessType businessType1 = new BusinessType();
        businessType1.setId(1L);
        BusinessType businessType2 = new BusinessType();
        businessType2.setId(businessType1.getId());
        assertThat(businessType1).isEqualTo(businessType2);
        businessType2.setId(2L);
        assertThat(businessType1).isNotEqualTo(businessType2);
        businessType1.setId(null);
        assertThat(businessType1).isNotEqualTo(businessType2);
    }
}
