package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class UReportFileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UReportFileDTO.class);
        UReportFileDTO uReportFileDTO1 = new UReportFileDTO();
        uReportFileDTO1.setId(1L);
        UReportFileDTO uReportFileDTO2 = new UReportFileDTO();
        assertThat(uReportFileDTO1).isNotEqualTo(uReportFileDTO2);
        uReportFileDTO2.setId(uReportFileDTO1.getId());
        assertThat(uReportFileDTO1).isEqualTo(uReportFileDTO2);
        uReportFileDTO2.setId(2L);
        assertThat(uReportFileDTO1).isNotEqualTo(uReportFileDTO2);
        uReportFileDTO1.setId(null);
        assertThat(uReportFileDTO1).isNotEqualTo(uReportFileDTO2);
    }
}
