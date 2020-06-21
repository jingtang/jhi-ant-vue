package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ProcessTableConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessTableConfigDTO.class);
        ProcessTableConfigDTO processTableConfigDTO1 = new ProcessTableConfigDTO();
        processTableConfigDTO1.setId(1L);
        ProcessTableConfigDTO processTableConfigDTO2 = new ProcessTableConfigDTO();
        assertThat(processTableConfigDTO1).isNotEqualTo(processTableConfigDTO2);
        processTableConfigDTO2.setId(processTableConfigDTO1.getId());
        assertThat(processTableConfigDTO1).isEqualTo(processTableConfigDTO2);
        processTableConfigDTO2.setId(2L);
        assertThat(processTableConfigDTO1).isNotEqualTo(processTableConfigDTO2);
        processTableConfigDTO1.setId(null);
        assertThat(processTableConfigDTO1).isNotEqualTo(processTableConfigDTO2);
    }
}
