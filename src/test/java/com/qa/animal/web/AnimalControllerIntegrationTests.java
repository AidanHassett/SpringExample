package com.qa.animal.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.animal.domain.Animal;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:animal-schema.sql", "classpath:animal-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class AnimalControllerIntegrationTests {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void testCreate() throws Exception {
		Animal testAnimal = new Animal(null, 12, "Chyna");
		String testAnimalJSON = this.mapper.writeValueAsString(testAnimal);
		RequestBuilder req = post("/create").contentType(MediaType.APPLICATION_JSON).content(testAnimalJSON);
		
		Animal createdAnimal = new Animal(3, 12, "Chyna");
		String createdAnimalJSON = this.mapper.writeValueAsString(createdAnimal);
		ResultMatcher checkStatus = status().isCreated();
		ResultMatcher checkBody = content().json(createdAnimalJSON);
		
		this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	void testGetAll() throws Exception {
		RequestBuilder req = get("/getAll");
		Collection<Animal> testAnimals = List.of(
			new Animal(1, 9, "Chyna"),
			new Animal(2, 12, "Bill"));
		String testAnimalsJSON = this.mapper.writeValueAsString(testAnimals);
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json(testAnimalsJSON);
		
		this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	void testGet() throws Exception {
		RequestBuilder req = get("/get/1");
		Animal testAnimal = new Animal(1, 9, "Chyna");
		String testAnimalsJSON = this.mapper.writeValueAsString(testAnimal);
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json(testAnimalsJSON);
		
		this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	void testGetNonExistent() throws Exception {
		RequestBuilder req = get("/get/0");
		ResultMatcher checkStatus = status().isNotFound();
		
		this.mvc.perform(req).andExpect(checkStatus);
	}
	
	@Test
	void testGetByName() throws Exception {
		RequestBuilder req = get("/getByName/Chyna");
		Collection<Animal> testAnimals = List.of(new Animal(1, 9, "Chyna"));
		String testAnimalsJSON = this.mapper.writeValueAsString(testAnimals);
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json(testAnimalsJSON);
		
		this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	void testGetByAge() throws Exception {
		RequestBuilder req = get("/getByAge/9");
		Collection<Animal> testAnimals = List.of(new Animal(1, 9, "Chyna"));
		String testAnimalsJSON = this.mapper.writeValueAsString(testAnimals);
		ResultMatcher checkStatus = status().isOk();
		ResultMatcher checkBody = content().json(testAnimalsJSON);
		
		this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	void testReplace() throws Exception {
		Animal testAnimal = new Animal(1, 12, "Chyna");
		String testAnimalJSON = this.mapper.writeValueAsString(testAnimal);
		RequestBuilder req = put("/replace").contentType(MediaType.APPLICATION_JSON).content(testAnimalJSON);
		
		ResultMatcher checkStatus = status().isOk();
		
		this.mvc.perform(req).andExpect(checkStatus);
	}
	
	@Test
	void testReplaceNonExistent() throws Exception {
		Animal testAnimal = new Animal(3, 12, "Chyna");
		String testAnimalJSON = this.mapper.writeValueAsString(testAnimal);
		RequestBuilder req = put("/replace").contentType(MediaType.APPLICATION_JSON).content(testAnimalJSON);
		
		ResultMatcher checkStatus = status().isCreated();
		
		this.mvc.perform(req).andExpect(checkStatus);
	}
	
	@Test
	void testDelete() throws Exception {
		RequestBuilder req = delete("/delete/2");
		
		ResultMatcher checkStatus = status().isNoContent();
		ResultMatcher checkBody = content().string("");
		
		this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
	
	@Test
	void testDeleteNonExistent() throws Exception {
		RequestBuilder req = delete("/delete/0");
		
		ResultMatcher checkStatus = status().isNotFound();
		
		this.mvc.perform(req).andExpect(checkStatus);
	}
}
