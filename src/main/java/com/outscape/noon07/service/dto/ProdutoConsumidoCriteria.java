package com.outscape.noon07.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ProdutoConsumido entity. This class is used in ProdutoConsumidoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /produto-consumidos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProdutoConsumidoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter idproduto;

    private StringFilter nome;

    private FloatFilter valor;

    private IntegerFilter quantidade;

    private IntegerFilter identrada;

    private LongFilter comandaId;

    public ProdutoConsumidoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(IntegerFilter idproduto) {
        this.idproduto = idproduto;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public FloatFilter getValor() {
        return valor;
    }

    public void setValor(FloatFilter valor) {
        this.valor = valor;
    }

    public IntegerFilter getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(IntegerFilter quantidade) {
        this.quantidade = quantidade;
    }

    public IntegerFilter getIdentrada() {
        return identrada;
    }

    public void setIdentrada(IntegerFilter identrada) {
        this.identrada = identrada;
    }

    public LongFilter getComandaId() {
        return comandaId;
    }

    public void setComandaId(LongFilter comandaId) {
        this.comandaId = comandaId;
    }

    @Override
    public String toString() {
        return "ProdutoConsumidoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idproduto != null ? "idproduto=" + idproduto + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (identrada != null ? "identrada=" + identrada + ", " : "") +
                (comandaId != null ? "comandaId=" + comandaId + ", " : "") +
            "}";
    }

}
