package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Livreurs} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LivreursDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

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
        if (!(o instanceof LivreursDTO)) {
            return false;
        }

        LivreursDTO livreursDTO = (LivreursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, livreursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivreursDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", listname=" + getListname() +
            "}";
    }
}
