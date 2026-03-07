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
        this.startDate = LocalDateTime.now();
        this.active = true;
    }

    public ProductSkuPrice(BigInteger id, BigDecimal price, ProductSku productSku) {
        setId(id);
        setPrice(price);
        setProductSku(productSku);
        this.startDate = LocalDateTime.now();
        this.active = true;
    }

    public ProductSkuPrice(BigInteger id, BigDecimal price, BigDecimal originalPrice, String currency,
                           LocalDateTime startDate, LocalDateTime endDate, ProductSku productSku) {
        setId(id);
        setPrice(price);
        setOriginalPrice(originalPrice);
        setCurrency(currency);
        setStartDate(startDate);
        setEndDate(endDate);
        setProductSku(productSku);
        this.active = true;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        if (id == null) {
            throw new IllegalArgumentException("Price ID cannot be null");
        }
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be >= 0");
        }
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        if (originalPrice != null && originalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Original price must be >= 0");
        }
        this.originalPrice = originalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }
        this.currency = currency.trim().toUpperCase();
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
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
        if (productSku == null) {
            throw new IllegalArgumentException("ProductSku cannot be null");
        }
        this.productSku = productSku;
    }

    @Override
    public String toString() {
        return "ProductSkuPrice{" +
                "id=" + id +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", currency='" + currency + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                '}';
    }
}