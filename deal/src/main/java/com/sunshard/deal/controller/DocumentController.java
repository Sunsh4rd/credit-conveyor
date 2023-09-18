package com.sunshard.deal.controller;

import com.sunshard.deal.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentAPI {

    private final DocumentService documentService;
    private static final Logger logger = LogManager.getLogger(DocumentController.class.getName());

    @Override
    public ResponseEntity<Void> sendDocuments(Long applicationId) {
        logger.info("Received send documents request for application {}", applicationId);
        documentService.createDocuments(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signRequest(Long applicationId) {
        logger.info("Received sign documents request for application {}", applicationId);
        documentService.signRequest(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signDocuments(Long applicationId, Integer sesCode) {
        logger.info("Documents were signed for application {}", applicationId);
        documentService.signDocuments(applicationId, sesCode);
        return ResponseEntity.ok().build();
    }

}
