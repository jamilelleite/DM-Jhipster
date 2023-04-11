package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Conseil;
import com.mycompany.myapp.domain.CooperativeLocal;
import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.service.dto.ConseilDTO;
import com.mycompany.myapp.service.dto.CooperativeLocalDTO;
import com.mycompany.myapp.service.dto.SocietaireDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Societaire} and its DTO {@link SocietaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocietaireMapper extends EntityMapper<SocietaireDTO, Societaire> {
    @Mapping(target = "coopname", source = "coopname", qualifiedByName = "cooperativeLocalId")
    @Mapping(target = "consname", source = "consname", qualifiedByName = "conseilId")
    SocietaireDTO toDto(Societaire s);

    @Named("cooperativeLocalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CooperativeLocalDTO toDtoCooperativeLocalId(CooperativeLocal cooperativeLocal);

    @Named("conseilId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConseilDTO toDtoConseilId(Conseil conseil);
}
