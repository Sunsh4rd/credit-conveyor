package com.sunshard.deal.controller;

import com.sunshard.deal.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    AdminController adminController;

    @Mock
    AdminServiceImpl adminService;

    @Test
    void getApplicationById_success() {
        assertEquals(HttpStatus.OK, adminController.getApplicationById(155L).getStatusCode());
        Mockito.verify(adminService, Mockito.times(1)).getApplicationById(155L);
    }

    @Test
    void getAllApplications_success() {
        assertEquals(HttpStatus.OK, adminController.getAllApplications().getStatusCode());
        assertInstanceOf(List.class, adminService.getAllApplications());
    }
}