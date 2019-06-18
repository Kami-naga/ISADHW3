package com.eis.hw.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue
    private Long productId;

    private String name;

    @OneToMany
    private List<Instrument> instruments;

    private String imgUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, name);
    }
}
