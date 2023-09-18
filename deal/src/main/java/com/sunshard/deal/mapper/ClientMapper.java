package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Client;
import com.sunshard.deal.model.ClientDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {
    @Mapping(target = "passport.series", source = "request.passportSeries")
    @Mapping(target = "passport.number", source = "request.passportNumber")
    Client dtoToEntity(LoanApplicationRequestDTO request);

    ClientDTO entityToDto(Client client);
}
