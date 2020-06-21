package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonLocalDateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonLocalDateDTO.class);
        CommonLocalDateDTO commonLocalDateDTO1 = new CommonLocalDateDTO();
        commonLocalDateDTO1.setId(1L);
        CommonLocalDateDTO commonLocalDateDTO2 = new CommonLocalDateDTO();
        assertThat(commonLocalDateDTO1).isNotEqualTo(commonLocalDateDTO2);
        commonLocalDateDTO2.setId(commonLocalDateDTO1.getId());
        assertThat(commonLocalDateDTO1).isEqualTo(commonLocalDateDTO2);
        commonLocalDateDTO2.setId(2L);
        assertThat(commonLocalDateDTO1).isNotEqualTo(commonLocalDateDTO2);
        commonLocalDateDTO1.setId(null);
        assertThat(commonLocalDateDTO1).isNotEqualTo(commonLocalDateDTO2);
    }
}
