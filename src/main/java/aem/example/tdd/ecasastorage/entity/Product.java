package aem.example.tdd.ecasastorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private long id;

    private int size;

    @Enumerated(EnumType.STRING)
    private Color color;

    private double price;
    private boolean isFragile;

    @Enumerated(EnumType.STRING)
    private ReceiptType receiptType;

    private String lot;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "product")
    private List<SectionItem> sections;

    public Product() {
    }

    public Product(int size, Color color, double price, boolean isFragile, ReceiptType receiptType, String lot) {
        this.size = size;
        this.color = color;
        this.price = price;
        this.isFragile = isFragile;
        this.receiptType = receiptType;
        this.lot = lot;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public void setFragile(boolean fragile) {
        isFragile = fragile;
    }

    public ReceiptType getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(ReceiptType receiptType) {
        this.receiptType = receiptType;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
}
