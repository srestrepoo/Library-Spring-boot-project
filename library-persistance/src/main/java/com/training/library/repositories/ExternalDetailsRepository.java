package com.training.library.repositories;

import com.training.library.entities.ExternalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalDetailsRepository extends JpaRepository<ExternalDetails, Integer> {
}
