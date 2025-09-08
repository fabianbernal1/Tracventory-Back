package com.ppi.trackventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ppi.trackventory.models.TransactionOrigins;

public interface TransactionOriginsRepository extends JpaRepository<TransactionOrigins, Integer> {
}