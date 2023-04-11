package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CooperativeNational.
 */
@Entity
@Table(name = "cooperative_national")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CooperativeNational implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "fournisseur", nullable = false)
    private String fournisseur;

    @OneToMany(mappedBy = "coopNaname")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "coopNaname", "socnames" }, allowSetters = true)
    private Set<CooperativeLocal> coopLos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CooperativeNational id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CooperativeNational name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFournisseur() {
        return this.fournisseur;
    }

    public CooperativeNational fournisseur(String fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Set<CooperativeLocal> getCoopLos() {
        return this.coopLos;
    }

    public void setCoopLos(Set<CooperativeLocal> cooperativeLocals) {
        if (this.coopLos != null) {
            this.coopLos.forEach(i -> i.setCoopNaname(null));
        }
        if (cooperativeLocals != null) {
            cooperativeLocals.forEach(i -> i.setCoopNaname(this));
        }
        this.coopLos = cooperativeLocals;
    }

    public CooperativeNational coopLos(Set<CooperativeLocal> cooperativeLocals) {
        this.setCoopLos(cooperativeLocals);
        return this;
    }

    public CooperativeNational addCoopLo(CooperativeLocal cooperativeLocal) {
        this.coopLos.add(cooperativeLocal);
        cooperativeLocal.setCoopNaname(this);
        return this;
    }

    public CooperativeNational removeCoopLo(CooperativeLocal cooperativeLocal) {
        this.coopLos.remove(cooperativeLocal);
        cooperativeLocal.setCoopNaname(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CooperativeNational)) {
            return false;
        }
        return id != null && id.equals(((CooperativeNational) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CooperativeNational{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fournisseur='" + getFournisseur() + "'" +
            "}";
    }
}
