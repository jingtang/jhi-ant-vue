package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ProcessEntityRelationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessEntityRelationDTO.class);
        ProcessEntityRelationDTO processEntityRelationDTO1 = new ProcessEntityRelationDTO();
        processEntityRelationDTO1.setId(1L);
        ProcessEntityRelationDTO processEntityRelationDTO2 = new ProcessEntityRelationDTO();
        assertThat(processEntityRelationDTO1).isNotEqualTo(processEntityRelationDTO2);
        processEntityRelationDTO2.setId(processEntityRelationDTO1.getId());
        assertThat(processEntityRelationDTO1).isEqualTo(processEntityRelationDTO2);
        processEntityRelationDTO2.setId(2L);
        assertThat(processEntityRelationDTO1).isNotEqualTo(processEntityRelationDTO2);
        processEntityRelationDTO1.setId(null);
        assertThat(processEntityRelationDTO1).isNotEqualTo(processEntityRelationDTO2);
    }
}
