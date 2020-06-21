package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonIntegerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonIntegerDTO.class);
        CommonIntegerDTO commonIntegerDTO1 = new CommonIntegerDTO();
        commonIntegerDTO1.setId(1L);
        CommonIntegerDTO commonIntegerDTO2 = new CommonIntegerDTO();
        assertThat(commonIntegerDTO1).isNotEqualTo(commonIntegerDTO2);
        commonIntegerDTO2.setId(commonIntegerDTO1.getId());
        assertThat(commonIntegerDTO1).isEqualTo(commonIntegerDTO2);
        commonIntegerDTO2.setId(2L);
        assertThat(commonIntegerDTO1).isNotEqualTo(commonIntegerDTO2);
        commonIntegerDTO1.setId(null);
        assertThat(commonIntegerDTO1).isNotEqualTo(commonIntegerDTO2);
    }
}
