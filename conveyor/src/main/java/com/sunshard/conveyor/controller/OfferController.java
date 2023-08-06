package com.sunshard.conveyor.controller;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController implements OfferAPI {

    private final OfferService offerService;
    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO loanApplicationRequest) {
        List<LoanOfferDTO> loanOffers = null;
        try {
            logger.info(
                    "OfferController received loan request:\n{} at {}",
                    loanApplicationRequest,
                    LocalDateTime.now()
            );
            loanOffers = offerService.createLoanOffers(loanApplicationRequest);
            logger.info(
                    "Loan offers were created:\n{} at {}",
                    loanOffers,
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(loanOffers, HttpStatus.OK);
        } catch (RuntimeException exception) {
            logger.info(
                    "Loan offers could not be created as invalid data was provided {}",
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(loanOffers, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LoanOfferDTO> handleMethodArgumentNotValidException() {
        logger.info(
                "Loan offers could not be created as invalid data was provided {}",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
