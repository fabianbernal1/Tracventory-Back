package com.ppi.trackventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.TransactionTypes;

public interface TransactionTypesRepository extends JpaRepository<TransactionTypes, Integer> {
}
