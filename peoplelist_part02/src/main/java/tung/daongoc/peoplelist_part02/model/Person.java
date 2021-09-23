package tung.daongoc.peoplelist_part02.model;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @CsvBindByName(column = "id")
    private int id;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "job")
    private String job;
    @CsvBindByName(column = "gender")
    private String gender;
    @CsvBindByName(column = "email")
    private String email;
}
