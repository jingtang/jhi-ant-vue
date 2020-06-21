package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ProcessEntityRelationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessEntityRelation.class);
        ProcessEntityRelation processEntityRelation1 = new ProcessEntityRelation();
        processEntityRelation1.setId(1L);
        ProcessEntityRelation processEntityRelation2 = new ProcessEntityRelation();
        processEntityRelation2.setId(processEntityRelation1.getId());
        assertThat(processEntityRelation1).isEqualTo(processEntityRelation2);
        processEntityRelation2.setId(2L);
        assertThat(processEntityRelation1).isNotEqualTo(processEntityRelation2);
        processEntityRelation1.setId(null);
        assertThat(processEntityRelation1).isNotEqualTo(processEntityRelation2);
    }
}
