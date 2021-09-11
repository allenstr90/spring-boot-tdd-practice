package aem.example.tdd.ecasastorage.service;

import org.springframework.stereotype.Service;

import aem.example.tdd.ecasastorage.entity.Product;
import aem.example.tdd.ecasastorage.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
