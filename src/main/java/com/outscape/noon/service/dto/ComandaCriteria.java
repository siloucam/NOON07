package com.outscape.noon.service.dto;

import java.io.Serializable;
import com.outscape.noon.domain.enumeration.StatusComanda;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Comanda entity. This class is used in ComandaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /comandas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ComandaCriteria implements Serializable {
    /**
     * Class for filtering StatusComanda
     */
    public static class StatusComandaFilter extends Filter<StatusComanda> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter numero;

    private LocalDateFilter data;

    private FloatFilter total;

    private StatusComandaFilter status;

    private LongFilter produtoId;

    private LongFilter clienteId;

    public ComandaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumero() {
        return numero;
    }

    public void setNumero(IntegerFilter numero) {
        this.numero = numero;
    }

    public LocalDateFilter getData() {
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
    }

    public FloatFilter getTotal() {
        return total;
    }

    public void setTotal(FloatFilter total) {
        this.total = total;
    }

    public StatusComandaFilter getStatus() {
        return status;
    }

    public void setStatus(StatusComandaFilter status) {
        this.status = status;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getClienteId() {
        return clienteId;
    }

    public void setClienteId(LongFilter clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "ComandaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (clienteId != null ? "clienteId=" + clienteId + ", " : "") +
            "}";
    }

}
