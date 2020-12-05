package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;

/**
 * A Procedimento.
 */
@Entity
@Table(name = "procedimento")
public class Procedimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "cid")
    private String cid;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @OneToOne(mappedBy = "procedimento")
    @JsonIgnore
    private Consulta consulta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Procedimento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCid() {
        return cid;
    }

    public Procedimento cid(String cid) {
        this.cid = cid;
        return this;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Procedimento valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public Procedimento consulta(Consulta consulta) {
        this.consulta = consulta;
        return this;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Procedimento)) {
            return false;
        }
        return id != null && id.equals(((Procedimento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Procedimento{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", cid='" + getCid() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
