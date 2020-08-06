package com.training.library.repositories;

import com.training.library.entities.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer> {

    @Modifying
    @Query("DELETE FROM Register r WHERE r.book.id = :bookId")
    void deleteByBookId(@Param("bookId") Integer bookId);

}
