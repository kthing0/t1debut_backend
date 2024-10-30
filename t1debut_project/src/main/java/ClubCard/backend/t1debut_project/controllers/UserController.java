package ClubCard.backend.t1debut_project.controllers;

import ClubCard.backend.t1debut_project.dto.UserDTO;
import ClubCard.backend.t1debut_project.dto.UserUpdateDTO;
import ClubCard.backend.t1debut_project.utils.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = userService.getUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/profile/update-fields")
    public ResponseEntity<UserDTO> updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserUpdateDTO updateDTO) {
        UserDTO updatedUser = userService.updateUserProfile(userDetails.getUsername(), updateDTO);
        return ResponseEntity.ok(updatedUser);
    }
}

