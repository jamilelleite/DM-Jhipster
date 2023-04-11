package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Livreurs;
import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.service.dto.LivreursDTO;
import com.mycompany.myapp.service.dto.SocietaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Livreurs} and its DTO {@link LivreursDTO}.
 */
@Mapper(componentModel = "spring")
public interface LivreursMapper extends EntityMapper<LivreursDTO, Livreurs> {
    @Mapping(target = "listname", source = "listname", qualifiedByName = "societaireId")
    LivreursDTO toDto(Livreurs s);

    @Named("societaireId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietaireDTO toDtoSocietaireId(Societaire societaire);
}
