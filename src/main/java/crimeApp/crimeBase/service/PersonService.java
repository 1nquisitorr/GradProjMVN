package crimeApp.crimeBase.service;

import crimeApp.crimeBase.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> allPersons(int page);
    void add(Person person);
    void delete(Person person);
    void edit(Person person);
    Person getById(int id);

    int personsCount();

    boolean checkPerson(String name);
}
