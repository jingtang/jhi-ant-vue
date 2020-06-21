package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class DataDictionaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataDictionaryDTO.class);
        DataDictionaryDTO dataDictionaryDTO1 = new DataDictionaryDTO();
        dataDictionaryDTO1.setId(1L);
        DataDictionaryDTO dataDictionaryDTO2 = new DataDictionaryDTO();
        assertThat(dataDictionaryDTO1).isNotEqualTo(dataDictionaryDTO2);
        dataDictionaryDTO2.setId(dataDictionaryDTO1.getId());
        assertThat(dataDictionaryDTO1).isEqualTo(dataDictionaryDTO2);
        dataDictionaryDTO2.setId(2L);
        assertThat(dataDictionaryDTO1).isNotEqualTo(dataDictionaryDTO2);
        dataDictionaryDTO1.setId(null);
        assertThat(dataDictionaryDTO1).isNotEqualTo(dataDictionaryDTO2);
    }
}
