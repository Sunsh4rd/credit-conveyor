package com.sunshard.conveyor.deal.mapper;

import com.sunshard.conveyor.deal.entity.Client;
import com.sunshard.conveyor.model.ClientDto;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {
    @Mapping(target = "passport.series", source = "request.passportSeries")
    @Mapping(target = "passport.number", source = "request.passportNumber")
    Client dtoToEntity(LoanApplicationRequestDto request);

    ClientDto entityToDto(Client client);
}
