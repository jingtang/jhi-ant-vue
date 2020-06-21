package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonTableFieldTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonTableField.class);
        CommonTableField commonTableField1 = new CommonTableField();
        commonTableField1.setId(1L);
        CommonTableField commonTableField2 = new CommonTableField();
        commonTableField2.setId(commonTableField1.getId());
        assertThat(commonTableField1).isEqualTo(commonTableField2);
        commonTableField2.setId(2L);
        assertThat(commonTableField1).isNotEqualTo(commonTableField2);
        commonTableField1.setId(null);
        assertThat(commonTableField1).isNotEqualTo(commonTableField2);
    }
}
