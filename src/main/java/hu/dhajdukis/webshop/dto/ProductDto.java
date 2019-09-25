package hu.dhajdukis.webshop.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import hu.dhajdukis.webshop.entity.Product;

public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private OffsetDateTime lastUpdate;

    @JsonCreator
    public ProductDto(
            @JsonProperty(value = "id") final Long id,
            @JsonProperty(value = "name", required = true) final String name,
            @JsonProperty(value = "currentPrice", required = true) final BigDecimal currentPrice,
            @JsonProperty(value = "lastUpdate") final OffsetDateTime lastUpdate) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdate = lastUpdate;
    }

    public ProductDto(final Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.currentPrice = product.getCurrentPrice();
        this.lastUpdate = product.getLastUpdate();
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

    public void setLastUpdate(final OffsetDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
