package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CooperativeLocal;
import com.mycompany.myapp.domain.CooperativeNational;
import com.mycompany.myapp.service.dto.CooperativeLocalDTO;
import com.mycompany.myapp.service.dto.CooperativeNationalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CooperativeLocal} and its DTO {@link CooperativeLocalDTO}.
 */
@Mapper(componentModel = "spring")
public interface CooperativeLocalMapper extends EntityMapper<CooperativeLocalDTO, CooperativeLocal> {
    @Mapping(target = "coopNaname", source = "coopNaname", qualifiedByName = "cooperativeNationalId")
    CooperativeLocalDTO toDto(CooperativeLocal s);

    @Named("cooperativeNationalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CooperativeNationalDTO toDtoCooperativeNationalId(CooperativeNational cooperativeNational);
}
