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
    public void testAddDeletePerson() throws Exception {
        Person person = new Person("testName", "testSurname", "86");
        person.setBirthDate("10/10/1987");
        personService.add(person);
        List<Person> personList = personService.allPersons(1);
        int lastId = personList.get(personService.allPersons(1).size()-1).getId();

        assert (personService.getById(lastId).getName().equals("testName"));
        personService.delete(personService.getById(lastId));
        assert (personService.getById(lastId)==null);

    }

}