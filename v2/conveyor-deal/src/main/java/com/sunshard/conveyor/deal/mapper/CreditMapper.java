package com.sunshard.conveyor.deal.mapper;

import com.sunshard.conveyor.deal.entity.Credit;
import com.sunshard.conveyor.model.CreditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreditMapper {

    @Mapping(target = "insuranceEnabled", source = "creditDto.isInsuranceEnabled")
    @Mapping(target = "salaryClient", source = "creditDto.isSalaryClient")
    Credit dtoToEntity(CreditDto creditDto);

    @Mapping(target = "isInsuranceEnabled", source = "credit.insuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "credit.salaryClient")
    CreditDto entityToDto(Credit credit);
}
