package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class ViewPermissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewPermissionDTO.class);
        ViewPermissionDTO viewPermissionDTO1 = new ViewPermissionDTO();
        viewPermissionDTO1.setId(1L);
        ViewPermissionDTO viewPermissionDTO2 = new ViewPermissionDTO();
        assertThat(viewPermissionDTO1).isNotEqualTo(viewPermissionDTO2);
        viewPermissionDTO2.setId(viewPermissionDTO1.getId());
        assertThat(viewPermissionDTO1).isEqualTo(viewPermissionDTO2);
        viewPermissionDTO2.setId(2L);
        assertThat(viewPermissionDTO1).isNotEqualTo(viewPermissionDTO2);
        viewPermissionDTO1.setId(null);
        assertThat(viewPermissionDTO1).isNotEqualTo(viewPermissionDTO2);
    }
}
