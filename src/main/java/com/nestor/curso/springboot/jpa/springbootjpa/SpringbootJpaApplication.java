package com.nestor.curso.springboot.jpa.springbootjpa;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.nestor.curso.springboot.jpa.springbootjpa.entities.Person;
import com.nestor.curso.springboot.jpa.springbootjpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Transactional(readOnly = true)
	public void findOne() {

		/* 
		Person person = null;
		Optional<Person> optionalPerson = repository.findOneName("Pepe");
		//String namePerson = optionalPerson.get().getName();
		//if(!optionalPerson.isEmpty()) {
		if(optionalPerson.isPresent()) {
			person = optionalPerson.get();			
		}
		System.out.println(person);
		*/
		repository.findByNameContaining("hn").ifPresent(System.out::println);
	}
	@Transactional(readOnly = true) // Cuando son consultas
	public void list() {
	//List<Person> persons = (List<Person>) repository.findAll();
	List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");

	persons.stream().forEach(person->System.out.println(person));

	List<Object[]> personsValues = repository.obtenerPersonDataByProgrammingLanguage("Java");
	personsValues.stream().forEach(person->System.out.println(person[0] + " es experto en " + person[1]));
//	personsValues.stream().forEach(person->System.out.println(person));


	}

	
	@Transactional
	public void create() {

		Scanner sc = new Scanner (System.in);
		System.out.println("Ingrese el nombre:");
		String name = sc.nextLine();
		System.out.println("Ingrese el apellido:");
		String lastname = sc.nextLine();
		System.out.println("Ingrese el lenguaje de programación:");
		String programmingLanguage = sc.nextLine();
		sc.close();

		Person person = new Person(null, name, lastname, programmingLanguage);
		
		Person personNew = repository.save(person);
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(System.out::println);
	}

	@Transactional
	public void delete() {

		repository.findAll().forEach(System.out::println);

		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar");
		Long id = sc.nextLong();
		repository.deleteById(id);

		repository.findAll().forEach(System.out::println);

		sc.close();
	}

	@Transactional
	public void update() {

		Scanner sc = new Scanner (System.in);
		System.out.println("Ingrese el id de la persona: ");
		Long id = sc.nextLong();
		sc.nextLine();
		Optional<Person> optionalPerson = repository.findById(id);

		//optionalPerson.ifPresent(person-> {
			if(optionalPerson.isPresent()){
			Person person = optionalPerson.orElseThrow();
			System.out.println(person);
			System.out.println("Ingrese el lenguaje de programación:");
			String programmingLanguage = sc.nextLine();
			person.setProgrammingLanguage(programmingLanguage);
			Person personDb = repository.save(person);
			System.out.println(personDb);
			} else {
				System.out.println("El usuario no existe ");
			}
			
	//	});
		sc.close();
	}

	@Override
	public void run(String... args) throws Exception {

		//findOne();
		//create();
		//update();
		delete();
	}

}
