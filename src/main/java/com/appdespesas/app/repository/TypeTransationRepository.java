package com.appdespesas.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.appdespesas.app.Entity.Expense;
import com.appdespesas.app.Entity.TypeTransation;

public interface TypeTransationRepository extends CrudRepository<TypeTransation, Integer>{
	
	public Optional<TypeTransation> findById(Integer id);
}
