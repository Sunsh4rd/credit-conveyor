package com.sunshard.conveyor.deal.mapper;

import com.sunshard.conveyor.deal.entity.Application;
import com.sunshard.conveyor.model.ApplicationDto;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicationMapper {

    Application dtoToEntity(ApplicationDto applicationDto);

    ApplicationDto entityToDto(Application application);
}
