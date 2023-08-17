package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Client;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

//    @Mapping(target = "firstName", source = "request.firstName")
//    @Mapping(target = "lastName", source = "request.lastName")
//    @Mapping(target = "middleName", source = "request.middleName")
//    @Mapping(target = "birthDate", source = "request.birthDate")
//    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "passport.series", source = "request.passportSeries")
    @Mapping(target = "passport.number", source = "request.passportNumber")
    Client dtoToEntity(LoanApplicationRequestDTO request);
}
