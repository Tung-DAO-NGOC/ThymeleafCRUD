package tung.daongoc.peoplelist_part04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import tung.daongoc.peoplelist_part04.model.Person;
import tung.daongoc.peoplelist_part04.repository.DAO;

@Controller
public class ThymeController {
    @Autowired
    @Qualifier("json")
    DAO<Person> personDAO;

    @GetMapping(value = { "/", "/index" })
    public String pageIndex(Model model) {
        model.addAttribute("title", "Index page");
        return "index";
    }

    @GetMapping("/person")
    public String getList(Model model) {
        model.addAttribute("title", "People List");
        model.addAttribute("list", personDAO.getAll());
        return "peopleList";
    }

    @GetMapping("/person/create")
    public String createPersonForm(Model model) {
        model.addAttribute("title", "Create new person data");
        return "personCreate";
    }

    @PostMapping("/person/create")
    public String createPerson(@ModelAttribute("createdPerson") Person newPerson) {
        personDAO.addObject(newPerson);
        return "redirect:/person";
    }

    @GetMapping("/person/edit/{id}")
    public String editPersonForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("editedPerson", personDAO.getByID(id));
        model.addAttribute("isMale", personDAO.getByID(id).getGender().equals("Male") ? true : false);
        return "personEdit";
    }

    @PostMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id") Integer id, @ModelAttribute("editedPerson") Person editPerson) {
        personDAO.replaceObject(id, editPerson);
        return "redirect:/person";
    }

    @GetMapping("/person/delete/{id}")
    public String deleteByID(@PathVariable("id") Integer id) {
        personDAO.deleteByID(id);
        return "redirect:/person";
    }
}