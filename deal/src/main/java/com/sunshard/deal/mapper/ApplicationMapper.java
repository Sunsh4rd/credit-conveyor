package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.model.ApplicationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicationMapper {

    Application dtoToEntity(ApplicationDTO applicationDTO);
    ApplicationDTO entityToDto(Application application);
}
