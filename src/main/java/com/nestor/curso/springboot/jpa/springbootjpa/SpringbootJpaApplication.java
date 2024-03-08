package com.nestor.curso.springboot.jpa.springbootjpa;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.nestor.curso.springboot.jpa.springbootjpa.dto.PersonDto;
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
	public void delete2() {

		repository.findAll().forEach(System.out::println);

		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar");
		Long id = sc.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(
		repository::delete, () -> System.out.println("Lo sentimos no existe la persona con ese id!"));
		

		repository.findAll().forEach(System.out::println);

		sc.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {

		Scanner sc = new Scanner(System.in);

		System.out.println("==================== consulta solo el nombre por el id ==================== ");
		System.out.println("Ingrese el id: ");
	
		Long id = sc.nextLong();

		sc.close();

		System.out.println("===== mostrando solo el nombre =====");
		String name = repository.getNameById(id);
		System.out.println(name);

		System.out.println("===== mostrando solo el id =====");
		Long idDb = repository.getIdById(id);
		System.out.println(idDb);

		System.out.println("===== mostrando nombre completo con concat =====");
		String fullName = repository.getFullName(id);
		System.out.println(fullName);

		System.out.println("=====consulta por campos personalizados por el id=====");
		Optional<Object> optionalReg =  repository.obtenerPersonDataById(id);
		if(optionalReg.isPresent()){
		Object[] personReg = (Object[]) optionalReg.orElseThrow();		
		System.out.println("id=" + personReg[0] + ", nombre=" + personReg[1] + ", apellido=" + personReg[2]+ ", lenguaje=" +personReg[3]);
	}
		System.out.println("===== consulta por campos personalizados lista=====");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg ->System.out.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido=" + reg[2]+ ", lenguaje=" +reg[3]));
	}


	@Transactional(readOnly = true)
	public void personalizedQueries2() {

		System.out.println("==================== consulta por objeto persona y lenguaje de programación ==================== ");
		List<Object[]> personsRegs = repository.findAllMixPerson();
		
		personsRegs.forEach(reg ->{
			System.out.println("programmingLanguage=" + reg[1] + ", person=");
		});

		System.out.println("consulta que puebla y devuelve objeto entity de una instancia personalizada");
		List<Person> persons = repository.findAllObjectPersonPersonalized();
		persons.forEach(System.out::println);

		System.out.println("consulta que puebla y devuelve objeto dto de una clase personalizada");
		List<PersonDto> personsDto = repository.findAllPersonDto();
		personsDto.forEach(System.out::println);

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct() {
		System.out.println("consultas con nombres de personas");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("==================== consultas con nombres unicos de personas ====================");
		names = repository.findAllNamesDistinct();
		names.forEach(System.out::println);

		System.out.println("==================== consulta con lenguaje de programación únicas ====================");
		List<String> languages = repository.findAllProgrammingLanguageDistinct();
		languages.forEach(System.out::println);

		System.out.println("==================== consulta con total de lenguajes de programación únicas ====================");
		Long totalLanguage = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("total de lenguajes de programación: " + totalLanguage);
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase() {
	System.out.println("==================== consulta nombres y apellidos de personas ====================");
	List<String> names = repository.findAllFullNameConcat();
	names.forEach(System.out::println);

	System.out.println("==================== consulta nombres y apellidos mayúscula ====================");
	names = repository.findAllFullNameConcatUpper();
	names.forEach(System.out::println);

	System.out.println("==================== consulta nombres y apellidos minúscula ====================");
	names = repository.findAllFullNameConcatLower();
	names.forEach(System.out::println);

	System.out.println("==================== consulta personalizada persona upper y lower case ====================");
	List<Object[]> regs = repository.findAllPersonDataListCase();
	regs.forEach(reg ->System.out.println("id=" + reg[0] + ", nombre=" + reg[1] + ", apellido=" + reg[2]+ ", lenguaje=" +reg[3]));
	
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween() {

		System.out.println("==================== consultas por rangos====================");
		List<Person> persons = repository.findByIdBetween(2L, 5L);
		persons.forEach(System.out::println);

		persons = repository.findByNameBetween("J", "Q");
		persons.forEach(System.out::println);
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
			Person personDB = optionalPerson.orElseThrow();


			System.out.println(personDB);
			System.out.println("Ingrese el lenguaje de programación:");
			String programmingLanguage = sc.nextLine();
			personDB.setProgrammingLanguage(programmingLanguage);
			Person personUpdated = repository.save(personDB);
			System.out.println(personUpdated);
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
		//delete();
		//delete2();
		//personalizedQueries();
		//personalizedQueries2();
		//personalizedQueriesDistinct();
		//personalizedQueriesConcatUpperAndLowerCase();
		personalizedQueriesBetween();
	}

}
