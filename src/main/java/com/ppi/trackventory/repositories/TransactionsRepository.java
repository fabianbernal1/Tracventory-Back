package com.ppi.trackventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.TransactionTypes;
import com.ppi.trackventory.models.Transactions;

public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
	List<Transactions> findByTransactionType(TransactionTypes transactionType);
}
