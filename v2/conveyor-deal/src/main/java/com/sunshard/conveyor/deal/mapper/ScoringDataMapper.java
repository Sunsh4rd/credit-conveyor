package com.sunshard.conveyor.deal.mapper;


import com.sunshard.conveyor.deal.entity.Application;
import com.sunshard.conveyor.model.FinishRegistrationRequestDto;
import com.sunshard.conveyor.model.ScoringDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ScoringDataMapper {

    @Mapping(target = "birthDate", source = "application.client.birthDate")
    @Mapping(target = "firstName", source = "application.client.firstName")
    @Mapping(target = "lastName", source = "application.client.lastName")
    @Mapping(target = "middleName", source = "application.client.middleName")
    @Mapping(target = "term", source = "application.appliedOffer.term")
    @Mapping(target = "amount", source = "application.appliedOffer.requestedAmount")
    @Mapping(target = "isInsuranceEnabled", source = "application.appliedOffer.isInsuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "application.appliedOffer.isSalaryClient")
    @Mapping(target = "passportNumber", source = "application.client.passport.number")
    @Mapping(target = "passportSeries", source = "application.client.passport.series")
    ScoringDataDto from(Application application, FinishRegistrationRequestDto finishRegistrationRequest);
}
