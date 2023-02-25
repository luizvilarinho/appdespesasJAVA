package com.appdespesas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appdespesas.app.Entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>{

	Tag deleteAllById(Integer id);

}
