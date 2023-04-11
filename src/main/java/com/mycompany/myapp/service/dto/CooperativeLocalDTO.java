package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CooperativeLocal} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CooperativeLocalDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String zone;

    private CooperativeNationalDTO coopNaname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public CooperativeNationalDTO getCoopNaname() {
        return coopNaname;
    }

    public void setCoopNaname(CooperativeNationalDTO coopNaname) {
        this.coopNaname = coopNaname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CooperativeLocalDTO)) {
            return false;
        }

        CooperativeLocalDTO cooperativeLocalDTO = (CooperativeLocalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cooperativeLocalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CooperativeLocalDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zone='" + getZone() + "'" +
            ", coopNaname=" + getCoopNaname() +
            "}";
    }
}
