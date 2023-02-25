package com.appdespesas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appdespesas.app.Entity.Teste;

@Repository
public interface TesteRepository extends CrudRepository<Teste, Integer>	 {

}
