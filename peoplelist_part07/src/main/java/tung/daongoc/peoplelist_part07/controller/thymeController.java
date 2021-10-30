package tung.daongoc.peoplelist_part07.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class thymeController {
    @GetMapping(value = { "/", "/index" })
    public String homePage(Model model) {
        model.addAttribute("active", "home");
        return "index";
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        model.addAttribute("active", "list");
        return "list";
    }
}
