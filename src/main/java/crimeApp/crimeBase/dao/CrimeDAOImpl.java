package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.Crime;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CrimeDAOImpl implements CrimeDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Crime getByID(int id) {

        Session session = sessionFactory.getCurrentSession();
        return session.get(Crime.class, id);

    }

    @Override
    @Transactional
    public List<Crime> getAllCrimes() {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Crime").list();
    }

}
