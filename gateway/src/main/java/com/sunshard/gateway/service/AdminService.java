package com.sunshard.gateway.service;

import com.sunshard.gateway.model.ApplicationDTO;

import java.util.List;

public interface AdminService {

    ApplicationDTO getApplicationById(Long applicationId);

    List<ApplicationDTO> getAllApplications();
}
