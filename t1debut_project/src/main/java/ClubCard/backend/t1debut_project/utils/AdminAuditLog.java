package ClubCard.backend.t1debut_project.utils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdminAuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long adminId;
    private Long userId;
    private String action; // e.g., BLOCK, UNBLOCK, UPDATE_PRIVILEGE
    private LocalDateTime timestamp;

    public AdminAuditLog(Long adminId, Long userId, String action) {
        this.adminId = adminId;
        this.userId = userId;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }
}

