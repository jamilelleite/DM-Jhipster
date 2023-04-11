package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CooperativeNational;
import com.mycompany.myapp.service.dto.CooperativeNationalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CooperativeNational} and its DTO {@link CooperativeNationalDTO}.
 */
@Mapper(componentModel = "spring")
public interface CooperativeNationalMapper extends EntityMapper<CooperativeNationalDTO, CooperativeNational> {}
