package crimeApp.crimeBase.dao;

import crimeApp.crimeBase.model.UserLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLogDaoImpl implements UserLogDao {
    SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserLog> usersActions() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UserLog").list();
    }

    @Override
    public void add(UserLog userLog) {

        Session session = sessionFactory.getCurrentSession();
        session.persist(userLog);

    }
}
