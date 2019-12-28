package crimeApp.crimeBase.controller;

import crimeApp.crimeBase.model.Crime;
import crimeApp.crimeBase.model.Person;
import crimeApp.crimeBase.model.UserLog;
import crimeApp.crimeBase.service.CrimeService;
import crimeApp.crimeBase.service.PersonService;
import crimeApp.crimeBase.service.UserLogService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
public class PersonController {


    private PersonService personService;
    private CrimeService crimeService;
    private UserLogService userLogService;
    private Authentication authentication;


//    @Autowired
//    private HttpServletRequest request;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setCrimeService(CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @Autowired
    public void setUserLogService(UserLogService userLogService) {
        this.userLogService = userLogService;
    }


    @ResponseBody
    private String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@RequestParam("file") MultipartFile file,ModelMap modelMap, @RequestParam String name,
                            String surname, String date, String crime, String connections) throws IOException, ParseException {


        Person person = new Person(name, surname, crime);
        person.setBirthDate(date);
        person.setPhoto(file.getBytes());
        if (connections.equals("false"))
            person.setPersonConnections("no_connection");
        else
            person.setPersonConnections(connections);


        ModelAndView modelAndView = new ModelAndView();
        if (personService.checkPerson(person.getName(), person.getSurname())) {
            modelAndView.setViewName("redirect:/all");
            log("add", "added Person " + person.getName() + " " + person.getSurname());
            personService.add(person);
        } else {
            modelAndView.addObject("message", "part with name \"" + person.getName() + "\" already exists");
            modelAndView.setViewName("redirect:/add");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView getAllPersons() {
        List<Person> personList = personService.allPersons();
        log("all", "look");
        int personsCount = personService.personsCount();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allPersons");
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("personsCount", personsCount);
        modelAndView.addObject("personAddMenu", false);

        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        int personsCount = personService.personsCount();
        List<Person> personList = personService.allPersons();
        List<Crime> crimeList = crimeService.getAllCrimes();
        log("add", "visit add page");
        modelAndView.addObject("personAddMenu", true);
        modelAndView.addObject("personMenuValid", false);
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("personsCount", personsCount);
        modelAndView.addObject("CrimeActionList", crimeList);
        modelAndView.setViewName("allPersons");

        return modelAndView;
    }


    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView showPage(@PathVariable("id") int id,
                                 @ModelAttribute("message") String message) {

        Person person = personService.getById(id);
        int CrimeID = Integer.parseInt(person.getCrimes());
        Crime crime = crimeService.getByID(CrimeID);
        List<Person> personList = personService.allPersons();
        int personsCount = personService.personsCount();


        String personConnections = personService.getPersonConnections(id);
        ModelAndView modelAndView= personConnectionsSetter(personConnections, new ModelAndView());

        if (person.getPhoto() != null) {
            byte[] imageBytes = person.getPhoto();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            modelAndView.addObject("img", base64Image);
        }


        log("show", "Show Person with ID " + id);
        modelAndView.setViewName("allPersons");
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("crime", crime);
        modelAndView.addObject("personMenu", person);
        modelAndView.addObject("personMenuValid", true);
        modelAndView.addObject("personsCount", personsCount);
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id,
                                 @ModelAttribute("message") String message) {
        log("/edit", "Edit Page Person with ID " + id);
        Person person = personService.getById(id);
        ModelAndView modelAndView = StandartRequest(person);

        if (person.getPhoto() != null) {
            byte[] imageBytes = person.getPhoto();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            modelAndView.addObject("img", base64Image);

        }
        String personConnections = personService.getPersonConnections(id);

        return personConnectionsSetter(personConnections,modelAndView);
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "file") MultipartFile file, ModelMap modelMap, @RequestParam String name,
                             String surname, String date, String crime, int id, String connections) throws IOException, ParseException {

        ModelAndView modelAndView = new ModelAndView();
        Person person = new Person(name, surname, crime);
        person.setId(id);
        person.setBirthDate(date);
        person.setPhoto(file.getBytes());

        String prevConnections = personService.getById(id).getPersonConnections();


        if (connections.equals("false")) {
            person.setPersonConnections(prevConnections);
        } else if (!connections.isEmpty()) {
            if (prevConnections.equals("no_connection")) {
                person.setPersonConnections(connections);
            } else {
                String[] string = prevConnections.split("-");
                List<String> list = Arrays.asList(string);
                if (!list.contains(connections)) {
                    person.setPersonConnections(prevConnections + "-" + connections);
                }
            }
        }

        if (file.isEmpty()) {
            person.setPhoto(personService.getById(id).getPhoto());
        }

        log("/edit", "Edited Person " + person.getSurname() + " " + person.getName());
        personService.edit(person);
        modelAndView.setViewName("redirect:/edit/" + +person.getId());
        return modelAndView;
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePerson(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
//        int personsCount = personService.personsCount();
        log("/delete", "Deleted Person with ID" + id);
        modelAndView.setViewName("redirect:/all");
        Person person = personService.getById(id);
        personService.delete(person);
        return modelAndView;
    }


    @RequestMapping(value = "/checkPerson", method = RequestMethod.POST)
    public ModelAndView CheckPerson(@ModelAttribute("person") Person person) {
        List<Person> personList = personService.findPerson(person.getName(), person.getSurname(), person.getBirthDate());
        log("/checkPerson", "Checked Person " + person.getName() + " " + person.getSurname());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allPersons");
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("personsCount", personList.size());
        return modelAndView;

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        List<UserLog> userLogs = userLogService.usersActions();
        modelAndView.addObject("LogList", userLogs);
        modelAndView.setViewName("/admin/admin");
        return modelAndView;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginException(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    private ModelAndView StandartRequest(Person person) {


        ModelAndView modelAndView = new ModelAndView();
        int CrimeID = Integer.parseInt(person.getCrimes());
        Crime crime = crimeService.getByID(CrimeID);
        List<Crime> crimeList = crimeService.getAllCrimes();
        List<Person> personList = personService.allPersons();
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("crime", crime);
        modelAndView.addObject("personMenu", person);
        modelAndView.addObject("personMenuValid", true);
        modelAndView.addObject("personAddMenu", true);
        modelAndView.addObject("CrimeActionList", crimeList);
        modelAndView.addObject("person", person);

        modelAndView.setViewName("allPersons");
        return modelAndView;
    }

    private void log(String pageName, String action) {


        authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = currentUserName(authentication);
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        userLogService.add(new UserLog(name, pageName, action, sqlDate));


    }

    private ModelAndView personConnectionsSetter(String personConnections, ModelAndView modelAndView) {

        if (!personConnections.equals("no_connection")) {
            String[] connectionsArray = personConnections.split("-");
            ArrayList<Person> connectedPerson = new ArrayList<>();
            ArrayList<String> connectedImages = new ArrayList<>();

            for (String s : connectionsArray) {

                connectedPerson.add(personService.getById(Integer.parseInt(s)));
                byte[] imageBytes = personService.getById(Integer.parseInt(s)).getPhoto();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                connectedImages.add(base64Image);
            }
            modelAndView.addObject("connectedPersonPhoto", connectedImages);
            modelAndView.addObject("connectedPerson", connectedPerson);
        }
        return modelAndView;


    }
}






