package com.nestor.curso.springboot.jpa.springbootjpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nestor.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.nestor.curso.springboot.jpa.springbootjpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

  @Query("select p from Person p where p.id in ?1")
  public List <Person> getPersonByIds(List<Long> ids);

  @Query("select p.name, length(p.name) from Person p where length(p.name)=(select min(length(p.name)) from Person p)")
  public List<Object []> getShorterName() ; 

  @Query("select p from Person p where p.id=(select max(p.id) from Person p)")
  public Optional<Person> getLastRegistration();

  @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
  public Object getResumeneAggregationFunction();

  @Query("select min(length(p.name)) from Person p")
  public Integer getMinLengthName();

  @Query("select max(length(p.name)) from Person p")
  public Integer getMaxLengthName();

  @Query("select p.name, length(p.name) from Person p")
  public List<Object[]> getPersonNameLength();


  @Query("select count(p) from Person p")
  Long gettotalPerson();

  @Query("select min(p.id) from Person p")
  Long getminId();

  @Query("select max(p.id) from Person p")
  Long getmaxId();

  List<Person> findAllByOrderByNameDescLastnameAsc();

    @Query("select p from Person p order by p.name, p.lastname desc")
    List<Person> getAllOrdered();

    List<Person> findByIdBetweenOrderByNameAsc(Long id1, Long id2);

    List<Person> findByNameBetweenOrderByNameDescLastnameDesc(String name1, String name2);

    @Query("select p from Person p where p.id between ?1 and ?2 order by p.name desc")
    List<Person> findAllBetweenId(Long id, Long id2);
   // @Query("select p from Person p where p.id between 2 and 5")
  //  List<Person> findAllBetweenId();

    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name asc,  p.lastname desc")
    List<Person> findAllBetweenName(String c1, String c2);
   // @Query("select p from Person p where p.name between 'J' and 'Q' ")
   // List<Person> findAllBetweenName(); // Lo estamos pasando hardcodeado


    @Query("select p.id, upper(p.name), lower(p.lastname), upper(p.programmingLanguage) from Person p")
    List<Object[]> findAllPersonDataListCase();

    @Query("select upper(p.name||' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatUpper();

    @Query("select lower(concat(p.name, ' ', p.lastname)) from Person p")
    List<String> findAllFullNameConcatLower();

    //@Query("select CONCAT (p.name, ' ', p.lastname) from Person p")
    @Query("select p.name|| ' ' || p.lastname from Person p")
    List<String> findAllFullNameConcat();

    @Query("select distinct (p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguageDistinct();

    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long findAllProgrammingLanguageDistinctCount();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select distinct (p.name) from Person p")
    List<String> findAllNamesDistinct();


    @Query("select new com.nestor.curso.springboot.jpa.springbootjpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllObjectPersonPersonalized();

    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    @Query("select p.id from Person p where p.id=?1")
    Long getIdById(Long id);

    @Query("select CONCAT (p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullName(Long id);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);
    
    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPerson();
    
    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonDataList();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=?1")
    Optional <Object> obtenerPersonDataById(Long id);

    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> obtenerPersonData(String name);

    
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);
    
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);
}
