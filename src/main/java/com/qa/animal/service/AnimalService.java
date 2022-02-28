package com.qa.animal.service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.qa.animal.domain.Animal;

@Service
public class AnimalService {
	private Map<Integer, Animal> animals = new TreeMap<Integer, Animal>();
	
	public boolean createAnimal(Animal inAnimal) {
		if (animals.containsKey(inAnimal.getId())) {
			return false;
		} else {
			this.animals.put(inAnimal.getId(), inAnimal);
			return true;
		}
	}
	
	public Collection<Animal> getAllAnimals() {
		return this.animals.values();
	}
	
	public Animal getAnimal(Integer id) {
		return animals.get(id);
	}
	
	public Animal replaceAnimal(Animal inAnimal) {
		return this.animals.put(inAnimal.getId(), inAnimal);
	}
	
	public Animal removeAnimal(Integer id) {
		return this.animals.remove(id);
	}
}
