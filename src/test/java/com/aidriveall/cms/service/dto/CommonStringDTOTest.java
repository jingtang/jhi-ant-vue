package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonStringDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonStringDTO.class);
        CommonStringDTO commonStringDTO1 = new CommonStringDTO();
        commonStringDTO1.setId(1L);
        CommonStringDTO commonStringDTO2 = new CommonStringDTO();
        assertThat(commonStringDTO1).isNotEqualTo(commonStringDTO2);
        commonStringDTO2.setId(commonStringDTO1.getId());
        assertThat(commonStringDTO1).isEqualTo(commonStringDTO2);
        commonStringDTO2.setId(2L);
        assertThat(commonStringDTO1).isNotEqualTo(commonStringDTO2);
        commonStringDTO1.setId(null);
        assertThat(commonStringDTO1).isNotEqualTo(commonStringDTO2);
    }
}
