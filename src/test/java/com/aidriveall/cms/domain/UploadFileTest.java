package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class UploadFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadFile.class);
        UploadFile uploadFile1 = new UploadFile();
        uploadFile1.setId(1L);
        UploadFile uploadFile2 = new UploadFile();
        uploadFile2.setId(uploadFile1.getId());
        assertThat(uploadFile1).isEqualTo(uploadFile2);
        uploadFile2.setId(2L);
        assertThat(uploadFile1).isNotEqualTo(uploadFile2);
        uploadFile1.setId(null);
        assertThat(uploadFile1).isNotEqualTo(uploadFile2);
    }
}
