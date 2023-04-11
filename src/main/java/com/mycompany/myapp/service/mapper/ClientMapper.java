package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.SocietaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "listname", source = "listname", qualifiedByName = "societaireId")
    ClientDTO toDto(Client s);

    @Named("societaireId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietaireDTO toDtoSocietaireId(Societaire societaire);
}
