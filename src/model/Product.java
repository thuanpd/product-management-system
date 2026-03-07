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
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public Product(BigInteger id, String productCode, String name) {
        setId(id);
        setProductCode(productCode);
        setName(name);
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public Product(BigInteger id, String productCode, String name, String description,
                   String category, String brand, boolean active) {
        setId(id);
        setProductCode(productCode);
        setName(name);
        setDescription(description);
        setCategory(category);
        setBrand(brand);
        setActive(active);
        this.createdAt = LocalDateTime.now();
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Product code cannot be empty");
        }
        this.productCode = productCode.trim();
        touchUpdateTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = name.trim();
        touchUpdateTime();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description.trim();
        } else {
            this.description = null;
        }
        touchUpdateTime();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category != null) {
            this.category = category.trim();
        } else {
            this.category = null;
        }
        touchUpdateTime();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand != null) {
            this.brand = brand.trim();
        } else {
            this.brand = null;
        }
        touchUpdateTime();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        touchUpdateTime();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
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

    private void touchUpdateTime() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}