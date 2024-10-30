package ClubCard.backend.t1debut_project.dto;

import ClubCard.backend.t1debut_project.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VirtualCardDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String privilegeLevel;
    private String template;

    public VirtualCardDTO(User user) {
        this.userId = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.privilegeLevel = user.getPrivilege();
        this.template = user.getTemplate();
    }
}

