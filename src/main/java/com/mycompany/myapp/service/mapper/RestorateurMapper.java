package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Restorateur;
import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.service.dto.RestorateurDTO;
import com.mycompany.myapp.service.dto.SocietaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Restorateur} and its DTO {@link RestorateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface RestorateurMapper extends EntityMapper<RestorateurDTO, Restorateur> {
    @Mapping(target = "listname", source = "listname", qualifiedByName = "societaireId")
    RestorateurDTO toDto(Restorateur s);

    @Named("societaireId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietaireDTO toDtoSocietaireId(Societaire societaire);
}
