package com.outscape.noon.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.outscape.noon.domain.enumeration.Setor;

/**
 * A ExtendUser.
 */
@Entity
@Table(name = "extend_user")
@Document(indexName = "extenduser")
public class ExtendUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "setor")
    private Setor setor;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Setor getSetor() {
        return setor;
    }

    public ExtendUser setor(Setor setor) {
        this.setor = setor;
        return this;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public User getUser() {
        return user;
    }

    public ExtendUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        ExtendUser extendUser = (ExtendUser) o;
        if (extendUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extendUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtendUser{" +
            "id=" + getId() +
            ", setor='" + getSetor() + "'" +
            "}";
    }
}
