package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Societaire} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SocietaireDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String directeur;

    private CooperativeLocalDTO coopname;

    private ConseilDTO consname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDirecteur() {
        return directeur;
    }

    public void setDirecteur(String directeur) {
        this.directeur = directeur;
    }

    public CooperativeLocalDTO getCoopname() {
        return coopname;
    }

    public void setCoopname(CooperativeLocalDTO coopname) {
        this.coopname = coopname;
    }

    public ConseilDTO getConsname() {
        return consname;
    }

    public void setConsname(ConseilDTO consname) {
        this.consname = consname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocietaireDTO)) {
            return false;
        }

        SocietaireDTO societaireDTO = (SocietaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, societaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocietaireDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", directeur='" + getDirecteur() + "'" +
            ", coopname=" + getCoopname() +
            ", consname=" + getConsname() +
            "}";
    }
}
