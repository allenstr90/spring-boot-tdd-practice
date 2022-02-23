package aem.example.tdd.ecasastorage.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("The product does not exist.");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
