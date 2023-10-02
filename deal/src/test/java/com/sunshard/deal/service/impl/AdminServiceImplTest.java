package com.sunshard.deal.service.impl;

import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @InjectMocks
    AdminServiceImpl adminService;

    @Mock
    ApplicationRepository applicationRepository;

    @Test
    void getApplicationById_throwsApplicationNotFoundException() {
        assertThrows(ApplicationNotFoundException.class, () -> adminService.getApplicationById(160L));
        Mockito.verify(applicationRepository, Mockito.times(1)).findById(160L);
    }

    @Test
    void getAllApplications_success() {
        assertInstanceOf(List.class, applicationRepository.findAll());
        Mockito.verify(applicationRepository, Mockito.times(1)).findAll();
    }
}