package com.clinic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.clinic.web.rest.TestUtil;

public class SaldoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Saldo.class);
        Saldo saldo1 = new Saldo();
        saldo1.setId(1L);
        Saldo saldo2 = new Saldo();
        saldo2.setId(saldo1.getId());
        assertThat(saldo1).isEqualTo(saldo2);
        saldo2.setId(2L);
        assertThat(saldo1).isNotEqualTo(saldo2);
        saldo1.setId(null);
        assertThat(saldo1).isNotEqualTo(saldo2);
    }
}
