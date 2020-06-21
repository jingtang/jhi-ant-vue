package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonBigDecimalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonBigDecimalDTO.class);
        CommonBigDecimalDTO commonBigDecimalDTO1 = new CommonBigDecimalDTO();
        commonBigDecimalDTO1.setId(1L);
        CommonBigDecimalDTO commonBigDecimalDTO2 = new CommonBigDecimalDTO();
        assertThat(commonBigDecimalDTO1).isNotEqualTo(commonBigDecimalDTO2);
        commonBigDecimalDTO2.setId(commonBigDecimalDTO1.getId());
        assertThat(commonBigDecimalDTO1).isEqualTo(commonBigDecimalDTO2);
        commonBigDecimalDTO2.setId(2L);
        assertThat(commonBigDecimalDTO1).isNotEqualTo(commonBigDecimalDTO2);
        commonBigDecimalDTO1.setId(null);
        assertThat(commonBigDecimalDTO1).isNotEqualTo(commonBigDecimalDTO2);
    }
}
