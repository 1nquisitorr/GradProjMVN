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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;

@Controller
public class PersonController {
    private int page;

    private PersonService personService;
    private CrimeService crimeService;
    private UserLogService userLogService;
    private Authentication authentication;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setCrimeService(CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @Autowired
    public void setUserLogService(UserLogService userLogService) { this.userLogService=userLogService; }


    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allPersons(@RequestParam(defaultValue = "1") int page) {
        List<Person> personList = personService.allPersons(page);
        log("all","look");
        int personsCount = personService.personsCount();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allPersons");
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("personsCount", personsCount);
        modelAndView.addObject("personAddMenu", false);
        this.page = page;
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        int personsCount = personService.personsCount();
        List<Person> personList = personService.allPersons(page);
        List<Crime> crimeList = crimeService.getAllCrimes();
        log("add","visit add page");
        modelAndView.addObject("personAddMenu", true);
        modelAndView.addObject("personMenuValid", false);
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("personsCount", personsCount);
        modelAndView.addObject("CrimeActionList", crimeList);
        modelAndView.setViewName("allPersons");

        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addPerson(@ModelAttribute("person") Person person) {// here

        ModelAndView modelAndView = new ModelAndView();
        if (personService.checkPerson(person.getName(), person.getSurname())) {
            modelAndView.setViewName("redirect:/all");
            modelAndView.addObject("page", page);
            log("add","added Person "+person.getName()+" "+person.getSurname());

            personService.add(person);
        } else {
            modelAndView.addObject("message", "part with name \"" + person.getName() + "\" already exists");
            modelAndView.setViewName("redirect:/add");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView showPage(@PathVariable("id") int id,
                                 @ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();
        Person person = personService.getById(id);
        int CrimeID = Integer.parseInt(person.getCrimes());
        Crime crime = crimeService.getByID(CrimeID);
        List<Person> personList = personService.allPersons(page);
        int personsCount = personService.personsCount();
        log("show","Show Person with ID "+String.valueOf(id));
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
        log("/edit","Edit Page Person with ID "+String.valueOf(id));
        ModelAndView modelAndView;
        Person person = personService.getById(id);
        modelAndView = StandartRequest(person);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editPerson(@ModelAttribute("person") Person person) {
        ModelAndView modelAndView;
        if (personService.checkPerson(person.getName(), person.getSurname()) || personService.getById(person.getId()).getName().equals(person.getName())) {
            log("/edit","Edited Person "+person.getSurname()+" "+person.getName());
            personService.edit(person);
            modelAndView = StandartRequest(person);

        } else {

            modelAndView = new ModelAndView();
            modelAndView.addObject("message", "part with name \"" + person.getName() + "\" already exists");
            modelAndView.setViewName("redirect:/edit/" + +person.getId());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePerson(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        int personsCount = personService.personsCount();
        int page = ((personsCount - 1) % 10 == 0 && personsCount > 10 && this.page == (personsCount + 9) / 10) ?
                this.page - 1 : this.page;
        log("/delete","Deleted Person with ID"+String.valueOf(id));
        modelAndView.setViewName("redirect:/all");
        modelAndView.addObject("page", page);
        Person person = personService.getById(id);
        personService.delete(person);
        return modelAndView;
    }


    @RequestMapping(value = "/checkPerson", method = RequestMethod.POST)
    public ModelAndView CheckPerson(@ModelAttribute("person") Person person) {
        List<Person> personList = personService.findPerson(person.getName(), person.getSurname(), person.getBirthDate());
        log("/checkPerson","Checked Person "+person.getName()+" "+person.getSurname());
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
        modelAndView.setViewName("admin/admin");
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


    public ModelAndView StandartRequest(Person person) {

        ModelAndView modelAndView = new ModelAndView();
        int CrimeID = Integer.parseInt(person.getCrimes());
        Crime crime = crimeService.getByID(CrimeID);
        List<Crime> crimeList = crimeService.getAllCrimes();
        modelAndView.addObject("crime", crime);
        modelAndView.addObject("personMenu", person);
        modelAndView.addObject("personMenuValid", true);
        modelAndView.addObject("personAddMenu", true);
        modelAndView.addObject("CrimeActionList", crimeList);
        modelAndView.addObject("person", person);
        modelAndView.setViewName("allPersons");
        return modelAndView;
    }

    public void log(String pageName, String action)
    {
        authentication=SecurityContextHolder.getContext().getAuthentication();
        String name=currentUserName(authentication);
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        userLogService.add(new UserLog(name,pageName, action, sqlDate));
    }

}


