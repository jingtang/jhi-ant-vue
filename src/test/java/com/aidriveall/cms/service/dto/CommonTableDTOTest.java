package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonTableDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonTableDTO.class);
        CommonTableDTO commonTableDTO1 = new CommonTableDTO();
        commonTableDTO1.setId(1L);
        CommonTableDTO commonTableDTO2 = new CommonTableDTO();
        assertThat(commonTableDTO1).isNotEqualTo(commonTableDTO2);
        commonTableDTO2.setId(commonTableDTO1.getId());
        assertThat(commonTableDTO1).isEqualTo(commonTableDTO2);
        commonTableDTO2.setId(2L);
        assertThat(commonTableDTO1).isNotEqualTo(commonTableDTO2);
        commonTableDTO1.setId(null);
        assertThat(commonTableDTO1).isNotEqualTo(commonTableDTO2);
    }
}
