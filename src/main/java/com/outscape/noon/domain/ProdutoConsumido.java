package com.outscape.noon.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProdutoConsumido.
 */
@Entity
@Table(name = "produto_consumido")
@Document(indexName = "produtoconsumido")
public class ProdutoConsumido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idproduto")
    private Integer idproduto;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private Float valor;

    @Column(name = "quantidade")
    private Integer quantidade;

    @ManyToOne
    private Comanda comanda;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdproduto() {
        return idproduto;
    }

    public ProdutoConsumido idproduto(Integer idproduto) {
        this.idproduto = idproduto;
        return this;
    }

    public void setIdproduto(Integer idproduto) {
        this.idproduto = idproduto;
    }

    public String getNome() {
        return nome;
    }

    public ProdutoConsumido nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public ProdutoConsumido valor(Float valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public ProdutoConsumido quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public ProdutoConsumido comanda(Comanda comanda) {
        this.comanda = comanda;
        return this;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
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
        ProdutoConsumido produtoConsumido = (ProdutoConsumido) o;
        if (produtoConsumido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtoConsumido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdutoConsumido{" +
            "id=" + getId() +
            ", idproduto=" + getIdproduto() +
            ", nome='" + getNome() + "'" +
            ", valor=" + getValor() +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
