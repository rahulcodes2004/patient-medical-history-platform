
package com.example.patienthistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthOverviewRepository
        extends JpaRepository<HealthOverview, Long> {

    Optional<HealthOverview> findByPatientId(Long patientId);
}

