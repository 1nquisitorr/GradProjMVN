package ru.crimeApp.crimeBase.Service;

import crimeApp.crimeBase.model.Person;
import crimeApp.crimeBase.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.crimeApp.crimeBase.config.HibernateConfigTest;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@WebAppConfiguration
public class PersonServiceTest {


    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Resource
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }


    @Test
    public void testGetById() {
        assert (personService.getById(5).getName().equals("Alex"));
        assert (personService.getById(5).getSurname().equals("Petrov"));
    }


    @Test
    public void testAddDeletePerson() throws Exception {
        Person person = new Person("testName", "testSurname", "86");
        person.setBirthDate("10/10/1987");
        personService.add(person);
        List<Person> personList = personService.allPersons();
        int lastId = personList.get(personService.allPersons().size()-1).getId();

        assert (personService.getById(lastId).getName().equals("testName"));
        personService.delete(personService.getById(lastId));
        assert (personService.getById(lastId)==null);
    }


    @Test
    public void testAllPersons() {

        assert (personService.allPersons().size() > 0);
    }

    @Test
    public void TestCheckPerson() {
        assert (personService.checkPerson("Alex", "Pertov"));
    }

    @Test
    public void testFindPerson() {
        List<Person> personList = personService.findPerson("Alex", "Petrov", new Date(1969 - 12 - 31));
        assert (personList.get(0).getName().equals("Alex"));
        assert (personList.get(0).getSurname().equals("Petrov"));
        List<Person> personList2 = personService.findPerson("", "", new Date(1));
        assert (personList2.size() == 0);
    }


    @Test
    public void testEdit() throws ParseException {
        Person person = new Person("testName", "testSurname", "86");
        person.setBirthDate("10/10/1987");
        personService.add(person);

        person.setName("testName2");
        person.setSurname("testSurname2");
        person.setCrimes("85");
        personService.edit(person);

        List<Person> personList = personService.allPersons();
        int lastId = personList.get(personService.allPersons().size() - 1).getId();

        assert (personService.getById(lastId).getName().equals("testName2"));
        assert (personService.getById(lastId).getSurname().equals("testSurname2"));
        assert (personService.getById(lastId).getCrimes().equals("85"));
        personService.delete(personService.getById(lastId));

    }

    @Test
            public void testPersonCount()
    {
        assert (personService.allPersons().size()==personService.personsCount());
    }


    public void testGetPersonConnections() throws ParseException {
        Person person = new Person("testName", "testSurname", "86");
        person.setBirthDate("10/10/1987");
        person.setPersonConnections("5");
        personService.add(person);

        List<Person> personList = personService.allPersons();
        int lastId = personList.get(personService.allPersons().size() - 1).getId();

        assert (personService.getById(lastId).getPersonConnections().equals("5"));
        personService.delete(personService.getById(lastId));

    }

}