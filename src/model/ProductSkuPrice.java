package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class ProductSkuPrice {

    private BigInteger id;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String currency;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private boolean active;

    private ProductSku productSku;

    public ProductSkuPrice() {
    }

    public ProductSkuPrice(BigInteger id, BigDecimal price, ProductSku productSku) {
        this.id = id;
        this.price = price;
        this.productSku = productSku;
        this.startDate = LocalDateTime.now();
        this.active = true;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }
}