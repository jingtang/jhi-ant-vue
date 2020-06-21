package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ProcessFormConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessFormConfigDTO.class);
        ProcessFormConfigDTO processFormConfigDTO1 = new ProcessFormConfigDTO();
        processFormConfigDTO1.setId(1L);
        ProcessFormConfigDTO processFormConfigDTO2 = new ProcessFormConfigDTO();
        assertThat(processFormConfigDTO1).isNotEqualTo(processFormConfigDTO2);
        processFormConfigDTO2.setId(processFormConfigDTO1.getId());
        assertThat(processFormConfigDTO1).isEqualTo(processFormConfigDTO2);
        processFormConfigDTO2.setId(2L);
        assertThat(processFormConfigDTO1).isNotEqualTo(processFormConfigDTO2);
        processFormConfigDTO1.setId(null);
        assertThat(processFormConfigDTO1).isNotEqualTo(processFormConfigDTO2);
    }
}
