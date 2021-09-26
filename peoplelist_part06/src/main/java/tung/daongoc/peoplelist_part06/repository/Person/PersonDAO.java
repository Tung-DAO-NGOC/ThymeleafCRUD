package tung.daongoc.peoplelist_part06.repository.Person;

import java.util.ArrayList;
import java.util.List;

import tung.daongoc.peoplelist_part06.model.Person;
import tung.daongoc.peoplelist_part06.repository.DAO;

public class PersonDAO implements DAO<Person> {
    protected List<Person> personList = new ArrayList<Person>();

    @Override
    public List<Person> getAll() {
        return personList;
    }

    @Override
    public Person getByName(String name) {
        return null;
    }

    @Override
    public void add(Person newObject) {
        personList.add(newObject);
    }

}
