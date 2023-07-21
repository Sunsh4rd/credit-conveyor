package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.ConveyorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api
public class ConveyorController {

    private final ConveyorService conveyorService;

    @PostMapping("/conveyor/offers")
    @ApiOperation("create loan offers")
    @ResponseBody
    public List<LoanOfferDTO> createLoanOffers(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequestDto) {
        return conveyorService.createLoanOffers(loanApplicationRequestDto);
    }

    @PostMapping("/conveyor/calculation")
    @ApiOperation("make calculations")
    @ResponseBody
    public List<LoanOfferDTO> calculation(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDto) {
        return List.of(
                new LoanOfferDTO(1L, BigDecimal.valueOf(2), BigDecimal.valueOf(3),4,BigDecimal.valueOf(5), BigDecimal.valueOf(6),true,true),
                new LoanOfferDTO(1L, BigDecimal.valueOf(2), BigDecimal.valueOf(3),4,BigDecimal.valueOf(5), BigDecimal.valueOf(6),true,true),
                new LoanOfferDTO(1L, BigDecimal.valueOf(2), BigDecimal.valueOf(3),4,BigDecimal.valueOf(5), BigDecimal.valueOf(6),true,true),
                new LoanOfferDTO(1L, BigDecimal.valueOf(2), BigDecimal.valueOf(3),4,BigDecimal.valueOf(5), BigDecimal.valueOf(6),true,true)
        );
    }
}
