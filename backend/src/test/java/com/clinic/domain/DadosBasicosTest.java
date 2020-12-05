package com.clinic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.clinic.web.rest.TestUtil;

public class DadosBasicosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DadosBasicos.class);
        DadosBasicos dadosBasicos1 = new DadosBasicos();
        dadosBasicos1.setId(1L);
        DadosBasicos dadosBasicos2 = new DadosBasicos();
        dadosBasicos2.setId(dadosBasicos1.getId());
        assertThat(dadosBasicos1).isEqualTo(dadosBasicos2);
        dadosBasicos2.setId(2L);
        assertThat(dadosBasicos1).isNotEqualTo(dadosBasicos2);
        dadosBasicos1.setId(null);
        assertThat(dadosBasicos1).isNotEqualTo(dadosBasicos2);
    }
}
