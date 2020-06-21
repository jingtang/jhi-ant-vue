package com.aidriveall.cms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class BusinessTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessTypeDTO.class);
        BusinessTypeDTO businessTypeDTO1 = new BusinessTypeDTO();
        businessTypeDTO1.setId(1L);
        BusinessTypeDTO businessTypeDTO2 = new BusinessTypeDTO();
        assertThat(businessTypeDTO1).isNotEqualTo(businessTypeDTO2);
        businessTypeDTO2.setId(businessTypeDTO1.getId());
        assertThat(businessTypeDTO1).isEqualTo(businessTypeDTO2);
        businessTypeDTO2.setId(2L);
        assertThat(businessTypeDTO1).isNotEqualTo(businessTypeDTO2);
        businessTypeDTO1.setId(null);
        assertThat(businessTypeDTO1).isNotEqualTo(businessTypeDTO2);
    }
}
