package com.outscape.noon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.outscape.noon.domain.enumeration.StatusComanda;

/**
 * A Comanda.
 */
@Entity
@Table(name = "comanda")
@Document(indexName = "comanda")
public class Comanda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "total")
    private Float total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusComanda status;

    @OneToMany(mappedBy = "comanda")
    @JsonIgnore
    private Set<ProdutoConsumido> produtos = new HashSet<>();

    @ManyToOne
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Comanda numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public Comanda data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Float getTotal() {
        return total;
    }

    public Comanda total(Float total) {
        this.total = total;
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public StatusComanda getStatus() {
        return status;
    }

    public Comanda status(StatusComanda status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusComanda status) {
        this.status = status;
    }

    public Set<ProdutoConsumido> getProdutos() {
        return produtos;
    }

    public Comanda produtos(Set<ProdutoConsumido> produtoConsumidos) {
        this.produtos = produtoConsumidos;
        return this;
    }

    public Comanda addProduto(ProdutoConsumido produtoConsumido) {
        this.produtos.add(produtoConsumido);
        produtoConsumido.setComanda(this);
        return this;
    }

    public Comanda removeProduto(ProdutoConsumido produtoConsumido) {
        this.produtos.remove(produtoConsumido);
        produtoConsumido.setComanda(null);
        return this;
    }

    public void setProdutos(Set<ProdutoConsumido> produtoConsumidos) {
        this.produtos = produtoConsumidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Comanda cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comanda comanda = (Comanda) o;
        if (comanda.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comanda.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comanda{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", data='" + getData() + "'" +
            ", total=" + getTotal() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
