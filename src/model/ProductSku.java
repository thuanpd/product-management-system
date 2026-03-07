package model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class ProductSku {

    private BigInteger id;
    private String skuCode;
    private String color;
    private String size;
    private String material;

    private int quantityInStock;
    private double weight;
    private String barcode;

    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Product product;
    private List<ProductSkuPrice> prices;

    public ProductSku() {
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public ProductSku(BigInteger id, String skuCode, Product product) {
        setId(id);
        setSkuCode(skuCode);
        setProduct(product);
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public ProductSku(BigInteger id, String skuCode, String color, String size, String material,
                      int quantityInStock, double weight, String barcode, Product product) {
        setId(id);
        setSkuCode(skuCode);
        setColor(color);
        setSize(size);
        setMaterial(material);
        setQuantityInStock(quantityInStock);
        setWeight(weight);
        setBarcode(barcode);
        setProduct(product);
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        if (id == null) {
            throw new IllegalArgumentException("SKU ID cannot be null");
        }
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        if (skuCode == null || skuCode.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU code cannot be empty");
        }
        this.skuCode = skuCode.trim();
        touchUpdateTime();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = (color != null) ? color.trim() : null;
        touchUpdateTime();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = (size != null) ? size.trim() : null;
        touchUpdateTime();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = (material != null) ? material.trim() : null;
        touchUpdateTime();
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        if (quantityInStock < 0) {
            throw new IllegalArgumentException("Quantity in stock cannot be negative");
        }
        this.quantityInStock = quantityInStock;
        touchUpdateTime();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.weight = weight;
        touchUpdateTime();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = (barcode != null) ? barcode.trim() : null;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null for SKU");
        }
        this.product = product;
        touchUpdateTime();
    }

    public List<ProductSkuPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductSkuPrice> prices) {
        this.prices = prices;
    }

    private void touchUpdateTime() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ProductSku{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", material='" + material + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", weight=" + weight +
                ", barcode='" + barcode + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}