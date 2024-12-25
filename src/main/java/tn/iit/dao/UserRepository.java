package tn.iit.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
