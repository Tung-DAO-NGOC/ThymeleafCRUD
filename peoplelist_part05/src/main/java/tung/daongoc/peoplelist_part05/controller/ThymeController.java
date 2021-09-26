package tung.daongoc.peoplelist_part05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tung.daongoc.peoplelist_part05.model.Person;
import tung.daongoc.peoplelist_part05.service.iService;

@Controller
public class ThymeController {
    @Autowired
    iService<Person> servicePerson;

    @GetMapping(value = { "/", "/index" })
    public String getIndex() {
        return "index";
    }

    @GetMapping(value = "/person/list")
    public String peopleList(Model model) {
        model.addAttribute("personList", servicePerson.getAll());
        return "personList";
    }

    @GetMapping(value = "/person/sort")
    public String listSort(Model model) {
        return "personSort";
    }

    @PostMapping(value = "/person/sort")
    public String returnList(@ModelAttribute("keyword") String keyword, @ModelAttribute("sort") String sort,
            Model model) {
        model.addAttribute("personList", servicePerson.sortByOrder(servicePerson.getByKeyword(keyword), sort));
        return "personSort";
    }
}
