package aem.example.tdd.ecasastorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int size;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "section")
    private Set<SectionItem> products = new HashSet<>();

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

    public Set<SectionItem> getProducts() {
        return products;
    }

    public void setProducts(Set<SectionItem> products) {
        this.products = products;
    }
}
