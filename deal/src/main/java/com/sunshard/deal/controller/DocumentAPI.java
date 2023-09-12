package com.sunshard.deal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface DocumentAPI {

    @PostMapping("/deal/document/{applicationId}/send")
    ResponseEntity<Void> sendDocuments();

    @PostMapping("/deal/document/{applicationId}/sign")
    ResponseEntity<Void> signRequest();

    @PostMapping("/deal/document/{applicationId}/code")
    ResponseEntity<Void> signDocuments();
}
