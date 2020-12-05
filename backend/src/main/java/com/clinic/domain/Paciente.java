package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(unique = true)
    private Contato contato;

    @OneToOne
    @JoinColumn(unique = true)
    private DadosBasicos dadosBasicos;

    @ManyToOne
    @JsonIgnoreProperties("pacientes")
    private Clinica clinica;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Paciente endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public Paciente contato(Contato contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public DadosBasicos getDadosBasicos() {
        return dadosBasicos;
    }

    public Paciente dadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
        return this;
    }

    public void setDadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public Paciente clinica(Clinica clinica) {
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
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            "}";
    }
}
