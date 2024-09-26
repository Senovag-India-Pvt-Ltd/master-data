package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    // You can add custom query methods or use @Query annotation here if needed
}
