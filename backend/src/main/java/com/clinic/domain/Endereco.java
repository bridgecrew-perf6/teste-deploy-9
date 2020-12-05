package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Endereco.
 */
@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cep")
    private Long cep;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "uf")
    private String uf;

    @Column(name = "data")
    private Instant data;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Clinica clinica;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Paciente paciente;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Profissional profissional;

    @OneToOne(mappedBy = "endereco")
    @JsonIgnore
    private Funcionario funcionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public Endereco cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Endereco logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public Endereco bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Long getCep() {
        return cep;
    }

    public Endereco cep(Long cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public Long getNumero() {
        return numero;
    }

    public Endereco numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getUf() {
        return uf;
    }

    public Endereco uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Instant getData() {
        return data;
    }

    public Endereco data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public Endereco clinica(Clinica clinica) {
        this.clinica = clinica;
        return this;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Endereco paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Endereco profissional(Profissional profissional) {
        this.profissional = profissional;
        return this;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Endereco funcionario(Funcionario funcionario) {
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
        if (!(o instanceof Endereco)) {
            return false;
        }
        return id != null && id.equals(((Endereco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Endereco{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cep=" + getCep() +
            ", numero=" + getNumero() +
            ", uf='" + getUf() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
