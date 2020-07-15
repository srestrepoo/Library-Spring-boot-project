package com.training.library.repositories;

import com.training.library.entities.HistoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryDetailsRepository extends JpaRepository<HistoryDetails, Integer> {
}
