package ru.crimeApp.crimeBase.controller;

import crimeApp.crimeBase.model.Crime;
import crimeApp.crimeBase.model.Person;
import crimeApp.crimeBase.service.CrimeService;
import crimeApp.crimeBase.service.PersonService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.crimeApp.crimeBase.config.HibernateConfigTest;
import java.text.ParseException;
import java.util.*;
import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfigTest.class)
@WebAppConfiguration
@Transactional
public class ControllerTest {

    List<Person> personList = new ArrayList<>();
    Crime crime;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    PersonService personService;

    @MockBean
    Authentication authentication;

    @MockBean
    CrimeService crimeService;

    @MockBean
    Person person;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Before
    public void onSetUp() {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        User user = new User("admin", "admin", grantedAuthorities);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Before
    public void PersonOnSetUp() throws ParseException {
        byte[] bytes= hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
        crime= new Crime();
        crime.setDescription("test");
        crime.setDescriptionFull("test");


        person = new Person("testName", "testSurname", "1");
        person.setBirthDate("10/10/1987");
        person.setPersonConnections("1");
        person.setPhoto(bytes);
        person.setId(1);
        personList.add(person);

    }


    @Test
    public void testLoginRef() throws Exception {


        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("login"));
    }


    @Test
    public void testAllPersons() throws Exception {

        this.mockMvc.perform(get("/all")).andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("personsList"))
                .andExpect(model().attributeExists("personsCount"))
                .andExpect(model().attributeExists("personAddMenu"))
                .andExpect(view().name("allPersons"));

    }


    @Test
    public void testShowPage() throws Exception {


        given(personService.getById(1)).willReturn(person);
        given(personService.getPersonConnections(1)).willReturn("1");
        given(crimeService.getByID(1)).willReturn(crime);


        this.mockMvc.perform(get("/show/1")).andExpect(status().isOk())
                .andExpect(model().size(9))
                .andExpect(model().attributeExists("connectedPersonPhoto"))
                .andExpect(model().attributeExists("connectedPerson"))
                .andExpect(model().attributeExists("personsList"))
                .andExpect(model().attributeExists("crime"))
                .andExpect(model().attributeExists("personMenu"))
                .andExpect(model().attributeExists("personMenuValid"))
                .andExpect(model().attributeExists("personsCount"))
                .andExpect(view().name("allPersons"));

    }


    @Test
    public void testAddPersons() throws Exception {

        this.mockMvc.perform(get("/add")).andExpect(status().isOk())
                .andExpect(model().size(6))
                .andExpect(model().attributeExists("personAddMenu"))
                .andExpect(model().attributeExists("personMenuValid"))
                .andExpect(model().attributeExists("personsList"))
                .andExpect(model().attributeExists("personsCount"))
                .andExpect(model().attributeExists("CrimeActionList"))
                .andExpect(view().name("allPersons"));
    }


    @Test
    public void testEditPersons() throws Exception {

        this.mockMvc.perform(get("/add")).andExpect(status().isOk())
                .andExpect(model().size(6))
                .andExpect(model().attributeExists("personAddMenu"))
                .andExpect(model().attributeExists("personMenuValid"))
                .andExpect(model().attributeExists("personsList"))
                .andExpect(model().attributeExists("personsCount"))
                .andExpect(model().attributeExists("CrimeActionList"))
                .andExpect(view().name("allPersons"));
    }

    @Test
    public void testCheckPersons() throws Exception {

        this.mockMvc.perform(get("/admin")).andExpect(status().isOk())
                .andExpect(model().size(2))
                .andExpect(view().name("/admin/admin"));
    }

    @Test
    public void testDelPersons() throws Exception {
        given(personService.getById(1)).willReturn(person);

        this.mockMvc.perform(get("/delete/1")).andExpect(status().is(302))
                .andExpect(view().name("redirect:/all"));
    }





}
