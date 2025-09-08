package com.ppi.trackventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ppi.trackventory.models.Form;

public interface FormRepository extends JpaRepository<Form, Integer> {
}
