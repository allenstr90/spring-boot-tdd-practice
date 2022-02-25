package aem.example.tdd.ecasastorage.controller;

import aem.example.tdd.ecasastorage.entity.Color;
import aem.example.tdd.ecasastorage.entity.Product;
import aem.example.tdd.ecasastorage.entity.ProductType;
import aem.example.tdd.ecasastorage.entity.ReceiptType;
import aem.example.tdd.ecasastorage.entity.Section;
import aem.example.tdd.ecasastorage.entity.SectionItem;
import aem.example.tdd.ecasastorage.repository.ProductRepository;
import aem.example.tdd.ecasastorage.repository.SectionItemRepository;
import aem.example.tdd.ecasastorage.repository.SectionRepository;
import aem.example.tdd.ecasastorage.service.ProductService;
import aem.example.tdd.ecasastorage.service.SectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
@AutoConfigureRestDocs
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SectionItemRepository itemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SectionService sectionService;

    private Product product;

    @BeforeEach
    void setUp() {
        this.productService = new ProductService(productRepository);
        this.product = new Product(3, Color.BLUE, 3.2, false, ReceiptType.CARDBOARD, "001-C");
        // clean data
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Get product list")
    void getProductList_ShouldBeOK() throws Exception {
        productService.saveProduct(product);

        this.mockMvc
                .perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].size").value(3))
                .andExpect(jsonPath("$.[0].color").value(Color.BLUE.name()))
                .andExpect(jsonPath("$.[0].price").value(3.2))
                .andExpect(jsonPath("$.[0].receiptType").value(ReceiptType.CARDBOARD.name()))
                .andExpect(jsonPath("$.[0].lot").value("001-C"))
                .andDo(document("product/get-product-list"));
    }

    @Test
    @DisplayName("Filter products by lot")
    public void filterProductByLot() throws Exception {
        saveBatchProducts();

        URI uri = UriComponentsBuilder.fromPath("/product").query("lot={lot}").buildAndExpand("001-C").toUri();
        this.mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(document("product/filter-by-lot"));
    }

    @Test
    @DisplayName("Filter products by color")
    public void filterProductByColor() throws Exception {
        saveBatchProducts();

        URI uri = UriComponentsBuilder.fromPath("/product").query("color={color}").buildAndExpand(Color.BLUE.name()).toUri();
        this.mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andDo(document("product/filter-by-color"));
    }

    @Test
    @DisplayName("Filter products by fragility")
    public void filterProductByFragility() throws Exception {
        saveBatchProducts();

        URI uri = UriComponentsBuilder.fromPath("/product").query("isFragile={isFragile}").buildAndExpand(true).toUri();
        this.mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(document("product/filter-by-fragility"));
    }

    @Test
    @DisplayName("Filter products by combined criteria")
    public void filterProductByCombinedCriteria() throws Exception {
        saveBatchProducts();

        URI uri = UriComponentsBuilder.fromPath("/product")
                .query("isFragile={isFragile}&color={color}")
                .buildAndExpand(false, Color.BLUE)
                .toUri();
        this.mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(document("product/filter-combined"));
    }

    @Test
    @DisplayName("Filter products by Section")
    public void filterProductBySection() throws Exception {
        saveBatchProducts();
        Section oneSection = sectionRepository.save(new Section(10, ProductType.CLOTHING));
        Product oneProduct = productService.saveProduct(new Product(3, Color.RED, 10, true, ReceiptType.CRYSTAL, "1001-S1"));
        sectionService.addProductToSection(oneProduct.getId(), oneSection.getId(), 10);

        URI uri = UriComponentsBuilder.fromPath("/product")
                .query("section={sectionId}")
                .buildAndExpand(oneSection.getId())
                .toUri();

        this.mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(oneProduct.getId()))
                .andDo(document("product/filter-by-section"));
    }

    @Test
    @DisplayName("Filter products ignore invalid filters")
    public void filterProductIgnoreInvalidFilter() throws Exception {
        saveBatchProducts();

        URI uri = UriComponentsBuilder.fromPath("/product")
                .query("invalidFilter={invalid}")
                .buildAndExpand("doNothing")
                .toUri();
        this.mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(5))
                .andDo(document("product/invalid-filters-ignored"));
    }

    private void saveBatchProducts() {
        List<Product> productList = Arrays.asList(
                new Product(3, Color.BLUE, 13.2, false, ReceiptType.CARDBOARD, "001-C"),
                new Product(5, Color.RED, 23.2, true, ReceiptType.CRYSTAL, "001-CY"),
                new Product(20, Color.BLUE, 343.2, true, ReceiptType.NYLON, "001-N"),
                new Product(15, Color.RED, 332.2, false, ReceiptType.PLASTIC, "001-P"),
                new Product(30, Color.BLUE, 100, false, ReceiptType.CARDBOARD, "001-C")
        );
        productService.saveAllProducts(productList);
    }
}
