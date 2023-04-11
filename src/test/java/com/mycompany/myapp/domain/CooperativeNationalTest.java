package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CooperativeNationalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CooperativeNational.class);
        CooperativeNational cooperativeNational1 = new CooperativeNational();
        cooperativeNational1.setId(1L);
        CooperativeNational cooperativeNational2 = new CooperativeNational();
        cooperativeNational2.setId(cooperativeNational1.getId());
        assertThat(cooperativeNational1).isEqualTo(cooperativeNational2);
        cooperativeNational2.setId(2L);
        assertThat(cooperativeNational1).isNotEqualTo(cooperativeNational2);
        cooperativeNational1.setId(null);
        assertThat(cooperativeNational1).isNotEqualTo(cooperativeNational2);
    }
}
