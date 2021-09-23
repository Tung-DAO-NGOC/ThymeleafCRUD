package tung.daongoc.peoplelist_part03.repository;

import java.util.ArrayList;
import java.util.List;

import tung.daongoc.peoplelist_part03.model.Person;

public class PersonDAO implements DAO<Person> {
    protected List<Person> listObject = new ArrayList<Person>();

    @Override
    public List<Person> getAll() {
        return listObject;
    }

    @Override
    public Person getById(int id) {
        return listObject.stream().filter(p -> (p.getId() == id)).findFirst().get();
    }

}
