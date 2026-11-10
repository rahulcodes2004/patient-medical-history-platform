package com.example.patienthistory;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface patientrepository extends JpaRepository<patient, Long> {

    List<patient> findByFullNameContainingIgnoreCase(String fullName);

}
