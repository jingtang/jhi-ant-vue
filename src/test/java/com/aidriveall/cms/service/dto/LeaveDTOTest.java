package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class LeaveDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveDTO.class);
        LeaveDTO leaveDTO1 = new LeaveDTO();
        leaveDTO1.setId(1L);
        LeaveDTO leaveDTO2 = new LeaveDTO();
        assertThat(leaveDTO1).isNotEqualTo(leaveDTO2);
        leaveDTO2.setId(leaveDTO1.getId());
        assertThat(leaveDTO1).isEqualTo(leaveDTO2);
        leaveDTO2.setId(2L);
        assertThat(leaveDTO1).isNotEqualTo(leaveDTO2);
        leaveDTO1.setId(null);
        assertThat(leaveDTO1).isNotEqualTo(leaveDTO2);
    }
}
