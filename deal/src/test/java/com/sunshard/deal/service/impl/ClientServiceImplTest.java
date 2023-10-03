package com.sunshard.deal.service.impl;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.model.ApplicationDTO;
import com.sunshard.deal.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    ApplicationRepository applicationRepository;

    @Test
    void getCreditData_throwsApplicationNotFoundException() {
        assertThrows(ApplicationNotFoundException.class, () -> clientService.getCreditData(160L));
        Mockito.verify(applicationRepository, Mockito.times(1)).findById(160L);
    }

    @Test
    void getSesCode_success() {
        Mockito.when(applicationRepository.findById(160L)).thenReturn(Optional.of(Application.builder()
                        .applicationId(160L)
                        .sesCode("1442")
                .build()));

        assertEquals("1442", applicationRepository.findById(160L).get().getSesCode());
        Mockito.verify(applicationRepository, Mockito.times(1)).findById(160L);
    }
}