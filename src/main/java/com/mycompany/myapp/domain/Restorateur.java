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
 * A Restorateur.
 */
@Entity
@Table(name = "restorateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Restorateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "theme")
    private String theme;

    @Column(name = "zone")
    private String zone;

    @Column(name = "options")
    private String options;

    @OneToMany(mappedBy = "restname")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "restname", "cliname" }, allowSetters = true)
    private Set<Panier> paniers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "restorateurs", "livreurs", "clients", "coopname", "consname" }, allowSetters = true)
    private Societaire listname;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Restorateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Restorateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTheme() {
        return this.theme;
    }

    public Restorateur theme(String theme) {
        this.setTheme(theme);
        return this;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getZone() {
        return this.zone;
    }

    public Restorateur zone(String zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getOptions() {
        return this.options;
    }

    public Restorateur options(String options) {
        this.setOptions(options);
        return this;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Set<Panier> getPaniers() {
        return this.paniers;
    }

    public void setPaniers(Set<Panier> paniers) {
        if (this.paniers != null) {
            this.paniers.forEach(i -> i.setRestname(null));
        }
        if (paniers != null) {
            paniers.forEach(i -> i.setRestname(this));
        }
        this.paniers = paniers;
    }

    public Restorateur paniers(Set<Panier> paniers) {
        this.setPaniers(paniers);
        return this;
    }

    public Restorateur addPanier(Panier panier) {
        this.paniers.add(panier);
        panier.setRestname(this);
        return this;
    }

    public Restorateur removePanier(Panier panier) {
        this.paniers.remove(panier);
        panier.setRestname(null);
        return this;
    }

    public Societaire getListname() {
        return this.listname;
    }

    public void setListname(Societaire societaire) {
        this.listname = societaire;
    }

    public Restorateur listname(Societaire societaire) {
        this.setListname(societaire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restorateur)) {
            return false;
        }
        return id != null && id.equals(((Restorateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restorateur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", theme='" + getTheme() + "'" +
            ", zone='" + getZone() + "'" +
            ", options='" + getOptions() + "'" +
            "}";
    }
}
