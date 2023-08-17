package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.model.CreditDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreditMapper {
//    CreditMapper INSTANCE = Mappers.getMapper(CreditMapper.class);

    @Mapping(target = "insuranceEnabled", source = "creditDTO.isInsuranceEnabled")
    @Mapping(target = "salaryClient", source = "creditDTO.isSalaryClient")
    Credit dtoToEntity(CreditDTO creditDTO);

    @Mapping(target = "isInsuranceEnabled", source = "credit.insuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "credit.salaryClient")
    CreditDTO entityToDto(Credit credit);
}
