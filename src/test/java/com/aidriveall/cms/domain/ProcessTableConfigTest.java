package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ProcessTableConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessTableConfig.class);
        ProcessTableConfig processTableConfig1 = new ProcessTableConfig();
        processTableConfig1.setId(1L);
        ProcessTableConfig processTableConfig2 = new ProcessTableConfig();
        processTableConfig2.setId(processTableConfig1.getId());
        assertThat(processTableConfig1).isEqualTo(processTableConfig2);
        processTableConfig2.setId(2L);
        assertThat(processTableConfig1).isNotEqualTo(processTableConfig2);
        processTableConfig1.setId(null);
        assertThat(processTableConfig1).isNotEqualTo(processTableConfig2);
    }
}
