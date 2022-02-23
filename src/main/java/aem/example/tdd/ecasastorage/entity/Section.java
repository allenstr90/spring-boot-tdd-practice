package aem.example.tdd.ecasastorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int size;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "section")
    private final List<SectionItem> products = new ArrayList<>();

    public Section() {
    }

    public Section(int size, ProductType productType) {
        this.size = size;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void addSectionItem(SectionItem sectionItem) {
        this.products.add(sectionItem);
        sectionItem.setSection(this);
    }

    public void removeSectionItem(SectionItem sectionItem) {
        products.remove(sectionItem);
        sectionItem.setSection(null);
    }
}
