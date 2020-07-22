package com.training.library.repositories;

import com.training.library.entities.MathDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathDetailsRepository extends JpaRepository<MathDetails, Integer> {
}
