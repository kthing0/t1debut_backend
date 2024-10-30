package ClubCard.backend.t1debut_project.utils;

import ClubCard.backend.t1debut_project.security.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {
    Optional<BlacklistToken> findByToken(String token);
}

