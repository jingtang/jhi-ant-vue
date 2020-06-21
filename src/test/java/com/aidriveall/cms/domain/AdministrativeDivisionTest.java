package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class AdministrativeDivisionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdministrativeDivision.class);
        AdministrativeDivision administrativeDivision1 = new AdministrativeDivision();
        administrativeDivision1.setId(1L);
        AdministrativeDivision administrativeDivision2 = new AdministrativeDivision();
        administrativeDivision2.setId(administrativeDivision1.getId());
        assertThat(administrativeDivision1).isEqualTo(administrativeDivision2);
        administrativeDivision2.setId(2L);
        assertThat(administrativeDivision1).isNotEqualTo(administrativeDivision2);
        administrativeDivision1.setId(null);
        assertThat(administrativeDivision1).isNotEqualTo(administrativeDivision2);
    }
}
