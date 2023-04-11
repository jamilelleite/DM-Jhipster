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
 * A CooperativeLocal.
 */
@Entity
@Table(name = "cooperative_local")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CooperativeLocal implements Serializable {

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
    @Column(name = "zone", nullable = false)
    private String zone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "coopLos" }, allowSetters = true)
    private CooperativeNational coopNaname;

    @OneToMany(mappedBy = "coopname")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "restorateurs", "livreurs", "clients", "coopname", "consname" }, allowSetters = true)
    private Set<Societaire> socnames = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CooperativeLocal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CooperativeLocal name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return this.zone;
    }

    public CooperativeLocal zone(String zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public CooperativeNational getCoopNaname() {
        return this.coopNaname;
    }

    public void setCoopNaname(CooperativeNational cooperativeNational) {
        this.coopNaname = cooperativeNational;
    }

    public CooperativeLocal coopNaname(CooperativeNational cooperativeNational) {
        this.setCoopNaname(cooperativeNational);
        return this;
    }

    public Set<Societaire> getSocnames() {
        return this.socnames;
    }

    public void setSocnames(Set<Societaire> societaires) {
        if (this.socnames != null) {
            this.socnames.forEach(i -> i.setCoopname(null));
        }
        if (societaires != null) {
            societaires.forEach(i -> i.setCoopname(this));
        }
        this.socnames = societaires;
    }

    public CooperativeLocal socnames(Set<Societaire> societaires) {
        this.setSocnames(societaires);
        return this;
    }

    public CooperativeLocal addSocname(Societaire societaire) {
        this.socnames.add(societaire);
        societaire.setCoopname(this);
        return this;
    }

    public CooperativeLocal removeSocname(Societaire societaire) {
        this.socnames.remove(societaire);
        societaire.setCoopname(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CooperativeLocal)) {
            return false;
        }
        return id != null && id.equals(((CooperativeLocal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CooperativeLocal{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zone='" + getZone() + "'" +
            "}";
    }
}
