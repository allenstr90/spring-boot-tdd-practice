package aem.example.tdd.ecasastorage.exception;

public class SectionNotFoundException extends RuntimeException{

    public SectionNotFoundException(String message) {
        super(message);
    }

    public SectionNotFoundException() {
        super("The section does not exist.");
    }
}
