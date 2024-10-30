package ClubCard.backend.t1debut_project.controllers;

import ClubCard.backend.t1debut_project.entities.User;
import ClubCard.backend.t1debut_project.utils.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setup")
public class SetupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // for the first launch and testing purposes only
    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin() {
        if (userRepository.findByEmail("admin@example.com").isPresent()) {
            return ResponseEntity.badRequest().body("Admin user already exists.");
        }

        User admin = new User();
        admin.setEmail("admin1@example.com");
        admin.setPassword(passwordEncoder.encode("admin_password"));
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setRole("SUPER-ADMIN");
        admin.setLocked(false);

        userRepository.save(admin);
        return ResponseEntity.ok("Super Admin user created with email: admin@example.com");
    }
}

