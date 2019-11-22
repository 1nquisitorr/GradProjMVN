package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.Person;

import java.sql.Date;
import java.util.List;

public interface PersonDAO {
    List allPersons(int page);

    void add(Person person);

    void delete(Person person);

    void edit(Person person);

    Person getById(int id);

    int personsCount();

    List<Person> findPerson(String findName, String findSurname, Date findDateOfBirth);

    boolean checkPerson(String name, String surname);
}
