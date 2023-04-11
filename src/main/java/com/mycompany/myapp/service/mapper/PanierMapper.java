package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Panier;
import com.mycompany.myapp.domain.Restorateur;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.PanierDTO;
import com.mycompany.myapp.service.dto.RestorateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Panier} and its DTO {@link PanierDTO}.
 */
@Mapper(componentModel = "spring")
public interface PanierMapper extends EntityMapper<PanierDTO, Panier> {
    @Mapping(target = "restname", source = "restname", qualifiedByName = "restorateurId")
    @Mapping(target = "cliname", source = "cliname", qualifiedByName = "clientId")
    PanierDTO toDto(Panier s);

    @Named("restorateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RestorateurDTO toDtoRestorateurId(Restorateur restorateur);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
