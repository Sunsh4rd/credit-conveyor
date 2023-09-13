package com.sunshard.deal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Documents", description = "Documents methods")
public interface DocumentAPI {

    @PostMapping("/deal/document/{applicationId}/send")
    ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/sign")
    ResponseEntity<Void> signRequest(@PathVariable Long applicationId);

    @PostMapping("/deal/document/{applicationId}/code")
    ResponseEntity<Void> signDocuments(@PathVariable Long applicationId, @RequestBody Integer sesCode);
}
