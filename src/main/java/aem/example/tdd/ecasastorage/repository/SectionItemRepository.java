package aem.example.tdd.ecasastorage.repository;

import aem.example.tdd.ecasastorage.entity.SectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionItemRepository extends JpaRepository<SectionItem, Long> {
}
