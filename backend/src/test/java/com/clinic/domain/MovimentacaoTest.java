package com.clinic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.clinic.web.rest.TestUtil;

public class MovimentacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movimentacao.class);
        Movimentacao movimentacao1 = new Movimentacao();
        movimentacao1.setId(1L);
        Movimentacao movimentacao2 = new Movimentacao();
        movimentacao2.setId(movimentacao1.getId());
        assertThat(movimentacao1).isEqualTo(movimentacao2);
        movimentacao2.setId(2L);
        assertThat(movimentacao1).isNotEqualTo(movimentacao2);
        movimentacao1.setId(null);
        assertThat(movimentacao1).isNotEqualTo(movimentacao2);
    }
}
