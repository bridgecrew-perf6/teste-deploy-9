package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Profissional.
 */
@Entity
@Table(name = "profissional")
public class Profissional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cfo")
    private String cfo;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(unique = true)
    private Contato contato;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoProfissional tipoProfissional;

    @OneToOne
    @JoinColumn(unique = true)
    private DadosBasicos dadosBasicos;

    @ManyToOne
    @JsonIgnoreProperties("profissionals")
    private Clinica clinica;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCfo() {
        return cfo;
    }

    public Profissional cfo(String cfo) {
        this.cfo = cfo;
        return this;
    }

    public void setCfo(String cfo) {
        this.cfo = cfo;
    }

    public User getUser() {
        return user;
    }

    public Profissional user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Profissional endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public Profissional contato(Contato contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public TipoProfissional getTipoProfissional() {
        return tipoProfissional;
    }

    public Profissional tipoProfissional(TipoProfissional tipoProfissional) {
        this.tipoProfissional = tipoProfissional;
        return this;
    }

    public void setTipoProfissional(TipoProfissional tipoProfissional) {
        this.tipoProfissional = tipoProfissional;
    }

    public DadosBasicos getDadosBasicos() {
        return dadosBasicos;
    }

    public Profissional dadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
        return this;
    }

    public void setDadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public Profissional clinica(Clinica clinica) {
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
        if (!(o instanceof Profissional)) {
            return false;
        }
        return id != null && id.equals(((Profissional) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profissional{" +
            "id=" + getId() +
            ", cfo='" + getCfo() + "'" +
            "}";
    }
}
