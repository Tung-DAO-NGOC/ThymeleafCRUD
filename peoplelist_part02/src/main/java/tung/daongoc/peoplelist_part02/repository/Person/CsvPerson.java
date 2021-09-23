package tung.daongoc.peoplelist_part02.repository.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.opencsv.bean.CsvToBeanBuilder;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import tung.daongoc.peoplelist_part02.model.Person;
import tung.daongoc.peoplelist_part02.repository.PersonDAO;

@Component("csv")
public class CsvPerson extends PersonDAO {

    private final String PATH = "classpath:static/People.csv";

    public CsvPerson() {
        try {
            File file = ResourceUtils.getFile(PATH);
            listObject = new CsvToBeanBuilder<Person>(new FileReader(file)).withType(Person.class).build().parse();
        } catch (IllegalStateException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

}
