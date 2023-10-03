package com.sunshard.deal.service;

public interface DocumentService {
    void createDocuments(Long applicationId);

    void signRequest(Long applicationId);

    void signDocuments(Long applicationId, Integer sesCode);
}
