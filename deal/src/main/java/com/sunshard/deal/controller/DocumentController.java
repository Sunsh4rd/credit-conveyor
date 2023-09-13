package com.sunshard.deal.controller;

import com.sunshard.deal.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentAPI {

    private final DocumentService documentService;

    @Override
    public ResponseEntity<Void> sendDocuments(Long applicationId) {
        System.out.println(applicationId);
        documentService.createDocuments(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signRequest(Long applicationId) {
        System.out.println(applicationId);
        documentService.signRequest(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signDocuments(Long applicationId, Integer sesCode) {
        System.out.println(applicationId);
        System.out.println(sesCode);
        documentService.signDocuments(applicationId, sesCode);
        return ResponseEntity.ok().build();
    }

}
