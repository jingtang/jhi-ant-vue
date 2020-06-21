package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonDoubleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonDoubleDTO.class);
        CommonDoubleDTO commonDoubleDTO1 = new CommonDoubleDTO();
        commonDoubleDTO1.setId(1L);
        CommonDoubleDTO commonDoubleDTO2 = new CommonDoubleDTO();
        assertThat(commonDoubleDTO1).isNotEqualTo(commonDoubleDTO2);
        commonDoubleDTO2.setId(commonDoubleDTO1.getId());
        assertThat(commonDoubleDTO1).isEqualTo(commonDoubleDTO2);
        commonDoubleDTO2.setId(2L);
        assertThat(commonDoubleDTO1).isNotEqualTo(commonDoubleDTO2);
        commonDoubleDTO1.setId(null);
        assertThat(commonDoubleDTO1).isNotEqualTo(commonDoubleDTO2);
    }
}
