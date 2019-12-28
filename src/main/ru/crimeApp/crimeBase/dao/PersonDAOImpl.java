package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    private SessionFactory sessionFactory;



    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Person> allPersons() {
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
        query = session.createQuery(String.format("from Person where name = '%s' AND surname = '%s'", name, surname));
        return query.list().isEmpty();
    }

    @Override
    public List<Person> findPerson(String findName, String findSurname, Date findDateOfBirth) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        query = session.createQuery(String.format("from Person where Name = '%s' AND LastName = '%s' AND BirthDate = '%s'", findName, findSurname, findDateOfBirth));
        return query.list();
    }


    @Override
    public String getPersonConnections(int id) {
        return getById(id).getPersonConnections();
    }



}
