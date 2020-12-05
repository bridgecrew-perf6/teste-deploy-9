package com.clinic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.clinic.web.rest.TestUtil;

public class TipoProfissionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProfissional.class);
        TipoProfissional tipoProfissional1 = new TipoProfissional();
        tipoProfissional1.setId(1L);
        TipoProfissional tipoProfissional2 = new TipoProfissional();
        tipoProfissional2.setId(tipoProfissional1.getId());
        assertThat(tipoProfissional1).isEqualTo(tipoProfissional2);
        tipoProfissional2.setId(2L);
        assertThat(tipoProfissional1).isNotEqualTo(tipoProfissional2);
        tipoProfissional1.setId(null);
        assertThat(tipoProfissional1).isNotEqualTo(tipoProfissional2);
    }
}
