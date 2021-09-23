package tung.daongoc.peoplelist_part02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tung.daongoc.peoplelist_part02.model.Person;
import tung.daongoc.peoplelist_part02.repository.DAO;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    @Qualifier("xml")
    DAO<Person> peopleDAO;

    @GetMapping("")
    public ResponseEntity<?> readAll() {
        return ResponseEntity.status(HttpStatus.OK).body(peopleDAO.readAll());
    }
}
