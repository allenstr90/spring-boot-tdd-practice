package aem.example.tdd.ecasastorage.entity;

import javax.persistence.*;

@Entity
@Table(name = "section_item")
public class SectionItem {

    @EmbeddedId
    private SectionProductKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sectionId")
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    public SectionItem() {
    }

    public SectionItem(Section section, Product product, int quantity) {
        this.section = section;
        this.product = product;
        this.quantity = quantity;
        this.id = new SectionProductKey();
        this.id.setSectionId(section.getId());
        this.id.setProductId(product.getId());
    }

    public SectionProductKey getId() {
        return id;
    }

    public void setId(SectionProductKey id) {
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
