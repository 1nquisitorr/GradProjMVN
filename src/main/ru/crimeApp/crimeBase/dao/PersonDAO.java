package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.Person;
import crimeApp.crimeBase.model.UserLog;

import java.sql.Date;
import java.util.List;

public interface PersonDAO {
    List allPersons();

    void add(Person person);

    void delete(Person person);

    void edit(Person person);

    Person getById(int id);

    int personsCount();

    List<Person> findPerson(String findName, String findSurname, Date findDateOfBirth);

    boolean checkPerson(String name, String surname);

    String getPersonConnections(int id);



}
