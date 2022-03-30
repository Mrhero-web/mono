package com.ledar.mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ledar.mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AUser.class);
        AUser aUser1 = new AUser();
        aUser1.setId(1L);
        AUser aUser2 = new AUser();
        aUser2.setId(aUser1.getId());
        assertThat(aUser1).isEqualTo(aUser2);
        aUser2.setId(2L);
        assertThat(aUser1).isNotEqualTo(aUser2);
        aUser1.setId(null);
        assertThat(aUser1).isNotEqualTo(aUser2);
    }
}
