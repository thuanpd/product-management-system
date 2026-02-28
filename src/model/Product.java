package model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class Product {

    private BigInteger id;
    private String productCode;
    private String name;
    private String description;
    private String category;
    private String brand;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<ProductSku> skus;

    public Product() {
    }

    public Product(BigInteger id, String productCode, String name) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ProductSku> getSkus() {
        return skus;
    }

    public void setSkus(List<ProductSku> skus) {
        this.skus = skus;
    }
}