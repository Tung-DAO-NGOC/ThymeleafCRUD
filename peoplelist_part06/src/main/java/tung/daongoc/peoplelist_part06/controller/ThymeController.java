package tung.daongoc.peoplelist_part06.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tung.daongoc.peoplelist_part06.model.Person;
import tung.daongoc.peoplelist_part06.repository.DAO;

@Controller
public class ThymeController {
    @Autowired
    DAO<Person> personDAO;

    @GetMapping(value = "/")
    public String getIndex() {
        return "index";
    }

    @GetMapping(value = "/person/list")
    public String getPersonList(Model model) {
        model.addAttribute("personList", personDAO.getAll());
        return "personList";
    }

    @GetMapping("/person/add")
    public String getForm(Model model) {
        Person person = new Person();
        model.addAttribute("newPerson", person);
        return "personForm";
    }

    @PostMapping("/person/add")
    public String addPerson(@Valid @ModelAttribute("newPerson") Person newPerson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newPerson", newPerson);
            return ("personForm");
        } else {
            personDAO.add(newPerson);
            return "redirect:/person/list";
        }
    }
}
