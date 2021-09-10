package aem.example.tdd.ecasastorage.repository;

import aem.example.tdd.ecasastorage.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
}
