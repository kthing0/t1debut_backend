package ClubCard.backend.t1debut_project.controllers;

import ClubCard.backend.t1debut_project.security.JwtTokenProvider;
import ClubCard.backend.t1debut_project.utils.UserService;
import ClubCard.backend.t1debut_project.utils.VirtualCardService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final VirtualCardService virtualCardService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public ProfileController(VirtualCardService virtualCardService, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.virtualCardService = virtualCardService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("Profile content accessible.");
    }

    @GetMapping("/generate-qr")
    public ResponseEntity<byte[]> generateQRCode(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request, request.getMethod());
        String email = jwtTokenProvider.getEmailFromToken(token);
        Long userId = userService.getUserIdByEmail(email);

        byte[] qrCodeImage = virtualCardService.generateQRCode(userId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
    }
}

