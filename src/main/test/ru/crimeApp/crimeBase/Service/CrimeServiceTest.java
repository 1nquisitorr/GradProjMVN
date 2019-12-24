package ru.crimeApp.crimeBase.Service;

import crimeApp.crimeBase.model.Crime;
import crimeApp.crimeBase.service.CrimeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;
import ru.crimeApp.crimeBase.config.HibernateConfigTest;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@WebAppConfiguration
public class CrimeServiceTest {

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Resource
    private CrimeService crimeService;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void testCrimeGet() throws Exception {
        Assert.notNull(crimeService.getByID(86));
        Assert.isNull(crimeService.getByID(0));
    }


    @Test
    public void testGetDescription() throws Exception {
        Crime crime= crimeService.getByID(86);

        assert (crime.getDescription().contains("Using symbols of unconstitutional organisations"));

    }

    @Test
    public void testGetAllCrimes() throws Exception {
        int CRIME_AMOUNT=26;
        List<Crime> crime= crimeService.getAllCrimes();

        assert (crime.size()>0);
        assert (crime.size()==CRIME_AMOUNT);

    }

}