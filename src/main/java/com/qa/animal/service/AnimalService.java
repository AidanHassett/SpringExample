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
	
	public boolean createAnimal(Animal inAnimal) {
		if (animals.existsById(inAnimal.getId())) {
			return false;
		} else {
			this.animals.save(inAnimal);
			return true;
		}
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
