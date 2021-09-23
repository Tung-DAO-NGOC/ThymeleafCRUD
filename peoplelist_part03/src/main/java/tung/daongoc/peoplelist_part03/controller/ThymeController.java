package tung.daongoc.peoplelist_part03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tung.daongoc.peoplelist_part03.model.Person;
import tung.daongoc.peoplelist_part03.repository.DAO;

@Controller
public class ThymeController {
    @Autowired
    @Qualifier("json")
    private DAO<Person> peopleDAO;

    @GetMapping(value = { "/", "/index" })
    public String homePage(Model model) {
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/person")
    public String readAllPage(Model model) {
        model.addAttribute("title", "List of people");
        model.addAttribute("list", peopleDAO.getAll());
        return "peopleList";
    }

    @GetMapping("/person/view/{id}")
    public String readByID(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("person", peopleDAO.getById(id));
        return "personView";
    }

}
