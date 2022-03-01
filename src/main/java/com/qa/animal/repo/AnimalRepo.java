package com.qa.animal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.animal.domain.Animal;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Integer> {
	
	List<Animal> findByNameIgnoreCase(String name);
	List<Animal> findByAge(Integer age);
}
