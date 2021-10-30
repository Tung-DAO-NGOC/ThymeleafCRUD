package tung.daongoc.peoplelist_part08.person;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true)
public class PersonModel {
    private Long id;

    @NotBlank(message = "First Name is required")
    @Size(max = 20, message = "First Name must less than 20 characters")
    private String firstName;

    @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "Last Name must less than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Size(max = 50, message = "Email must less than 50 characters")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @Size(max = 20, message = "Gender must less than 20 characters")
    private String gender;

    @Size(max = 20, message = "Job title must less than 50 characters")
    private String job;

    @Min(value = 18, message = "Age must be larger than 18")
    private int age;

    private byte[] avatar;

    private MultipartFile avatarUpload;
    private String avatarBase64;
    private String fullName;

    public String standardizeFirstName(String firstName) {
        return String.join("", firstName.substring(0, 1).toUpperCase(),
                firstName.substring(1).toLowerCase());
    }

    public String standardizeLastName(String lastName) {
        return lastName.toUpperCase();
    }

    public void setFullName() {
        this.fullName = String.join(" ", standardizeFirstName(this.firstName),
                standardizeLastName(this.lastName));
    }
}
