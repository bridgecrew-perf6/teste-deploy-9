package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Contato.
 */
@Entity
@Table(name = "contato")
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "telefone")
    private Long telefone;

    @Column(name = "celular")
    private Long celular;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "contato")
    @JsonIgnore
    private Clinica clinica;

    @OneToOne(mappedBy = "contato")
    @JsonIgnore
    private Paciente paciente;

    @OneToOne(mappedBy = "contato")
    @JsonIgnore
    private Profissional profissional;

    @OneToOne(mappedBy = "contato")
    @JsonIgnore
    private Funcionario funcionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTelefone() {
        return telefone;
    }

    public Contato telefone(Long telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Long getCelular() {
        return celular;
    }

    public Contato celular(Long celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public Contato email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public Contato clinica(Clinica clinica) {
        this.clinica = clinica;
        return this;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Contato paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Contato profissional(Profissional profissional) {
        this.profissional = profissional;
        return this;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Contato funcionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        return this;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contato)) {
            return false;
        }
        return id != null && id.equals(((Contato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contato{" +
            "id=" + getId() +
            ", telefone=" + getTelefone() +
            ", celular=" + getCelular() +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
