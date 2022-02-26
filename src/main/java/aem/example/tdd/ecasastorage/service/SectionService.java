package aem.example.tdd.ecasastorage.service;

import aem.example.tdd.ecasastorage.entity.Product;
import aem.example.tdd.ecasastorage.entity.Section;
import aem.example.tdd.ecasastorage.entity.SectionItem;
import aem.example.tdd.ecasastorage.entity.SectionProductKey;
import aem.example.tdd.ecasastorage.exception.ProductNotFoundException;
import aem.example.tdd.ecasastorage.exception.SectionNotFoundException;
import aem.example.tdd.ecasastorage.repository.ProductRepository;
import aem.example.tdd.ecasastorage.repository.SectionItemRepository;
import aem.example.tdd.ecasastorage.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SectionService {

    private final SectionItemRepository sectionItemRepository;
    private final SectionRepository sectionRepository;
    private final ProductRepository productRepository;

    public SectionService(SectionItemRepository sectionItemRepository, SectionRepository sectionRepository, ProductRepository productRepository) {
        this.sectionItemRepository = sectionItemRepository;
        this.sectionRepository = sectionRepository;
        this.productRepository = productRepository;
    }

    public SectionItem addProductToSection(long productId, long sectionId, int cant) {
        Product product = productRepository.findProductById(productId).orElseThrow(ProductNotFoundException::new);
        Section section = sectionRepository.findSectionById(sectionId).orElseThrow(SectionNotFoundException::new);

        SectionItem sectionItem = new SectionItem(section, product, cant);

        section.addSectionItem(sectionItem);

        sectionItemRepository.saveAndFlush(sectionItem);
        return sectionItem;
    }

    public Section saveSection(Section section) {
        return sectionRepository.saveAndFlush(section);
    }

    public void deleteSection(long id) {
        if (sectionItemRepository.existsBySectionIdAndQuantityGreaterThan(id, 0))
            throw new UnsupportedOperationException("The section has products.");
        sectionItemRepository.deleteAllBySectionId(id);
        sectionRepository.deleteById(id);
    }

    public Section findSectionById(long id) {
        return sectionRepository.findSectionById(id)
                .orElseThrow(() -> new SectionNotFoundException("The section does not exist."));
    }
}
