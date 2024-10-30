package ClubCard.backend.t1debut_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleUpdateDTO {
    @NotBlank
    private String role;
}

