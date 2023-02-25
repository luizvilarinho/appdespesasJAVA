package com.appdespesas.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appdespesas.app.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>{

	public User findByEmail(String email);
	
	public Optional<User> findById(UUID id);
	
	
}
