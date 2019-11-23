import crimeApp.crimeBase.dao.PersonDAO;
import crimeApp.crimeBase.dao.PersonDAOImpl;
import crimeApp.crimeBase.model.Person;
        import org.junit.Before;
        import org.junit.Test;

        import static org.assertj.core.api.Assertions.assertThat;

public class PersonDAOTest {

    private PersonDAO personDAO;

    @Before
    public void setUp() throws Exception {
        this.personDAO= new PersonDAOImpl();
    }

    @Test
    public void getPersonByName_Should_Return_True() throws Exception
    {
        Person p=personDAO.getPersonByName("Alex");
        assertThat(p).isNotNull();
        assertThat(p.getName()).isEqualTo("Alex");
    }

    @Test
    public void getPersonByName_Should_Return_Null() throws Exception
    {
        Person p=personDAO.getPersonByName("Nikolai");
        assertThat(p).isNull();
    }

}