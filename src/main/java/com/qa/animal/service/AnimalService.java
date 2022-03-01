package com.qa.animal.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.animal.domain.Animal;
import com.qa.animal.repo.AnimalRepo;

@Service
public class AnimalService {
	
	private AnimalRepo animals;
	
	@Autowired
	public AnimalService(AnimalRepo repo) {
		this.animals = repo;
	}
	
	public Animal createAnimal(Animal inAnimal) {
		return this.animals.save(inAnimal);
	}
	
	public Collection<Animal> getAllAnimals() {
		return this.animals.findAll();
	}
	
	public Animal getAnimal(Integer id) {
		try {
			return animals.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Collection<Animal> getAnimalsByName(String name) {
		return animals.findByNameIgnoreCase(name);
	}
	
	public Collection<Animal> getAnimalsByAge(Integer age) {
		return animals.findByAge(age);
	}
	
	public boolean replaceAnimal(Animal inAnimal) {
		Animal existing;
		boolean exists;
		try {
			existing = this.animals.findById(inAnimal.getId()).get();
			existing.setAge(inAnimal.getAge());
			existing.setName(inAnimal.getName());
			exists = true;
		} catch (NoSuchElementException e) {
			existing = new Animal(inAnimal);
			exists = false;
		}
		this.animals.save(existing);
		return exists;
	}
	
	public boolean removeAnimal(Integer id) {
		if (this.animals.existsById(id)) {
			this.animals.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
