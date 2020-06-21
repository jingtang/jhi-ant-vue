package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonTextBlobTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonTextBlob.class);
        CommonTextBlob commonTextBlob1 = new CommonTextBlob();
        commonTextBlob1.setId(1L);
        CommonTextBlob commonTextBlob2 = new CommonTextBlob();
        commonTextBlob2.setId(commonTextBlob1.getId());
        assertThat(commonTextBlob1).isEqualTo(commonTextBlob2);
        commonTextBlob2.setId(2L);
        assertThat(commonTextBlob1).isNotEqualTo(commonTextBlob2);
        commonTextBlob1.setId(null);
        assertThat(commonTextBlob1).isNotEqualTo(commonTextBlob2);
    }
}
