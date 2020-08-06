package com.training.library.repositories;

import com.training.library.entities.Book;
import com.training.library.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    @Modifying
    @Query("DELETE FROM Book b WHERE b.id = :id")
    void customDelete(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE Book b SET b.state = :state, b.active = :active WHERE b.id = :id")
    void updateStateAndActiveById(@Param("id") Integer id, @Param("state") StateEnum state, @Param("active") Boolean active);
}
