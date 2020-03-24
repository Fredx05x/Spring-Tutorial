package com.example.spring.dao;

import com.example.spring.models.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

  private static List<Person> DB = new ArrayList<>();

  @Override
  public Person insertPerson(UUID id, Person person) {
    Person personToAdd = new Person(id, person.getName());
    DB.add(personToAdd);
    return personToAdd;
  }

  @Override
  public List<Person> selectAllPeople() {
    return DB;
  }

  @Override
  public Optional<Person> selectPersonById(UUID id) {
    return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
  }

  @Override
  public int deletePersonById(UUID id) {
    Optional<Person> person = selectPersonById(id);
    if (person.isEmpty()) {
      return 0;
    }
    DB.remove(person.get());
    return 1;
  }

  @Override
  public int updatePersonById(UUID id, Person person) {
    return selectPersonById(id)
        .map(
            p -> {
              int index = DB.indexOf(p);
              if (index >= 0) {
                DB.set(index, new Person(id, person.getName()));
                return 1;
              }
              return 0;
            })
        .orElse(0);
  }
}
