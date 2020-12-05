package com.clinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.Instant;

import com.clinic.domain.enumeration.TipoMovimento;

/**
 * A Movimentacao.
 */
@Entity
@Table(name = "movimentacao")
public class Movimentacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "data")
    private Instant data;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimento")
    private TipoMovimento tipoMovimento;

    @ManyToOne
    @JsonIgnoreProperties("movimentacaos")
    private Saldo saldo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Movimentacao valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Instant getData() {
        return data;
    }

    public Movimentacao data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public Movimentacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoMovimento getTipoMovimento() {
        return tipoMovimento;
    }

    public Movimentacao tipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
        return this;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public Movimentacao saldo(Saldo saldo) {
        this.saldo = saldo;
        return this;
    }

    public void setSaldo(Saldo saldo) {
        this.saldo = saldo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movimentacao)) {
            return false;
        }
        return id != null && id.equals(((Movimentacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", data='" + getData() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipoMovimento='" + getTipoMovimento() + "'" +
            "}";
    }
}
