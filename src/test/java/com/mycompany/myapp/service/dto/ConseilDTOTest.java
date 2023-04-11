package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConseilDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConseilDTO.class);
        ConseilDTO conseilDTO1 = new ConseilDTO();
        conseilDTO1.setId(1L);
        ConseilDTO conseilDTO2 = new ConseilDTO();
        assertThat(conseilDTO1).isNotEqualTo(conseilDTO2);
        conseilDTO2.setId(conseilDTO1.getId());
        assertThat(conseilDTO1).isEqualTo(conseilDTO2);
        conseilDTO2.setId(2L);
        assertThat(conseilDTO1).isNotEqualTo(conseilDTO2);
        conseilDTO1.setId(null);
        assertThat(conseilDTO1).isNotEqualTo(conseilDTO2);
    }
}
