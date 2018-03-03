package com.outscape.noon.service.dto;

import java.io.Serializable;
import com.outscape.noon.domain.enumeration.Setor;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the ExtendUser entity. This class is used in ExtendUserResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /extend-users?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExtendUserCriteria implements Serializable {
    /**
     * Class for filtering Setor
     */
    public static class SetorFilter extends Filter<Setor> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private SetorFilter setor;

    private LongFilter userId;

    public ExtendUserCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SetorFilter getSetor() {
        return setor;
    }

    public void setSetor(SetorFilter setor) {
        this.setor = setor;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ExtendUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (setor != null ? "setor=" + setor + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
