package tung.daongoc.peoplelist_part08.controller;

import java.io.IOException;
import java.util.Base64;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tung.daongoc.peoplelist_part08.controller.Model.View;
import tung.daongoc.peoplelist_part08.person.PersonModel;
import tung.daongoc.peoplelist_part08.services.iService;

@Controller
public class PeopleController {

    @Autowired
    iService<PersonModel> personService;

    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {
        model.addAttribute("active", "index");
        return "index";
    }

    @GetMapping(value = {"/list"})
    public String getListPage(Model model,
            @RequestParam(required = false, value = "limit") Integer limit,
            @RequestParam(required = false, value = "offset") Integer offset,
            @RequestParam(required = false, value = "move") String move) {
        View view = new View();
        view.setLimit(limit == null ? 10 : limit);
        view.setOffset(offset == null ? 0 : offset);
        if (move != null) {
            switch (move) {
                case "Prev":
                    int newOffset = view.getOffset() - view.getLimit();
                    view.setOffset(newOffset < 0 ? 0 : newOffset);
                    break;
                case "Next":
                    view.setOffset(view.getOffset() + view.getLimit());
                    break;
            }
        }
        model.addAttribute("addNew", false);
        model.addAttribute("list", personService.getList(view.getLimit(), view.getOffset()));
        model.addAttribute("active", "list");
        model.addAttribute("view", view);
        return "list";
    }


    @GetMapping("/view_{id}")
    public String getViewPage(@PathVariable(value = "id") Long id, Model model) {
        PersonModel personModel = personService.getByID(id);
        boolean hasAvatar = personModel.getAvatarBase64() != null ? true
                : false;
        model.addAttribute("person", personModel);
        model.addAttribute("hasAvatar", hasAvatar);
        model.addAttribute("active", "view");
        return "view";
    }

    @GetMapping("/edit_{id}")
    public String getEditPage(@PathVariable(value = "id") Long id, Model model)
            throws DataAccessException {
        PersonModel personModel = personService.getByID(id);

        model.addAttribute("person", personModel);
        model.addAttribute("hasAvatar", personModel.getAvatar() != null ? true : false);
        model.addAttribute("active", "view");
        return "edit";
    }

    @PostMapping("/edit_{id}")
    public String postEditPage(@PathVariable(value = "id") Long id,
            @Valid @ModelAttribute(value = "person") PersonModel personModel,
            BindingResult br, Model model)
            throws IOException {
        String getReturn;
        boolean hasAvatar = false;
        if (personModel.getAvatar().length == 0) {
            personModel.setAvatar(null);
        }
        if (br.hasErrors()) {
            if (personModel.getAvatar() != null) {
                hasAvatar = true;
                personModel.setAvatarBase64(Base64.getEncoder().encodeToString(
                        personModel.getAvatar()));
            }
            model.addAttribute("person", personModel);
            model.addAttribute("active", "view");
            model.addAttribute("hasAvatar", hasAvatar);
            return "edit";
        } else {
            getReturn = "redirect:" + "/view_" + String.valueOf(personModel.getId());
            if (!personModel.getAvatarUpload().isEmpty()) {
                personModel.setAvatar(personModel.getAvatarUpload().getBytes());
            }
            personService.update(personModel.getId(), personModel);
            return getReturn;
        }
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        PersonModel personModel = new PersonModel();
        model.addAttribute("newPerson", personModel);
        model.addAttribute("active", "add");
        return "add";
    }

    @PostMapping("/add")
    public String postAddPage(
            @Valid @ModelAttribute(value = "newPerson") PersonModel personModel,
            BindingResult br, Model model)
            throws IOException {
        boolean hasAvatar = false;
        if (personModel.getAvatar().length == 0) {
            personModel.setAvatar(null);
        }
        if (br.hasErrors()) {
            if (personModel.getAvatar() != null) {
                hasAvatar = true;
                personModel.setAvatarBase64(Base64.getEncoder().encodeToString(
                        personModel.getAvatar()));
            }
            model.addAttribute("newPerson", personModel);
            model.addAttribute("active", "add");
            model.addAttribute("hasAvatar", hasAvatar);
            return "add";
        } else {
            if (personModel.getAvatarUpload() != null) {
                personModel.setAvatar(personModel.getAvatarUpload().getBytes());
            }
            personService.add(personModel);
            model.addAttribute("addNew", true);
            return "redirect:/list";
        }
    }

    @GetMapping("/delete_{id}")
    public String getDeletePage(@PathVariable(value = "id") Long id, Model model) {
        PersonModel personModel = personService.getByID(id);
        boolean hasAvatar =
                personModel.getAvatar() != null || personModel.getAvatarBase64() != null ? true
                        : false;
        model.addAttribute("person", personModel);
        model.addAttribute("hasAvatar", hasAvatar);
        model.addAttribute("active", "delete");
        return "delete";
    }

    @PostMapping("/delete_{id}")
    public String postDeletePage(@PathVariable(value = "id") Long id) {
        personService.delete(id);
        return "redirect:/list";
    }

}
