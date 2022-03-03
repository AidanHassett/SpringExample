package com.qa.animal.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

public class AnimalController{
	
	AnimalService animalService;
	
	@Autowired
	public AnimalController(AnimalService serv) {
		super();
		this.animalService = serv;
	}
	
	@GetMapping("/helloWorld")
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/create")
	public ResponseEntity<Animal> createAnimal(@RequestBody Animal inAnimal) {
		Animal an = this.animalService.create(inAnimal);
		return new ResponseEntity<Animal>(an, HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Collection<Animal>> getAll() {
		return ResponseEntity.ok(this.animalService.getAll());
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Animal> getAnimal(@PathVariable Integer id) {
		Animal an = this.animalService.get(id);
		HttpStatus status;
		if (an == null) {
			status = HttpStatus.NOT_FOUND;
		} else {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Animal>(an, status);
	}
	
	@GetMapping("/getByName/{name}")
	public ResponseEntity<Collection<Animal>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(this.animalService.getByName(name));
	}
	
	@GetMapping("/getByAge/{age}")
	public ResponseEntity<Collection<Animal>> getByAge(@PathVariable Integer age) {
		return ResponseEntity.ok(this.animalService.getByAge(age));
	}
	
	@PutMapping("/replace")
	public ResponseEntity<?> replaceAnimal(@RequestBody Animal inAnimal) {
		HttpStatus status;
		if (this.animalService.replace(inAnimal)) {
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAnimal(@PathVariable Integer id) {
		if (this.animalService.remove(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
