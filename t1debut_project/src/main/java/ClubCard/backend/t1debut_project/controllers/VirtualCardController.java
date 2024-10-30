package ClubCard.backend.t1debut_project.controllers;

import ClubCard.backend.t1debut_project.dto.VirtualCardCustomizationDTO;
import ClubCard.backend.t1debut_project.dto.VirtualCardDTO;
import ClubCard.backend.t1debut_project.utils.VirtualCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/virtual-card")
public class VirtualCardController {

    private final VirtualCardService virtualCardService;

    public VirtualCardController(VirtualCardService virtualCardService) {
        this.virtualCardService = virtualCardService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<VirtualCardDTO> getVirtualCard(@PathVariable Long userId) {
        return ResponseEntity.ok(virtualCardService.getVirtualCard(userId));
    }

    @PutMapping("/{userId}/customize")
    public ResponseEntity<VirtualCardDTO> customizeVirtualCard(
            @PathVariable Long userId,
            @RequestBody VirtualCardCustomizationDTO customizationDTO) {
        return ResponseEntity.ok(virtualCardService.customizeVirtualCard(userId, customizationDTO));
    }

    @GetMapping("/{userId}/qr-code")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long userId) {
        byte[] qrCode = virtualCardService.generateQRCode(userId);
        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .body(qrCode);
    }
}

