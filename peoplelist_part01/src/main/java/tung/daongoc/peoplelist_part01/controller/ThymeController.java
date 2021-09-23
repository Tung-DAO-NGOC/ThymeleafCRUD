package tung.daongoc.peoplelist_part01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tung.daongoc.peoplelist_part01.model.Person;
import tung.daongoc.peoplelist_part01.repository.DAO;

@Controller
public class ThymeController {
    @Autowired
    @Qualifier("list")
    private DAO<Person> peopleDAO;

    @GetMapping(value = { "/", "/index", "/home" })
    public String homePage(Model model) {
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/list")
    public String readAllPage(Model model) {
        model.addAttribute("title", "List of people");
        model.addAttribute("list", peopleDAO.getAll());
        return "peopleList";
    }
}
