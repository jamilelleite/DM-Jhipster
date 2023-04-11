package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Restorateur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RestorateurDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String theme;

    private String zone;

    private String options;

    private SocietaireDTO listname;

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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public SocietaireDTO getListname() {
        return listname;
    }

    public void setListname(SocietaireDTO listname) {
        this.listname = listname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestorateurDTO)) {
            return false;
        }

        RestorateurDTO restorateurDTO = (RestorateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, restorateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestorateurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", theme='" + getTheme() + "'" +
            ", zone='" + getZone() + "'" +
            ", options='" + getOptions() + "'" +
            ", listname=" + getListname() +
            "}";
    }
}
