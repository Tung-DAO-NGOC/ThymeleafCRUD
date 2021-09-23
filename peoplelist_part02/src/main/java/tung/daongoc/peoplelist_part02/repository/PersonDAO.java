package tung.daongoc.peoplelist_part02.repository;

import java.util.ArrayList;
import java.util.List;

import tung.daongoc.peoplelist_part02.model.Person;

public class PersonDAO implements DAO<Person> {
    protected List<Person> listObject = new ArrayList<Person>();

    @Override
    public List<Person> readAll() {
        return listObject;
    }

}
