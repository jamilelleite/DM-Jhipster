package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Conseil;
import com.mycompany.myapp.service.dto.ConseilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Conseil} and its DTO {@link ConseilDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConseilMapper extends EntityMapper<ConseilDTO, Conseil> {}
