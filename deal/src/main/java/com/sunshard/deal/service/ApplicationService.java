package com.sunshard.deal.service;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;

    public Client addClient(LoanApplicationRequestDTO request) {
        Client client = ClientMapper.INSTANCE.dtoToEntity(request);
        return clientRepository.save(client);
    }

    public Application createApplicationForClient(Client client) {
        return applicationRepository.save(Application.builder()
                .status(ApplicationStatus.PREAPPROVAL)
                .creationDate(LocalDateTime.now())
                .client(client)
                .statusHistory(List.of(
                        ApplicationStatusHistoryDTO.builder()
                                .status(ApplicationStatus.PREAPPROVAL)
                                .time(LocalDateTime.now())
                                .changeType(ChangeType.AUTOMATIC)
                                .build()
                ))
                .build()
        );
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("No application with id %d", id))
        );
    }

    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }
}
