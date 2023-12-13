package com.sunshard.conveyor.deal.repository;

import com.sunshard.conveyor.deal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
