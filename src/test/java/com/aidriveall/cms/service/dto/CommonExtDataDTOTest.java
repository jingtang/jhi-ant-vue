package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonExtDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonExtDataDTO.class);
        CommonExtDataDTO commonExtDataDTO1 = new CommonExtDataDTO();
        commonExtDataDTO1.setId(1L);
        CommonExtDataDTO commonExtDataDTO2 = new CommonExtDataDTO();
        assertThat(commonExtDataDTO1).isNotEqualTo(commonExtDataDTO2);
        commonExtDataDTO2.setId(commonExtDataDTO1.getId());
        assertThat(commonExtDataDTO1).isEqualTo(commonExtDataDTO2);
        commonExtDataDTO2.setId(2L);
        assertThat(commonExtDataDTO1).isNotEqualTo(commonExtDataDTO2);
        commonExtDataDTO1.setId(null);
        assertThat(commonExtDataDTO1).isNotEqualTo(commonExtDataDTO2);
    }
}
