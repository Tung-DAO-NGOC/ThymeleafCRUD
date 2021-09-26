package tung.daongoc.peoplelist_part05.repository.Person;

import java.io.File;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import tung.daongoc.peoplelist_part05.model.Person;

import java.util.List;

@Component("json")
public class PersonJson extends PersonDAO {
    private final String PATH = "classpath:static/PeopleList.json";

    public PersonJson() {
        try {
            File file = ResourceUtils.getFile(PATH);
            ObjectMapper mapper = new ObjectMapper();
            listPerson.addAll(mapper.readValue(file, new TypeReference<List<Person>>() {
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
