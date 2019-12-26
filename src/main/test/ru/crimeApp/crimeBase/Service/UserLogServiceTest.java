package ru.crimeApp.crimeBase.Service;

import crimeApp.crimeBase.model.UserLog;
import crimeApp.crimeBase.service.UserLogService;
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
import java.util.Date;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@WebAppConfiguration
public class UserLogServiceTest {

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Resource
    private UserLogService userLogService;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void testUserLogServiceGetAll() throws Exception {
        assert (userLogService.usersActions().size() > 0);
    }


    @Test
    public void testUserLogServiceGetDescription() throws Exception {
        assert (userLogService.usersActions().get(0).getAction().equals("add"));
        assert (userLogService.usersActions().get(0).getVisitedPage().equals("look"));
        assert (userLogService.usersActions().get(0).getUserName().equals("admin"));
    }

    @Test
    public void testUserLogServiceAdd() throws Exception {

        UserLog userLog = new UserLog("admin", "test", "testAction", new Date());
        int lastEntry = userLogService.usersActions().size();
        userLogService.add(userLog);
        assert (userLogService.usersActions().get(lastEntry).getUserName().equals("admin"));
        assert (userLogService.usersActions().get(lastEntry).getVisitedPage().equals("test"));
        assert (userLogService.usersActions().get(lastEntry).getAction().equals("testAction"));


    }

}