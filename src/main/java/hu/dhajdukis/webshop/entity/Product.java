package hu.dhajdukis.webshop.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.*;

import hu.dhajdukis.webshop.dto.ProductDto;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private BigDecimal currentPrice;
    private OffsetDateTime lastUpdate;

    public Product() {
    }

    public Product(final ProductDto productDto) {
        this.name = productDto.getName();
        this.currentPrice = productDto.getCurrentPrice();
        this.lastUpdate = OffsetDateTime.now();
    }

    public void updateProduct(final ProductDto productDto) {
        this.lastUpdate = OffsetDateTime.now();
        this.name = productDto.getName();
        this.currentPrice = productDto.getCurrentPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(final BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public OffsetDateTime getLastUpdate() {
        return lastUpdate;
    }
}
