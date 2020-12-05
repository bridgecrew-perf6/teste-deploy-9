package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.clinic.domain.enumeration.Sexo;

/**
 * A DadosBasicos.
 */
@Entity
@Table(name = "dados_basicos")
public class DadosBasicos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private Instant dataNascimento;

    @Column(name = "data")
    private Instant data;

    @Column(name = "cpf")
    private Long cpf;

    @Column(name = "identidade")
    private Long identidade;

    @Column(name = "pai")
    private String pai;

    @Column(name = "mae")
    private String mae;

    @Column(name = "foto")
    private String foto;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;

    @OneToOne(mappedBy = "dadosBasicos")
    @JsonIgnore
    private Paciente paciente;

    @OneToOne(mappedBy = "dadosBasicos")
    @JsonIgnore
    private Profissional profissional;

    @OneToOne(mappedBy = "dadosBasicos")
    @JsonIgnore
    private Funcionario funcionario;

    @OneToOne(mappedBy = "dadosBasicos")
    @JsonIgnore
    private Proprietario proprietario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public DadosBasicos nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public DadosBasicos dataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Instant getData() {
        return data;
    }

    public DadosBasicos data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Long getCpf() {
        return cpf;
    }

    public DadosBasicos cpf(Long cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getIdentidade() {
        return identidade;
    }

    public DadosBasicos identidade(Long identidade) {
        this.identidade = identidade;
        return this;
    }

    public void setIdentidade(Long identidade) {
        this.identidade = identidade;
    }

    public String getPai() {
        return pai;
    }

    public DadosBasicos pai(String pai) {
        this.pai = pai;
        return this;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getMae() {
        return mae;
    }

    public DadosBasicos mae(String mae) {
        this.mae = mae;
        return this;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getFoto() {
        return foto;
    }

    public DadosBasicos foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public DadosBasicos sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public DadosBasicos paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public DadosBasicos profissional(Profissional profissional) {
        this.profissional = profissional;
        return this;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public DadosBasicos funcionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        return this;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public DadosBasicos proprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
        return this;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DadosBasicos)) {
            return false;
        }
        return id != null && id.equals(((DadosBasicos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DadosBasicos{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", data='" + getData() + "'" +
            ", cpf=" + getCpf() +
            ", identidade=" + getIdentidade() +
            ", pai='" + getPai() + "'" +
            ", mae='" + getMae() + "'" +
            ", foto='" + getFoto() + "'" +
            ", sexo='" + getSexo() + "'" +
            "}";
    }
}
