package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class UploadImageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadImageDTO.class);
        UploadImageDTO uploadImageDTO1 = new UploadImageDTO();
        uploadImageDTO1.setId(1L);
        UploadImageDTO uploadImageDTO2 = new UploadImageDTO();
        assertThat(uploadImageDTO1).isNotEqualTo(uploadImageDTO2);
        uploadImageDTO2.setId(uploadImageDTO1.getId());
        assertThat(uploadImageDTO1).isEqualTo(uploadImageDTO2);
        uploadImageDTO2.setId(2L);
        assertThat(uploadImageDTO1).isNotEqualTo(uploadImageDTO2);
        uploadImageDTO1.setId(null);
        assertThat(uploadImageDTO1).isNotEqualTo(uploadImageDTO2);
    }
}
