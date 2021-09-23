package tung.daongoc.peoplelist_part02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tung.daongoc.peoplelist_part02.model.Person;
import tung.daongoc.peoplelist_part02.repository.DAO;

@Controller
public class ThymeController {
    @Autowired
    // Thay thế tag của bean ở đây
    @Qualifier("xml")
    private DAO<Person> peopleDAO;

    @GetMapping(value = { "/", "/index", "/home" })
    public String homePage(Model model) {
        model.addAttribute("title", "Home Page");
        return "index";
    }

    @GetMapping("/list")
    public String readAllPage(Model model) {
        model.addAttribute("title", "List of people");
        model.addAttribute("list", peopleDAO.readAll());
        return "peopleList";
    }
}
