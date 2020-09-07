package com.training.library.repositories;

import com.training.library.entities.ExternalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExternalDetailsRepository extends JpaRepository<ExternalDetails, Integer> {

    @Modifying
    @Query("DELETE FROM ExternalDetails ed WHERE ed.id IN :idList")
    void deleteDetails(List<Integer> idList);

}
