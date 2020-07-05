package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonQueryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonQuery.class);
        CommonQuery commonQuery1 = new CommonQuery();
        commonQuery1.setId(1L);
        CommonQuery commonQuery2 = new CommonQuery();
        commonQuery2.setId(commonQuery1.getId());
        assertThat(commonQuery1).isEqualTo(commonQuery2);
        commonQuery2.setId(2L);
        assertThat(commonQuery1).isNotEqualTo(commonQuery2);
        commonQuery1.setId(null);
        assertThat(commonQuery1).isNotEqualTo(commonQuery2);
    }
}
