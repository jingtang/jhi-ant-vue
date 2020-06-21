package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonLocalDateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonLocalDate.class);
        CommonLocalDate commonLocalDate1 = new CommonLocalDate();
        commonLocalDate1.setId(1L);
        CommonLocalDate commonLocalDate2 = new CommonLocalDate();
        commonLocalDate2.setId(commonLocalDate1.getId());
        assertThat(commonLocalDate1).isEqualTo(commonLocalDate2);
        commonLocalDate2.setId(2L);
        assertThat(commonLocalDate1).isNotEqualTo(commonLocalDate2);
        commonLocalDate1.setId(null);
        assertThat(commonLocalDate1).isNotEqualTo(commonLocalDate2);
    }
}
