package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RestorateurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestorateurDTO.class);
        RestorateurDTO restorateurDTO1 = new RestorateurDTO();
        restorateurDTO1.setId(1L);
        RestorateurDTO restorateurDTO2 = new RestorateurDTO();
        assertThat(restorateurDTO1).isNotEqualTo(restorateurDTO2);
        restorateurDTO2.setId(restorateurDTO1.getId());
        assertThat(restorateurDTO1).isEqualTo(restorateurDTO2);
        restorateurDTO2.setId(2L);
        assertThat(restorateurDTO1).isNotEqualTo(restorateurDTO2);
        restorateurDTO1.setId(null);
        assertThat(restorateurDTO1).isNotEqualTo(restorateurDTO2);
    }
}
