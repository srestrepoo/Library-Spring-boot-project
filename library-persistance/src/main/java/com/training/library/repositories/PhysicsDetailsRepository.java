package com.training.library.repositories;

import com.training.library.entities.PhysicsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicsDetailsRepository extends JpaRepository<PhysicsDetails, Integer> {
}
