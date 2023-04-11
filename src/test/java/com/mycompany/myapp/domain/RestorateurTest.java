package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RestorateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Restorateur.class);
        Restorateur restorateur1 = new Restorateur();
        restorateur1.setId(1L);
        Restorateur restorateur2 = new Restorateur();
        restorateur2.setId(restorateur1.getId());
        assertThat(restorateur1).isEqualTo(restorateur2);
        restorateur2.setId(2L);
        assertThat(restorateur1).isNotEqualTo(restorateur2);
        restorateur1.setId(null);
        assertThat(restorateur1).isNotEqualTo(restorateur2);
    }
}
