package com.appdespesas.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appdespesas.app.Entity.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, UUID> {
	
	public Optional<Expense> findById(Integer id);

	public void deleteById(Integer id);

	public List<Expense> findAllByUser_id(UUID id);
}
