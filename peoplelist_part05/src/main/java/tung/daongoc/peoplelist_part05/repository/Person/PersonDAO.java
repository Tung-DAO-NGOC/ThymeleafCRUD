package tung.daongoc.peoplelist_part05.repository.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tung.daongoc.peoplelist_part05.model.Person;
import tung.daongoc.peoplelist_part05.repository.DAO;

public class PersonDAO implements DAO<Person> {

    protected List<Person> listPerson = new ArrayList<Person>();

    @Override
    public List<Person> getAll() {
        return listPerson;
    }

    @Override
    public List<Person> sortByKeyword(String keyword) {
        List<Person> result = listPerson.stream()
                .filter(p -> (p.getName().toLowerCase().contains(keyword.toLowerCase()))).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Person> sortByOrder(List<Person> list, String order) {
        List<Person> result = new ArrayList<Person>();
        if (order.isEmpty()) {
            return list;
        } else {
            switch (order) {
                case "jobInc":
                    result = list.stream().sorted((p1, p2) -> (p1.getJob().compareToIgnoreCase(p2.getJob())))
                            .collect(Collectors.toList());
                    break;
                case "jobDec":
                    result = list.stream().sorted((p1, p2) -> (p2.getJob().compareToIgnoreCase(p1.getJob())))
                            .collect(Collectors.toList());
                    break;
                case "ageInc":
                    result = list.stream().sorted((p1, p2) -> (p1.getAge() - p2.getAge())).collect(Collectors.toList());
                    break;
                case "ageDec":
                    result = list.stream().sorted((p1, p2) -> (p2.getAge() - p1.getAge())).collect(Collectors.toList());
                    break;
            }
            return result;
        }
    }

}
