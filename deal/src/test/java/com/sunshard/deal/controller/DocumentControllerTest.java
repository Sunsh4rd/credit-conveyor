package com.sunshard.deal.controller;

import com.sunshard.deal.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    @InjectMocks
    DocumentController documentController;

    @Mock
    DocumentServiceImpl documentService;

    @Test
    void sendDocuments_success() {
        assertEquals(HttpStatus.OK, documentController.sendDocuments(155L).getStatusCode());
        Mockito.verify(documentService, Mockito.times(1)).createDocuments(155L);
    }

    @Test
    void signRequest_success() {
        assertEquals(HttpStatus.OK, documentController.signRequest(155L).getStatusCode());
        Mockito.verify(documentService, Mockito.times(1)).signRequest(155L);
    }

    @Test
    void signDocuments_success() {
        assertEquals(HttpStatus.OK, documentController.signRequest(155L).getStatusCode());
        Mockito.verify(documentService, Mockito.times(1)).signRequest(155L);
    }
}