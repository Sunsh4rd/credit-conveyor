package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.model.ApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ApplicationMapper {

    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);
    Application dtoToEntity(ApplicationDTO applicationDTO);

    @Mapping(target = "applicationId", source = "application.applicationId")
    @Mapping(target = "client", source = "application.client")
    @Mapping(target = "credit", source = "application.credit")
    @Mapping(target = "status", source = "application.status")
    @Mapping(target = "creationDate", source = "application.creationDate")
    @Mapping(target = "appliedOffer", source = "application.appliedOffer")
    @Mapping(target = "signDate", source = "application.signDate")
    @Mapping(target = "sesCode", source = "application.sesCode")
    @Mapping(target = "statusHistory", source = "application.statusHistory")
    ApplicationDTO entityToDto(Application application);
}
