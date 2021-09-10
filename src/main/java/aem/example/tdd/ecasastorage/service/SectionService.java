package aem.example.tdd.ecasastorage.service;

import aem.example.tdd.ecasastorage.entity.Section;
import aem.example.tdd.ecasastorage.entity.SectionItem;
import aem.example.tdd.ecasastorage.repository.SectionItemRepository;
import aem.example.tdd.ecasastorage.repository.SectionRepository;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionItemRepository sectionItemRepository;
    private final SectionRepository sectionRepository;

    public SectionService(SectionItemRepository sectionItemRepository, SectionRepository sectionRepository) {
        this.sectionItemRepository = sectionItemRepository;
        this.sectionRepository = sectionRepository;
    }


    public SectionItem addProductToSection(SectionItem item) {
        return sectionItemRepository.save(item);
    }

}
