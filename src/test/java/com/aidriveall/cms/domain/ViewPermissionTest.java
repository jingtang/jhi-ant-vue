package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ViewPermissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewPermission.class);
        ViewPermission viewPermission1 = new ViewPermission();
        viewPermission1.setId(1L);
        ViewPermission viewPermission2 = new ViewPermission();
        viewPermission2.setId(viewPermission1.getId());
        assertThat(viewPermission1).isEqualTo(viewPermission2);
        viewPermission2.setId(2L);
        assertThat(viewPermission1).isNotEqualTo(viewPermission2);
        viewPermission1.setId(null);
        assertThat(viewPermission1).isNotEqualTo(viewPermission2);
    }
}
