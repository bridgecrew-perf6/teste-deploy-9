package com.clinic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Clinica.
 */
@Entity
@Table(name = "clinica")
public class Clinica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cnpj", nullable = false)
    private Long cnpj;

    @Column(name = "data")
    private Instant data;

    @OneToOne
    @JoinColumn(unique = true)
    private Saldo saldo;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(unique = true)
    private Contato contato;

    @OneToMany(mappedBy = "clinica")
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(mappedBy = "clinica")
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToMany(mappedBy = "clinica")
    private Set<Profissional> profissionals = new HashSet<>();

    @OneToMany(mappedBy = "clinica")
    private Set<Consulta> consultas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "clinica_proprietario",
               joinColumns = @JoinColumn(name = "clinica_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "proprietario_id", referencedColumnName = "id"))
    private Set<Proprietario> proprietarios = new HashSet<>();

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

    public Clinica nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public Clinica cnpj(Long cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public Instant getData() {
        return data;
    }

    public Clinica data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public Clinica saldo(Saldo saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Clinica endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public Clinica contato(Contato contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Clinica pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Clinica addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setClinica(this);
        return this;
    }

    public Clinica removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setClinica(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Set<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public Clinica funcionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public Clinica addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setClinica(this);
        return this;
    }

    public Clinica removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setClinica(null);
        return this;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Set<Profissional> getProfissionals() {
        return profissionals;
    }

    public Clinica profissionals(Set<Profissional> profissionals) {
        this.profissionals = profissionals;
        return this;
    }

    public Clinica addProfissional(Profissional profissional) {
        this.profissionals.add(profissional);
        profissional.setClinica(this);
        return this;
    }

    public Clinica removeProfissional(Profissional profissional) {
        this.profissionals.remove(profissional);
        profissional.setClinica(null);
        return this;
    }

    public void setProfissionals(Set<Profissional> profissionals) {
        this.profissionals = profissionals;
    }

    public Set<Consulta> getConsultas() {
        return consultas;
    }

    public Clinica consultas(Set<Consulta> consultas) {
        this.consultas = consultas;
        return this;
    }

    public Clinica addConsulta(Consulta consulta) {
        this.consultas.add(consulta);
        consulta.setClinica(this);
        return this;
    }

    public Clinica removeConsulta(Consulta consulta) {
        this.consultas.remove(consulta);
        consulta.setClinica(null);
        return this;
    }

    public void setConsultas(Set<Consulta> consultas) {
        this.consultas = consultas;
    }

    public Set<Proprietario> getProprietarios() {
        return proprietarios;
    }

    public Clinica proprietarios(Set<Proprietario> proprietarios) {
        this.proprietarios = proprietarios;
        return this;
    }

    public Clinica addProprietario(Proprietario proprietario) {
        this.proprietarios.add(proprietario);
        proprietario.getClinicas().add(this);
        return this;
    }

    public Clinica removeProprietario(Proprietario proprietario) {
        this.proprietarios.remove(proprietario);
        proprietario.getClinicas().remove(this);
        return this;
    }

    public void setProprietarios(Set<Proprietario> proprietarios) {
        this.proprietarios = proprietarios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clinica)) {
            return false;
        }
        return id != null && id.equals(((Clinica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Clinica{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cnpj=" + getCnpj() +
            ", data='" + getData() + "'" +
            "}";
    }
}
