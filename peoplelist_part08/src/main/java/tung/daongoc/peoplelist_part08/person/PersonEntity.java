package tung.daongoc.peoplelist_part08.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true)
public class PersonEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String job;
    private int age;
    private byte[] avatar;
}
