package tung.daongoc.peoplelist_part05.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tung.daongoc.peoplelist_part05.model.Person;
import tung.daongoc.peoplelist_part05.repository.DAO;

@Component("person")
public class PersonService implements iService<Person> {
    @Autowired
    DAO<Person> personDAO;

    @Override
    public List<Person> getAll() {
        return personDAO.getAll();
    }

    @Override
    public List<Person> getByKeyword(String keyword) {
        return personDAO.sortByKeyword(keyword);
    }

    @Override
    public List<Person> sortByOrder(List<Person> list, String order) {
        return personDAO.sortByOrder(list, order);
    }
}
