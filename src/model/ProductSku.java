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
    }

    public ProductSku(BigInteger id, String skuCode, Product product) {
        this.id = id;
        this.skuCode = skuCode;
        this.product = product;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductSkuPrice> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductSkuPrice> prices) {
        this.prices = prices;
    }
}