package com.aidriveall.cms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.aidriveall.cms.web.rest.TestUtil;

public class CommonZonedDateTimeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommonZonedDateTime.class);
        CommonZonedDateTime commonZonedDateTime1 = new CommonZonedDateTime();
        commonZonedDateTime1.setId(1L);
        CommonZonedDateTime commonZonedDateTime2 = new CommonZonedDateTime();
        commonZonedDateTime2.setId(commonZonedDateTime1.getId());
        assertThat(commonZonedDateTime1).isEqualTo(commonZonedDateTime2);
        commonZonedDateTime2.setId(2L);
        assertThat(commonZonedDateTime1).isNotEqualTo(commonZonedDateTime2);
        commonZonedDateTime1.setId(null);
        assertThat(commonZonedDateTime1).isNotEqualTo(commonZonedDateTime2);
    }
}
