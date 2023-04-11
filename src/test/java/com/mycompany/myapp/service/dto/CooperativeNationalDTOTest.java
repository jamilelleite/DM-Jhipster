package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CooperativeNationalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CooperativeNationalDTO.class);
        CooperativeNationalDTO cooperativeNationalDTO1 = new CooperativeNationalDTO();
        cooperativeNationalDTO1.setId(1L);
        CooperativeNationalDTO cooperativeNationalDTO2 = new CooperativeNationalDTO();
        assertThat(cooperativeNationalDTO1).isNotEqualTo(cooperativeNationalDTO2);
        cooperativeNationalDTO2.setId(cooperativeNationalDTO1.getId());
        assertThat(cooperativeNationalDTO1).isEqualTo(cooperativeNationalDTO2);
        cooperativeNationalDTO2.setId(2L);
        assertThat(cooperativeNationalDTO1).isNotEqualTo(cooperativeNationalDTO2);
        cooperativeNationalDTO1.setId(null);
        assertThat(cooperativeNationalDTO1).isNotEqualTo(cooperativeNationalDTO2);
    }
}
