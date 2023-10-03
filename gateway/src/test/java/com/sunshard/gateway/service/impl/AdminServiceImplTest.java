package com.sunshard.gateway.service.impl;

import com.sunshard.gateway.client.AdminFeignClient;
import com.sunshard.gateway.model.ApplicationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    AdminFeignClient adminFeignClient;

    @Test
    void getApplicationById_success() {
        Mockito.when(adminFeignClient.getApplicationById(160L))
                        .thenReturn(ResponseEntity.ok(ApplicationDTO.builder().build()));
        adminService.getApplicationById(160L);
        Mockito.verify(adminFeignClient, Mockito.times(1)).getApplicationById(160L);
    }

    @Test
    void getAllApplications_success() {
        Mockito.when(adminFeignClient.getAllApplications())
                .thenReturn(ResponseEntity.of(Optional.of(List.of(ApplicationDTO.builder().build()))));
        adminService.getAllApplications();
        Mockito.verify(adminFeignClient, Mockito.times(1)).getAllApplications();
    }
}