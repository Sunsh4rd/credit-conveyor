package com.sunshard.deal.controller;

import com.sunshard.deal.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks
    ClientController clientController;

    @Mock
    ClientServiceImpl clientService;

    @Test
    void getCreditData_success() {
        assertEquals(HttpStatus.OK, clientController.getCreditData(155L).getStatusCode());
        Mockito.verify(clientService, Mockito.times(1)).getCreditData(155L);
    }

    @Test
    void getSesCode_success() {
        assertEquals(HttpStatus.OK, clientController.getSesCode(155L).getStatusCode());
        Mockito.verify(clientService, Mockito.times(1)).getSesCode(155L);
    }
}