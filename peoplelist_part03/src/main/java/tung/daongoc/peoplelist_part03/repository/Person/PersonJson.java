package tung.daongoc.peoplelist_part03.repository.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import tung.daongoc.peoplelist_part03.model.Person;
import tung.daongoc.peoplelist_part03.repository.PersonDAO;

@Component("json")
public class PersonJson extends PersonDAO {

    private final String PATH = "classpath:static/People.json";

    public PersonJson() {
        try {
            File file = ResourceUtils.getFile(PATH);
            ObjectMapper mapper = new ObjectMapper();
            listObject.addAll(mapper.readValue(file, new TypeReference<List<Person>>() {
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
