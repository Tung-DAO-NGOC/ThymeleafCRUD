package tung.daongoc.peoplelist_part04.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tung.daongoc.peoplelist_part04.model.Person;

public class PersonDAO implements DAO<Person> {
    protected List<Person> listObject = new ArrayList<Person>();

    @Override
    public List<Person> getAll() {
        return listObject;
    }

    @Override
    public Person getByID(int id) {
        return listObject.stream().filter(p -> (p.getId() == id)).findFirst().get();
    }

    @Override
    public void addObject(Person newObject) {
        listObject.add(newObject);
    }

    @Override
    public void replaceObject(int id, Person newObject) {
        listObject = listObject.stream().map(o -> o.getId() == id ? newObject : o).collect(Collectors.toList());
    }

    @Override
    public void deleteByID(int id) {
        Person getPerson = getByID(id);
        listObject.remove(getPerson);
    }

}
