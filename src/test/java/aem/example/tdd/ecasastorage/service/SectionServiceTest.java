package aem.example.tdd.ecasastorage.service;

import aem.example.tdd.ecasastorage.entity.Product;
import aem.example.tdd.ecasastorage.entity.Section;
import aem.example.tdd.ecasastorage.entity.SectionItem;
import aem.example.tdd.ecasastorage.repository.ProductRepository;
import aem.example.tdd.ecasastorage.repository.SectionItemRepository;
import aem.example.tdd.ecasastorage.repository.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static aem.example.tdd.ecasastorage.entity.Color.RED;
import static aem.example.tdd.ecasastorage.entity.ProductType.MEAT;
import static aem.example.tdd.ecasastorage.entity.ReceiptType.NYLON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class SectionServiceTest {

    @Autowired
    private SectionItemRepository sectionItemRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ProductRepository productRepository;

    private SectionService sectionService;
    private Section aSection;
    private Product aProduct;


    @BeforeEach
    void setUp() {
        sectionItemRepository.deleteAll();
        sectionRepository.deleteAll();
        sectionService = new SectionService(sectionItemRepository, sectionRepository);
        aSection = new Section(10, MEAT);
        aProduct = new Product(5, RED, 4.5, false, NYLON, "M-01");
    }

    @Test
    @DisplayName("Add product to section")
    @Transactional
    void addProductToSection() {
        sectionRepository.save(aSection);
        productRepository.save(aProduct);
        SectionItem sectionItem = new SectionItem(aSection, aProduct, 100);
        sectionService.addProductToSection(sectionItem);

        assertEquals(1l, sectionItemRepository.count());

        assertNotEquals(0, sectionItem.getId());
    }

}
