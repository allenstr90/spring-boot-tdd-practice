package aem.example.tdd.ecasastorage.repository;

import aem.example.tdd.ecasastorage.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
