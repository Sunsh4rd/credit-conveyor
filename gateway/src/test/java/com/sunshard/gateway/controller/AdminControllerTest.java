package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.ApplicationDTO;
import com.sunshard.gateway.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    AdminServiceImpl adminService;

    @Test
    void getApplicationById_success() {
        Mockito.when(adminService.getApplicationById(160L))
                .thenReturn(ApplicationDTO.builder()
                                .applicationId(160L)
                        .build());
        adminController.getApplicationById(160L);
        Mockito.verify(adminService, Mockito.times(1)).getApplicationById(160L);
    }

    @Test
    void getAllApplications_success() {
        Mockito.when(adminService.getAllApplications())
                .thenReturn(List.of(ApplicationDTO.builder().applicationId(160L).build(),
                        ApplicationDTO.builder().applicationId(161L).build()));
        adminController.getAllApplications();
        Mockito.verify(adminService, Mockito.times(1)).getAllApplications();
    }
}