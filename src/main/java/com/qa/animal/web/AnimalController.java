package com.qa.animal.web;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.animal.domain.Animal;
import com.qa.animal.service.AnimalService;

@RestController

public class AnimalController {
	
	AnimalService animalService = new AnimalService();
	
	@GetMapping("/helloWorld")
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/create")
	public ResponseEntity<Animal> createAnimal(@RequestBody Animal inAnimal) {
		HttpStatus status;
		if (this.animalService.createAnimal(inAnimal)) {
			status = HttpStatus.CREATED;
		} else {
			status = HttpStatus.CONFLICT;
		}
		Animal an = this.animalService.getAnimal(inAnimal.getId());
		return new ResponseEntity<Animal>(an, status);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Collection<Animal>> getAll() {
		return ResponseEntity.ok(this.animalService.getAllAnimals());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Animal> getAnimal(@PathVariable Integer id) {
		Animal an = this.animalService.getAnimal(id);
		HttpStatus status;
		if (an == null) {
			status = HttpStatus.NOT_FOUND;
		} else {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Animal>(an, status);
	}
	
	@PutMapping("/replace/{id}")
	public ResponseEntity<Animal> replaceAnimal(@PathVariable Integer id, @RequestBody Animal inAnimal) {
		Animal an = this.animalService.replaceAnimal(inAnimal);
		HttpStatus status;
		if (an == null) {
			status = HttpStatus.CREATED;
		} else {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Animal>(an, status);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAnimal(@PathVariable Integer id) {
		Animal an = this.animalService.removeAnimal(id);
		if (an == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
