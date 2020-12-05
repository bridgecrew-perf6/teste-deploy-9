package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Proprietario.
 */
@Entity
@Table(name = "proprietario")
public class Proprietario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private DadosBasicos dadosBasicos;

    @ManyToMany(mappedBy = "proprietarios")
    @JsonIgnore
    private Set<Clinica> clinicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Proprietario user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DadosBasicos getDadosBasicos() {
        return dadosBasicos;
    }

    public Proprietario dadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
        return this;
    }

    public void setDadosBasicos(DadosBasicos dadosBasicos) {
        this.dadosBasicos = dadosBasicos;
    }

    public Set<Clinica> getClinicas() {
        return clinicas;
    }

    public Proprietario clinicas(Set<Clinica> clinicas) {
        this.clinicas = clinicas;
        return this;
    }

    public Proprietario addClinica(Clinica clinica) {
        this.clinicas.add(clinica);
        clinica.getProprietarios().add(this);
        return this;
    }

    public Proprietario removeClinica(Clinica clinica) {
        this.clinicas.remove(clinica);
        clinica.getProprietarios().remove(this);
        return this;
    }

    public void setClinicas(Set<Clinica> clinicas) {
        this.clinicas = clinicas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proprietario)) {
            return false;
        }
        return id != null && id.equals(((Proprietario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Proprietario{" +
            "id=" + getId() +
            "}";
    }
}
