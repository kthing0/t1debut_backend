package ClubCard.backend.t1debut_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 2, max = 50)
    private String lastName;

    @Email
    private String email;

    private String phone;

    @Size(min = 8, message = "Password must be at least 8 characters long", max = 20)
    private String password;


    private String birthday;
}

