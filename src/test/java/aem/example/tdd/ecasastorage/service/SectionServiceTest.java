package aem.example.tdd.ecasastorage.service;

import aem.example.tdd.ecasastorage.entity.Product;
import aem.example.tdd.ecasastorage.entity.ProductType;
import aem.example.tdd.ecasastorage.entity.Section;
import aem.example.tdd.ecasastorage.entity.SectionItem;
import aem.example.tdd.ecasastorage.exception.SectionNotFoundException;
import aem.example.tdd.ecasastorage.repository.ProductRepository;
import aem.example.tdd.ecasastorage.repository.SectionItemRepository;
import aem.example.tdd.ecasastorage.repository.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static aem.example.tdd.ecasastorage.entity.Color.RED;
import static aem.example.tdd.ecasastorage.entity.ProductType.MEAT;
import static aem.example.tdd.ecasastorage.entity.ReceiptType.NYLON;
import static org.junit.jupiter.api.Assertions.*;

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
        sectionService = new SectionService(sectionItemRepository, sectionRepository, productRepository);
        aSection = new Section(10, MEAT);
        aProduct = new Product(5, RED, 4.5, false, NYLON, "M-01");
    }

    @Test
    @DisplayName("Add product to section")
    @Transactional
    void addProductToSection() {
        productRepository.save(aProduct);
        sectionRepository.save(aSection);

        SectionItem sectionItem = sectionService.addProductToSection(aProduct.getId(), aSection.getId(), 100);


        assertEquals(1L, sectionItemRepository.count());
        Optional<Section> updatedSection = sectionRepository.findSectionById(sectionItem.getSection().getId());
        assertTrue(updatedSection.isPresent());
        Section section = updatedSection.get();

        assertEquals(aSection.getId(), section.getId());
        assertEquals(1, section.getProducts().size());
        assertEquals(aProduct.getId(), section.getProducts().iterator().next().getProduct().getId());
    }

    @Test
    @DisplayName("Edit a section")
    @Transactional
    public void editSection_ShouldBeOk() {
        sectionService.saveSection(aSection);
        aSection.setProductType(ProductType.CLEANLINESS);
        sectionService.saveSection(aSection);

        Section fromDB = sectionRepository.getById(aSection.getId());

        assertEquals(fromDB.getId(), aSection.getId());
        assertEquals(ProductType.CLEANLINESS, fromDB.getProductType());
    }

    @Test
    @DisplayName("Delete section should be ok if there are has no products")
    @Transactional
    void deleteSection_ShouldBeOk() {
        sectionService.saveSection(aSection);
        long id = aSection.getId();

        sectionService.deleteSection(id);

        Section deletedSection = sectionRepository.findById(id).orElse(null);

        assertNull(deletedSection);
    }

    @Test
    @DisplayName("No delete section if it has products")
    @Transactional
    public void deleteSection_ShouldFailIfHasProducts() {
        // prepare
        sectionRepository.save(aSection);
        productRepository.save(aProduct);

        sectionService.addProductToSection(aProduct.getId(), aSection.getId(), 1);

        long id = aSection.getId();
        // delete
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> sectionService.deleteSection(id));
        assertEquals("The section has products.", exception.getMessage());
    }

    @Test
    @DisplayName("Get section details by id")
    @Transactional
    public void getSectionDetails_ById() {
        // prepare
        sectionService.saveSection(aSection);

        long id = aSection.getId();

        Section fromDB = sectionService.findSectionById(id);

        assertEquals(fromDB.getId(), aSection.getId());
        assertEquals(aSection.getProductType(), fromDB.getProductType());
    }

    @Test
    @DisplayName("Return SectionNotFoundException if not exist")
    public void getSectionInvalidId_ShouldReturnSectionNotFoundException() {
        Exception exception = assertThrows(SectionNotFoundException.class, () -> sectionService.findSectionById(20l));
        assertEquals("The section does not exist.", exception.getMessage());
    }
}
