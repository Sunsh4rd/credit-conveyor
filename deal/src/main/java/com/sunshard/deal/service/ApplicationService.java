package com.sunshard.deal.service;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.ChangeType;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.repository.ClientRepository;
import com.sunshard.deal.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for working with credit conveyor database
 */

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ClientRepository clientRepository;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;

    /**
     * Saves the <i>client</i> data  from <i>request</i> into the database
     * @param request loan request data
     * @return client data
     * @see Client
     * @see LoanApplicationRequestDTO
     */
    public Client addClient(LoanApplicationRequestDTO request) {
        Client client = ClientMapper.INSTANCE.dtoToEntity(request);
        return clientRepository.save(client);
    }

    /**
     * Creates and saves the <i>application</i> for the certain <i>client</i>
     * @param client loaner data
     * @return created application
     * @see Application
     * @see Client
     */
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

    /**
     * Find <i>application</i> by the provided <i>id</i>
     * @param id provided id
     * @return found application
     * @throws IllegalArgumentException if no application was found
     * @see Application
     */
    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(String.format("No application with id %d", id))
        );
    }

    /**
     * Save the provided <i>application</i> to the <i>database</i>
     * @param application provided application
     * @return application data
     * @see Application
     */
    public Application saveApplication(Application application) {
        return applicationRepository.save(application);
    }

    /**
     * Save the provided <i>credit</i> to the <i>database</i>
     * @param credit provided credit
     * @return credit data
     * @see Credit
     */
    public Credit saveCreditData(Credit credit) {
        return creditRepository.save(credit);
    }
}
