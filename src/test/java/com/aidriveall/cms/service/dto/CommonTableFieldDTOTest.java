package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonTableFieldDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonTableFieldDTO.class);
        CommonTableFieldDTO commonTableFieldDTO1 = new CommonTableFieldDTO();
        commonTableFieldDTO1.setId(1L);
        CommonTableFieldDTO commonTableFieldDTO2 = new CommonTableFieldDTO();
        assertThat(commonTableFieldDTO1).isNotEqualTo(commonTableFieldDTO2);
        commonTableFieldDTO2.setId(commonTableFieldDTO1.getId());
        assertThat(commonTableFieldDTO1).isEqualTo(commonTableFieldDTO2);
        commonTableFieldDTO2.setId(2L);
        assertThat(commonTableFieldDTO1).isNotEqualTo(commonTableFieldDTO2);
        commonTableFieldDTO1.setId(null);
        assertThat(commonTableFieldDTO1).isNotEqualTo(commonTableFieldDTO2);
    }
}
