package ClubCard.backend.t1debut_project.dto;

import ClubCard.backend.t1debut_project.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String privilege;
    private String role;
    private boolean isLocked;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.privilege = user.getPrivilege();
        this.role = user.getRole();
        this.isLocked = user.isLocked();
    }
}

