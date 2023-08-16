package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.model.ApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);
    Application dtoToEntity(ApplicationDTO applicationDTO);
    ApplicationDTO entityToDto(Application application);
}
