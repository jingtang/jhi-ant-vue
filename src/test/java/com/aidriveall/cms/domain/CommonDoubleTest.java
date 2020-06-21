package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonDoubleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonDouble.class);
        CommonDouble commonDouble1 = new CommonDouble();
        commonDouble1.setId(1L);
        CommonDouble commonDouble2 = new CommonDouble();
        commonDouble2.setId(commonDouble1.getId());
        assertThat(commonDouble1).isEqualTo(commonDouble2);
        commonDouble2.setId(2L);
        assertThat(commonDouble1).isNotEqualTo(commonDouble2);
        commonDouble1.setId(null);
        assertThat(commonDouble1).isNotEqualTo(commonDouble2);
    }
}
