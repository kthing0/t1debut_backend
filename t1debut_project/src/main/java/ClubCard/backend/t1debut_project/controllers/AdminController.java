package ClubCard.backend.t1debut_project.controllers;

import ClubCard.backend.t1debut_project.dto.UserDTO;
import ClubCard.backend.t1debut_project.dto.UserPrivilegeDTO;
import ClubCard.backend.t1debut_project.dto.UserRegistrationDTO;
import ClubCard.backend.t1debut_project.dto.UserRoleUpdateDTO;
import ClubCard.backend.t1debut_project.utils.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER-ADMIN')")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/members/{id}/privilege")
    @PreAuthorize("hasRole('SUPER-ADMIN') or (hasRole('ADMIN') and @userService.getUserRole(#id) != 'ADMIN')")
    public ResponseEntity<UserDTO> updateUserPrivilege(@PathVariable Long id, @RequestBody UserPrivilegeDTO privilegeDTO) {
        return ResponseEntity.ok(userService.updateUserPrivilege(id, privilegeDTO.getPrivilege()));
    }

    @PutMapping("/members/{id}/block")
    @PreAuthorize("hasRole('SUPER-ADMIN') or (hasRole('ADMIN') and @userService.getUserRole(#id) != 'ADMIN')")
    public ResponseEntity<String> blockUserAccount(@PathVariable Long id) {
        userService.blockUserAccount(id);
        return ResponseEntity.ok("Account blocked successfully");
    }

    @PutMapping("/members/{id}/unblock")
    @PreAuthorize("hasRole('SUPER-ADMIN') or (hasRole('ADMIN') and @userService.getUserRole(#id) != 'ADMIN')")
    public ResponseEntity<String> unblockUserAccount(@PathVariable Long id) {
        userService.unblockUserAccount(id);
        return ResponseEntity.ok("Account unblocked successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.registerUser(userRegistrationDTO, passwordEncoder);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/members")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/members/{id}/change-role")
    @PreAuthorize("hasRole('SUPER-ADMIN')")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestBody UserRoleUpdateDTO userRoleUpdateDTO) {
        return ResponseEntity.ok(userService.changeUserRole(id, userRoleUpdateDTO.getRole()));
    }
}
