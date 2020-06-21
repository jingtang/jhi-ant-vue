package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonTableRelationshipDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonTableRelationshipDTO.class);
        CommonTableRelationshipDTO commonTableRelationshipDTO1 = new CommonTableRelationshipDTO();
        commonTableRelationshipDTO1.setId(1L);
        CommonTableRelationshipDTO commonTableRelationshipDTO2 = new CommonTableRelationshipDTO();
        assertThat(commonTableRelationshipDTO1).isNotEqualTo(commonTableRelationshipDTO2);
        commonTableRelationshipDTO2.setId(commonTableRelationshipDTO1.getId());
        assertThat(commonTableRelationshipDTO1).isEqualTo(commonTableRelationshipDTO2);
        commonTableRelationshipDTO2.setId(2L);
        assertThat(commonTableRelationshipDTO1).isNotEqualTo(commonTableRelationshipDTO2);
        commonTableRelationshipDTO1.setId(null);
        assertThat(commonTableRelationshipDTO1).isNotEqualTo(commonTableRelationshipDTO2);
    }
}
