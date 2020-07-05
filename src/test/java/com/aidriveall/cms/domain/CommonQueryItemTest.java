package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonQueryItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonQueryItem.class);
        CommonQueryItem commonQueryItem1 = new CommonQueryItem();
        commonQueryItem1.setId(1L);
        CommonQueryItem commonQueryItem2 = new CommonQueryItem();
        commonQueryItem2.setId(commonQueryItem1.getId());
        assertThat(commonQueryItem1).isEqualTo(commonQueryItem2);
        commonQueryItem2.setId(2L);
        assertThat(commonQueryItem1).isNotEqualTo(commonQueryItem2);
        commonQueryItem1.setId(null);
        assertThat(commonQueryItem1).isNotEqualTo(commonQueryItem2);
    }
}
