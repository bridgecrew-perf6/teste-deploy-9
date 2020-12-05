package com.clinic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.clinic.web.rest.TestUtil;

public class ProfissionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profissional.class);
        Profissional profissional1 = new Profissional();
        profissional1.setId(1L);
        Profissional profissional2 = new Profissional();
        profissional2.setId(profissional1.getId());
        assertThat(profissional1).isEqualTo(profissional2);
        profissional2.setId(2L);
        assertThat(profissional1).isNotEqualTo(profissional2);
        profissional1.setId(null);
        assertThat(profissional1).isNotEqualTo(profissional2);
    }
}
