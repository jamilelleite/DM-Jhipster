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
 * A Societaire.
 */
@Entity
@Table(name = "societaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Societaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "directeur")
    private String directeur;

    @OneToMany(mappedBy = "listname")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paniers", "listname" }, allowSetters = true)
    private Set<Restorateur> restorateurs = new HashSet<>();

    @OneToMany(mappedBy = "listname")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "listname" }, allowSetters = true)
    private Set<Livreurs> livreurs = new HashSet<>();

    @OneToMany(mappedBy = "listname")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paniers", "listname" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "coopNaname", "socnames" }, allowSetters = true)
    private CooperativeLocal coopname;

    @ManyToOne(optional = false)
    @NotNull
    private Conseil consname;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Societaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Societaire nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDirecteur() {
        return this.directeur;
    }

    public Societaire directeur(String directeur) {
        this.setDirecteur(directeur);
        return this;
    }

    public void setDirecteur(String directeur) {
        this.directeur = directeur;
    }

    public Set<Restorateur> getRestorateurs() {
        return this.restorateurs;
    }

    public void setRestorateurs(Set<Restorateur> restorateurs) {
        if (this.restorateurs != null) {
            this.restorateurs.forEach(i -> i.setListname(null));
        }
        if (restorateurs != null) {
            restorateurs.forEach(i -> i.setListname(this));
        }
        this.restorateurs = restorateurs;
    }

    public Societaire restorateurs(Set<Restorateur> restorateurs) {
        this.setRestorateurs(restorateurs);
        return this;
    }

    public Societaire addRestorateur(Restorateur restorateur) {
        this.restorateurs.add(restorateur);
        restorateur.setListname(this);
        return this;
    }

    public Societaire removeRestorateur(Restorateur restorateur) {
        this.restorateurs.remove(restorateur);
        restorateur.setListname(null);
        return this;
    }

    public Set<Livreurs> getLivreurs() {
        return this.livreurs;
    }

    public void setLivreurs(Set<Livreurs> livreurs) {
        if (this.livreurs != null) {
            this.livreurs.forEach(i -> i.setListname(null));
        }
        if (livreurs != null) {
            livreurs.forEach(i -> i.setListname(this));
        }
        this.livreurs = livreurs;
    }

    public Societaire livreurs(Set<Livreurs> livreurs) {
        this.setLivreurs(livreurs);
        return this;
    }

    public Societaire addLivreurs(Livreurs livreurs) {
        this.livreurs.add(livreurs);
        livreurs.setListname(this);
        return this;
    }

    public Societaire removeLivreurs(Livreurs livreurs) {
        this.livreurs.remove(livreurs);
        livreurs.setListname(null);
        return this;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setListname(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setListname(this));
        }
        this.clients = clients;
    }

    public Societaire clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public Societaire addClient(Client client) {
        this.clients.add(client);
        client.setListname(this);
        return this;
    }

    public Societaire removeClient(Client client) {
        this.clients.remove(client);
        client.setListname(null);
        return this;
    }

    public CooperativeLocal getCoopname() {
        return this.coopname;
    }

    public void setCoopname(CooperativeLocal cooperativeLocal) {
        this.coopname = cooperativeLocal;
    }

    public Societaire coopname(CooperativeLocal cooperativeLocal) {
        this.setCoopname(cooperativeLocal);
        return this;
    }

    public Conseil getConsname() {
        return this.consname;
    }

    public void setConsname(Conseil conseil) {
        this.consname = conseil;
    }

    public Societaire consname(Conseil conseil) {
        this.setConsname(conseil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Societaire)) {
            return false;
        }
        return id != null && id.equals(((Societaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Societaire{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", directeur='" + getDirecteur() + "'" +
            "}";
    }
}
