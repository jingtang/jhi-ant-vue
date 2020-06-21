package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonLongTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonLong.class);
        CommonLong commonLong1 = new CommonLong();
        commonLong1.setId(1L);
        CommonLong commonLong2 = new CommonLong();
        commonLong2.setId(commonLong1.getId());
        assertThat(commonLong1).isEqualTo(commonLong2);
        commonLong2.setId(2L);
        assertThat(commonLong1).isNotEqualTo(commonLong2);
        commonLong1.setId(null);
        assertThat(commonLong1).isNotEqualTo(commonLong2);
    }
}
