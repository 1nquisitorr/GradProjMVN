package crimeApp.crimeBase.service;

import crimeApp.crimeBase.model.Person;

import java.sql.Date;
import java.util.List;

public interface PersonService {
    List<Person> allPersons();

    void add(Person person);

    void delete(Person person);

    void edit(Person person);

    Person getById(int id);

    int personsCount();

    boolean checkPerson(String name, String surname);

    List<Person> findPerson(String findName, String findSurname, Date findDateOfBirth);

//    boolean checkPersonPrence(Person person) throws Exception;

    String getPersonConnections(int id);

//    void setPersonConnections(int id, String connection);

}
