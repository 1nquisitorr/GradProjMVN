package crimeApp.crimeBase.service;

import crimeApp.crimeBase.dao.PersonDAO;
import crimeApp.crimeBase.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO;

    @Autowired
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    @Transactional
    public List<Person> allPersons(int page) {
        return personDAO.allPersons(page);
    }

    @Override
    @Transactional
    public void add(Person person) {
        personDAO.add(person);
    }

    @Override
    @Transactional
    public void delete(Person person) {
        personDAO.delete(person);
    }

    @Override
    @Transactional
    public void edit(Person person) {
        personDAO.edit(person);
    }

    @Override
    @Transactional
    public Person getById(int id) {
        return personDAO.getById(id);
    }

    @Override
    @Transactional
    public int personsCount() {
        return personDAO.personsCount();
    }

    @Override
    @Transactional
    public boolean checkPerson(String name, String surname) {
        return personDAO.checkPerson(name, surname);
    }

    @Override
    @Transactional

    public List<Person> findPerson(String findName, String findSurname, Date findDateOfBirth) {
        return personDAO.findPerson(findName, findSurname, findDateOfBirth);
    }


}
