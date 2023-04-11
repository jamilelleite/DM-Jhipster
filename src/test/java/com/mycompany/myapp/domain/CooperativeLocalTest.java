package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CooperativeLocalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CooperativeLocal.class);
        CooperativeLocal cooperativeLocal1 = new CooperativeLocal();
        cooperativeLocal1.setId(1L);
        CooperativeLocal cooperativeLocal2 = new CooperativeLocal();
        cooperativeLocal2.setId(cooperativeLocal1.getId());
        assertThat(cooperativeLocal1).isEqualTo(cooperativeLocal2);
        cooperativeLocal2.setId(2L);
        assertThat(cooperativeLocal1).isNotEqualTo(cooperativeLocal2);
        cooperativeLocal1.setId(null);
        assertThat(cooperativeLocal1).isNotEqualTo(cooperativeLocal2);
    }
}
