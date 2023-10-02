package com.sunshard.deal.service.impl;

import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    ApplicationMapper applicationMapper;

    @Mock
    CreditMapper creditMapper;

    @Mock
    ClientMapper clientMapper;

    @Test
    void getCreditData() {
//        assertThrows(ApplicationNotFoundException.class, () -> clientService.getCreditData(155L));
//        Mockito.verify(applicationMapper, Mockito.times(1)).entityToDto(applicationRepository.findById(155L).get());
    }

    @Test
    void getSesCode() {
    }
}