package aem.example.tdd.ecasastorage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import aem.example.tdd.ecasastorage.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
