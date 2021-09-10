package aem.example.tdd.ecasastorage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SectionItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Section section;

    @ManyToOne
    private Product product;

    private int quantity;

    public SectionItem() {
    }

    public SectionItem(Section section, Product product, int quantity) {
        this.section = section;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
