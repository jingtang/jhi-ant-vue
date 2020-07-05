package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonQueryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonQueryDTO.class);
        CommonQueryDTO commonQueryDTO1 = new CommonQueryDTO();
        commonQueryDTO1.setId(1L);
        CommonQueryDTO commonQueryDTO2 = new CommonQueryDTO();
        assertThat(commonQueryDTO1).isNotEqualTo(commonQueryDTO2);
        commonQueryDTO2.setId(commonQueryDTO1.getId());
        assertThat(commonQueryDTO1).isEqualTo(commonQueryDTO2);
        commonQueryDTO2.setId(2L);
        assertThat(commonQueryDTO1).isNotEqualTo(commonQueryDTO2);
        commonQueryDTO1.setId(null);
        assertThat(commonQueryDTO1).isNotEqualTo(commonQueryDTO2);
    }
}
