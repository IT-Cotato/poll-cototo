package org.cotato.poll.polltato.domain.team.repostiroy;

import java.util.Optional;

import org.cotato.poll.polltato.domain.team.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndSessionKey(String email, String key);

	Optional<User> findByEmail(String email);
}
