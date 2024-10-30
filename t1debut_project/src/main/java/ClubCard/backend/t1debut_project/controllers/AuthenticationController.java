package ClubCard.backend.t1debut_project.controllers;

import ClubCard.backend.t1debut_project.dto.TokenResponseDTO;
import ClubCard.backend.t1debut_project.dto.UserLoginDTO;
import ClubCard.backend.t1debut_project.security.BlacklistToken;
import ClubCard.backend.t1debut_project.security.JwtTokenProvider;
import ClubCard.backend.t1debut_project.utils.BlacklistTokenRepository;
import ClubCard.backend.t1debut_project.utils.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
    public class AuthenticationController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(
            UserService userService,
            JwtTokenProvider jwtTokenProvider,
            BlacklistTokenRepository blacklistTokenRepository,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.blacklistTokenRepository = blacklistTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody UserLoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        String token = userService.authenticateUser(email, password, passwordEncoder);

        if (token != null) {
            return ResponseEntity.ok(new TokenResponseDTO(token, jwtTokenProvider.createRefreshToken(email)));
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request, request.getMethod());
        if (token != null && jwtTokenProvider.validateToken(token)) {
            blacklistTokenRepository.save(new BlacklistToken(token, jwtTokenProvider.getExpirationFromToken(token)));
            return ResponseEntity.ok("Logout successful. Token has been invalidated.");
        }
        return ResponseEntity.badRequest().body("Invalid token.");
    }
}

