package com.sunshard.gateway.client;

import com.sunshard.gateway.model.ApplicationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "AdminFeignClient", url = "${deal-address}")
public interface AdminFeignClient {

    @GetMapping("/deal/admin/application/{applicationId}")
    ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long applicationId);

    @GetMapping("/deal/admin/application")
    ResponseEntity<List<ApplicationDTO>> getAllApplications();
}
