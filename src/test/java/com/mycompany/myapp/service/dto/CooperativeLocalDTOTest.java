package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CooperativeLocalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CooperativeLocalDTO.class);
        CooperativeLocalDTO cooperativeLocalDTO1 = new CooperativeLocalDTO();
        cooperativeLocalDTO1.setId(1L);
        CooperativeLocalDTO cooperativeLocalDTO2 = new CooperativeLocalDTO();
        assertThat(cooperativeLocalDTO1).isNotEqualTo(cooperativeLocalDTO2);
        cooperativeLocalDTO2.setId(cooperativeLocalDTO1.getId());
        assertThat(cooperativeLocalDTO1).isEqualTo(cooperativeLocalDTO2);
        cooperativeLocalDTO2.setId(2L);
        assertThat(cooperativeLocalDTO1).isNotEqualTo(cooperativeLocalDTO2);
        cooperativeLocalDTO1.setId(null);
        assertThat(cooperativeLocalDTO1).isNotEqualTo(cooperativeLocalDTO2);
    }
}
