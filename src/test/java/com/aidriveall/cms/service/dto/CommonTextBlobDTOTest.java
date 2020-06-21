package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonTextBlobDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonTextBlobDTO.class);
        CommonTextBlobDTO commonTextBlobDTO1 = new CommonTextBlobDTO();
        commonTextBlobDTO1.setId(1L);
        CommonTextBlobDTO commonTextBlobDTO2 = new CommonTextBlobDTO();
        assertThat(commonTextBlobDTO1).isNotEqualTo(commonTextBlobDTO2);
        commonTextBlobDTO2.setId(commonTextBlobDTO1.getId());
        assertThat(commonTextBlobDTO1).isEqualTo(commonTextBlobDTO2);
        commonTextBlobDTO2.setId(2L);
        assertThat(commonTextBlobDTO1).isNotEqualTo(commonTextBlobDTO2);
        commonTextBlobDTO1.setId(null);
        assertThat(commonTextBlobDTO1).isNotEqualTo(commonTextBlobDTO2);
    }
}
