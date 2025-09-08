package com.ppi.trackventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.TransactionDetails;
import com.ppi.trackventory.models.Transactions;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
    List<TransactionDetails> findByTransaction(Transactions transaction);
}
