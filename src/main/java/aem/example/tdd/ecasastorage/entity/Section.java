package aem.example.tdd.ecasastorage.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Section {
    @Id
    @GeneratedValue
    private long id;

    private int size;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    public Section() {
    }

    public Section(int size, ProductType productType) {
        this.size = size;
        this.productType = productType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
