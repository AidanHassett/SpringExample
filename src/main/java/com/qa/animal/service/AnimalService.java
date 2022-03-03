package com.qa.animal.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.animal.domain.Animal;
import com.qa.animal.repo.AnimalRepo;

@Service
public class AnimalService implements ServiceInterface<Animal>{
	
	private AnimalRepo animals;
	
	@Autowired
	public AnimalService(AnimalRepo repo) {
		this.animals = repo;
	}
	
	public Animal create(Animal inAnimal) {
		return this.animals.save(inAnimal);
	}
	
	public Collection<Animal> getAll() {
		return this.animals.findAll();
	}
	
	public Animal get(Integer id) {
		try {
			return animals.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Collection<Animal> getByName(String name) {
		return animals.findByNameIgnoreCase(name);
	}
	
	public Collection<Animal> getByAge(Integer age) {
		return animals.findByAge(age);
	}
	
	public boolean replace(Animal inAnimal) {
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
	
	public boolean remove(Integer id) {
		if (this.animals.existsById(id)) {
			this.animals.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
}
