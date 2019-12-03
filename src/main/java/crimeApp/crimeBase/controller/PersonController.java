package crimeApp.crimeBase.controller;

import crimeApp.crimeBase.model.Crime;
import crimeApp.crimeBase.model.Person;
import crimeApp.crimeBase.service.CrimeService;
import crimeApp.crimeBase.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PersonController {
    private int page;

    private PersonService personService;
    private CrimeService crimeService;


    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setCrimeService(CrimeService crimeService) {
        this.crimeService = crimeService;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView allPersons(@RequestParam(defaultValue = "1") int page) {
        List<Person> personList = personService.allPersons(page);
        int personsCount = personService.personsCount();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/allPersons");
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

        modelAndView.addObject("personAddMenu", true);
        modelAndView.addObject("personMenuValid", false);
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("personsCount", personsCount);
        modelAndView.addObject("CrimeActionList", crimeList);
        modelAndView.setViewName("user/allPersons");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addPerson(@ModelAttribute("person") Person person) {// here
        ModelAndView modelAndView = new ModelAndView();
        if (personService.checkPerson(person.getName(), person.getSurname())) {
            modelAndView.setViewName("redirect:/");
            modelAndView.addObject("page", page);

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
        modelAndView.setViewName("user/allPersons");
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
        ModelAndView modelAndView;
        Person person = personService.getById(id);
        modelAndView = StandartRequest(person);
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editPerson(@ModelAttribute("person") Person person) {
        ModelAndView modelAndView;
        if (personService.checkPerson(person.getName(), person.getSurname()) || personService.getById(person.getId()).getName().equals(person.getName())) {
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
        modelAndView.setViewName("redirect:/");
        modelAndView.addObject("page", page);
        Person person = personService.getById(id);
        personService.delete(person);
        return modelAndView;
    }


    @RequestMapping(value = "/checkPerson", method = RequestMethod.POST)
    public ModelAndView CheckPerson(@ModelAttribute("person") Person person) {
        List<Person> personList = personService.findPerson(person.getName(), person.getSurname(), person.getBirthDate());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/allPersons");
        modelAndView.addObject("personsList", personList);
        modelAndView.addObject("personsCount", personList.size());
        return modelAndView;

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin(@ModelAttribute("message") String message) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/admin");
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
        modelAndView.setViewName("user/allPersons");
        return modelAndView;
    }

}


