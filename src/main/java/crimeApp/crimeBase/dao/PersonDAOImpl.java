package crimeApp.crimeBase.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import crimeApp.crimeBase.model.Person;

import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Person> allPersons(int page) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person").setFirstResult(10 * (page - 1)).setMaxResults(10).list();
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
    public boolean checkPerson(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query;
        query = session.createQuery("from Person where name = :name");
        query.setParameter("name", name);
        return query.list().isEmpty();
    }
}
