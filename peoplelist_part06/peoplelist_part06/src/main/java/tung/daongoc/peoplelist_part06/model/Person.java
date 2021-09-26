package tung.daongoc.peoplelist_part06.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @NotBlank(message = "Your name is required")
    @Size(min = 5, max = 30, message = "Name must between 5 and 30")
    private String name;

    @NotBlank(message = "Job is required")
    private String job;

    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 18, message = "Age must be larger than 18")
    private int age;
}
