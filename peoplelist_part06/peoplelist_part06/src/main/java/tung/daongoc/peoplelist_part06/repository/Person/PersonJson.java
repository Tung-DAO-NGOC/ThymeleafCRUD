package tung.daongoc.peoplelist_part06.repository.Person;

import org.springframework.stereotype.Component;

import tung.daongoc.peoplelist_part06.model.Person;

@Component
public class PersonJson extends PersonDAO {
    public PersonJson() {
        personList.add(new Person("Dao Ngoc Tung", "Coder", "tungdn30.weo@gmail.com", 26));
        personList.add(new Person("Nguyen Khanh Huyen", "Streamer", "huyencute@yahoo.com", 17));
        personList.add(new Person("Pham Huy Tuong", "Writer", "tuongph.hnv@gmail.com", 31));
    }
}
