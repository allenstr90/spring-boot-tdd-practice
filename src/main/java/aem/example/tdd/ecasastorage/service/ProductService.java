package aem.example.tdd.ecasastorage.service;

import aem.example.tdd.ecasastorage.entity.Product;
import aem.example.tdd.ecasastorage.repository.ProductRepository;
import aem.example.tdd.ecasastorage.repository.SpecsProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> saveAllProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public List<Product> findAll(Map<String, String> queryParams) {
        if (queryParams == null || queryParams.isEmpty())
            return productRepository.findAll();
        return productRepository.findAll(SpecsProvider.filterProduct(queryParams));
    }
}
