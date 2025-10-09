package com.porfolio.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.porfolio.portfolio.model.Paint;

public interface PaintRepository extends CrudRepository<Paint, Long> {

    Optional<List<Paint>> findByCategory(String category);

    Optional<List<Paint>> findByStatus(String status);

    @Query("SELECT p FROM Paint p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Paint> searchByQuery(@Param("query") String query);

    @Query("SELECT DISTINCT category FROM Paint")
    List<String> findCategory();

}