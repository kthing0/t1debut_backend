package ClubCard.backend.t1debut_project.utils;

import ClubCard.backend.t1debut_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

