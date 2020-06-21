package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonTableRelationshipTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonTableRelationship.class);
        CommonTableRelationship commonTableRelationship1 = new CommonTableRelationship();
        commonTableRelationship1.setId(1L);
        CommonTableRelationship commonTableRelationship2 = new CommonTableRelationship();
        commonTableRelationship2.setId(commonTableRelationship1.getId());
        assertThat(commonTableRelationship1).isEqualTo(commonTableRelationship2);
        commonTableRelationship2.setId(2L);
        assertThat(commonTableRelationship1).isNotEqualTo(commonTableRelationship2);
        commonTableRelationship1.setId(null);
        assertThat(commonTableRelationship1).isNotEqualTo(commonTableRelationship2);
    }
}
