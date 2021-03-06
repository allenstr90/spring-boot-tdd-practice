package aem.example.tdd.ecasastorage.controller;

import aem.example.tdd.ecasastorage.entity.*;
import aem.example.tdd.ecasastorage.repository.ProductRepository;
import aem.example.tdd.ecasastorage.repository.SectionItemRepository;
import aem.example.tdd.ecasastorage.repository.SectionRepository;
import aem.example.tdd.ecasastorage.service.ProductService;
import aem.example.tdd.ecasastorage.service.SectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
@AutoConfigureRestDocs
public class SectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SectionItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    private SectionService sectionService;
    private ProductService productService;

    private Section section;
    private Product product;

    @BeforeEach
    void setUp() {
        this.sectionService = new SectionService(itemRepository, sectionRepository, productRepository);
        this.productService = new ProductService(productRepository);

        this.section = new Section(10, ProductType.APPLIANCES);
        this.product = new Product(3, Color.BLUE, 3.2, false, ReceiptType.CARDBOARD, "001-C");

        // clean data
        itemRepository.deleteAll();
        productRepository.deleteAll();
        sectionRepository.deleteAll();
    }

    @Test
    @DisplayName("Add product to section should be ok")
    public void addProductToSection_ShouldBeOk() throws Exception {
        sectionService.saveSection(section);
        productService.saveProduct(product);

        int cant = 10;

        this.mockMvc.perform(post("/section/{sectionId}/product/{productId}/{cant}", section.getId(), product.getId(), cant)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(document("section/add-product-to-section"));
    }

    @Test
    @DisplayName("Add a new section")
    void createSection_shouldBeOk() throws Exception {

        String jsonData = mapper.writeValueAsString(section);

        this.mockMvc.perform(post("/section").contentType(MediaType.APPLICATION_JSON).content(jsonData)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(greaterThan(0)))
                .andDo(document("section/add-new-section"));
    }

    @Test
    @DisplayName("Edit a section")
    public void editSection_ShouldBeOk() throws Exception {
        sectionService.saveSection(section);

        section.setProductType(ProductType.CLEANLINESS);
        String jsonData = mapper.writeValueAsString(section);

        this.mockMvc.perform(put("/section")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData)
                        .with(csrf()))
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(section.getId()))
                .andExpect(jsonPath("$.productType").value(ProductType.CLEANLINESS.name()))
                .andDo(document("section/edit-section"));
    }

    @Test
    @DisplayName("Delete section")
    public void deleteSection_shouldBeOk() throws Exception {
        sectionService.saveSection(section);

        long id = section.getId();

        this.mockMvc.perform(delete("/section/{id}", id)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(document("section/delete-section"));
        assertNull(sectionRepository.findById(id).orElse(null));
    }

    @Test
    @DisplayName("Only admin can delete section")
    @WithMockUser(username = "user")
    public void deleteSectionAsUser_ShouldBeUnauthorized() throws Exception {
        sectionService.saveSection(section);

        long id = section.getId();

        this.mockMvc.perform(delete("/section/{id}", id)
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andDo(document("section/delete-section-only-admin"));
        assertNotNull(sectionRepository.findById(id).orElse(null));
    }

    @Test
    @DisplayName("Get section details")
    @WithMockUser(username = "admin")
    public void getSectionDetails() throws Exception {
        sectionService.saveSection(section);
        long id = section.getId();

        this.mockMvc.perform(get("/section/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(section.getId()))
                .andExpect(jsonPath("$.size").value(section.getSize()))
                .andExpect(jsonPath("$.productType").value(section.getProductType().name()))
                .andDo(document("/section/get-section-by-id"));
    }

    @Test
    @DisplayName("Get does not exists")
    @WithMockUser(username = "admin")
    public void getNoExistingSection() throws Exception {
        this.mockMvc.perform(get("/section/{id}", 20l)
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.details").value("uri=/section/20"))
                .andExpect(jsonPath("$.message").value("The section does not exist."))
                .andDo(document("/section/section-does-not-exist"));
    }
}
