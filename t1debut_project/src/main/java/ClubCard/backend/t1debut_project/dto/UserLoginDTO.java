package ClubCard.backend.t1debut_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
