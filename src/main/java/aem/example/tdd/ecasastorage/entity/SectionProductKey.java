package aem.example.tdd.ecasastorage.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SectionProductKey implements Serializable {

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "product_id")
    private Long productId;

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionProductKey that = (SectionProductKey) o;
        return Objects.equals(sectionId, that.sectionId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId, productId);
    }
}
