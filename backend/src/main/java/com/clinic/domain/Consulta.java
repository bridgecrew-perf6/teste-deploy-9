package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.clinic.domain.enumeration.StatusConsulta;

/**
 * A Consulta.
 */
@Entity
@Table(name = "consulta")
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_consulta", nullable = false)
    private Instant dataConsulta;

    @NotNull
    @Column(name = "id_profissional", nullable = false)
    private Long idProfissional;

    @NotNull
    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;

    @Column(name = "status_pagamento")
    private Boolean statusPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_consulta")
    private StatusConsulta statusConsulta;

    @OneToOne
    @JoinColumn(unique = true)
    private Procedimento procedimento;

    @ManyToOne
    @JsonIgnoreProperties("consultas")
    private Clinica clinica;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataConsulta() {
        return dataConsulta;
    }

    public Consulta dataConsulta(Instant dataConsulta) {
        this.dataConsulta = dataConsulta;
        return this;
    }

    public void setDataConsulta(Instant dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public Long getIdProfissional() {
        return idProfissional;
    }

    public Consulta idProfissional(Long idProfissional) {
        this.idProfissional = idProfissional;
        return this;
    }

    public void setIdProfissional(Long idProfissional) {
        this.idProfissional = idProfissional;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public Consulta idPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
        return this;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Boolean isStatusPagamento() {
        return statusPagamento;
    }

    public Consulta statusPagamento(Boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
        return this;
    }

    public void setStatusPagamento(Boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public Consulta statusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
        return this;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public Consulta procedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
        return this;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public Consulta clinica(Clinica clinica) {
        this.clinica = clinica;
        return this;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consulta)) {
            return false;
        }
        return id != null && id.equals(((Consulta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Consulta{" +
            "id=" + getId() +
            ", dataConsulta='" + getDataConsulta() + "'" +
            ", idProfissional=" + getIdProfissional() +
            ", idPaciente=" + getIdPaciente() +
            ", statusPagamento='" + isStatusPagamento() + "'" +
            ", statusConsulta='" + getStatusConsulta() + "'" +
            "}";
    }
}
