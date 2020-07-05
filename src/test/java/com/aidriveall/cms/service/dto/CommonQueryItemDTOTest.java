package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonQueryItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonQueryItemDTO.class);
        CommonQueryItemDTO commonQueryItemDTO1 = new CommonQueryItemDTO();
        commonQueryItemDTO1.setId(1L);
        CommonQueryItemDTO commonQueryItemDTO2 = new CommonQueryItemDTO();
        assertThat(commonQueryItemDTO1).isNotEqualTo(commonQueryItemDTO2);
        commonQueryItemDTO2.setId(commonQueryItemDTO1.getId());
        assertThat(commonQueryItemDTO1).isEqualTo(commonQueryItemDTO2);
        commonQueryItemDTO2.setId(2L);
        assertThat(commonQueryItemDTO1).isNotEqualTo(commonQueryItemDTO2);
        commonQueryItemDTO1.setId(null);
        assertThat(commonQueryItemDTO1).isNotEqualTo(commonQueryItemDTO2);
    }
}
