package com.clinic.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.clinic.web.rest.TestUtil;

public class ClinicaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clinica.class);
        Clinica clinica1 = new Clinica();
        clinica1.setId(1L);
        Clinica clinica2 = new Clinica();
        clinica2.setId(clinica1.getId());
        assertThat(clinica1).isEqualTo(clinica2);
        clinica2.setId(2L);
        assertThat(clinica1).isNotEqualTo(clinica2);
        clinica1.setId(null);
        assertThat(clinica1).isNotEqualTo(clinica2);
    }
}
