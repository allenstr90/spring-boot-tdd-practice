package aem.example.tdd.ecasastorage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Authority {
    @Id
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Empty authority is not allowed")
    private String name;

    public Authority() {
    }

    public Authority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
