package tung.daongoc.peoplelist_part01.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import tung.daongoc.peoplelist_part01.model.Person;

@Component("list")
public class ListPerson extends DAO<Person> {

    public ListPerson() {
        listPerson.add(new Person(1, "Pham Hong Hanh", "Teacher", "Female", "hanh1908@gmail.com"));
        listPerson.add(new Person(2, "Mai Hong Vu", "Director", "Male", "hvmai91@xt.com"));
        listPerson.add(new Person(3, "Nguyen Cao Ky", "Technician", "Male", "ngcaoky@yahoo.com"));
    }

    @Override
    public List<Person> getAll() {
        return listPerson;
    }

}
