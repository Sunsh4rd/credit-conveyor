package com.sunshard.deal.service;

import com.sunshard.deal.model.ApplicationDTO;

import java.util.List;

public interface AdminService {
    ApplicationDTO getApplicationById(Long applicationId);

    List<ApplicationDTO> getAllApplications();
}
