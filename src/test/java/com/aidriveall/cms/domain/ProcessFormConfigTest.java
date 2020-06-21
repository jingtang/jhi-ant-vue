package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ProcessFormConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessFormConfig.class);
        ProcessFormConfig processFormConfig1 = new ProcessFormConfig();
        processFormConfig1.setId(1L);
        ProcessFormConfig processFormConfig2 = new ProcessFormConfig();
        processFormConfig2.setId(processFormConfig1.getId());
        assertThat(processFormConfig1).isEqualTo(processFormConfig2);
        processFormConfig2.setId(2L);
        assertThat(processFormConfig1).isNotEqualTo(processFormConfig2);
        processFormConfig1.setId(null);
        assertThat(processFormConfig1).isNotEqualTo(processFormConfig2);
    }
}
