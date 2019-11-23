package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    private SessionFactory sessionFactory;
    private List<Person> person;

    public PersonDAOImpl() {
        this.person = Arrays.asList(new Person("Alex", "Ivanov",new Date(1), "126"));
    }


    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Person> allPersons(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person").list();
    }

    @Override
    public void add(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Override
    public void delete(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(person);
    }

    @Override
    public void edit(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.update(person);
    }

    @Override
    public Person getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Override
    public int personsCount() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select count(*) from Person", Number.class).getSingleResult().intValue();
    }

    @Override
    public boolean checkPerson(String name, String surname) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        query = session.createQuery("from Person where name = " + "\'" + name + "\'" + " AND surname = " + "\'" + surname + "\'");
        return query.list().isEmpty();
    }

    @Override
    public List<Person> findPerson(String findName, String findSurname, Date findDateOfBirth) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        query = session.createQuery("from Person where name = " + "\'" + findName + "\'" + " OR surname = " + "\'" + findSurname + "\'" + " OR birthDate = " + "\'" + findDateOfBirth + "\'");
        return query.list();
    }

    @Override
    public Person getPersonByName(String name) throws Exception {
      return person.stream().filter(u->u.getName().equals(name)).findAny().orElse(null);
    }


}
