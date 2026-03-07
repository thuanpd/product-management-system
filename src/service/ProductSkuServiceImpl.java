package service;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Product;
import model.ProductSku;
import service.IProductSkuService;

public class ProductSkuServiceImpl implements IProductSkuService {

    private static final String FILE_PATH = "data/skus.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private List<ProductSku> listSkus;
    private BigInteger currentId = BigInteger.ZERO;

    public ProductSkuServiceImpl() {
        this.listSkus = new ArrayList<>();
        new File("data").mkdirs();
        loadFromCSV();
    }

    // Get SKUs by product ID
    @Override
    public List<ProductSku> getSkusByProductId(BigInteger productId) {
        return listSkus.stream()
                .filter(sku -> sku.getProduct() != null &&
                        sku.getProduct().getId().equals(productId))
                .collect(Collectors.toList());
    }

    // Add new SKU
    @Override
    public void addSku(ProductSku sku) throws Exception {

        validateSku(sku);

        // Check duplicate skuCode
        boolean exists = listSkus.stream()
                .anyMatch(s -> s.getSkuCode().equalsIgnoreCase(sku.getSkuCode()));

        if (exists) {
            throw new Exception("SKU code already exists");
        }

        if (sku.getId() == null) {
            currentId = currentId.add(BigInteger.ONE);
            sku.setId(currentId);
        } else {

            boolean idExists = listSkus.stream()
                    .anyMatch(s -> s.getId().equals(sku.getId()));

            if (idExists) {
                throw new Exception("SKU ID already exists");
            }

            if (sku.getId().compareTo(currentId) > 0) {
                currentId = sku.getId();
            }
        }

        sku.setCreatedAt(LocalDateTime.now());

        listSkus.add(sku);

        saveToCSV();
    }

    // Update SKU
    @Override
    public void updateSku(ProductSku sku) throws Exception {

        if (sku.getId() == null) {
            throw new Exception("SKU ID cannot be null");
        }

        validateSku(sku);

        boolean found = false;

        for (ProductSku item : listSkus) {

            if (item.getId().equals(sku.getId())) {

                item.setSkuCode(sku.getSkuCode());
                item.setColor(sku.getColor());
                item.setSize(sku.getSize());
                item.setMaterial(sku.getMaterial());
                item.setQuantityInStock(sku.getQuantityInStock());
                item.setWeight(sku.getWeight());
                item.setBarcode(sku.getBarcode());
                item.setActive(sku.isActive());

                item.setUpdatedAt(LocalDateTime.now());

                found = true;
                break;
            }
        }

        if (!found) {
            throw new Exception("SKU not found for update");
        }

        saveToCSV();
    }

    // Delete SKU
    @Override
    public void deleteSku(BigInteger id) throws Exception {

        if (id == null) {
            throw new Exception("SKU ID cannot be null");
        }

        boolean removed = listSkus.removeIf(s -> s.getId().equals(id));

        if (!removed) {
            throw new Exception("SKU ID not found");
        }

        saveToCSV();
    }

    // Validate SKU data
    private void validateSku(ProductSku sku) throws Exception {

        if (sku == null) {
            throw new Exception("SKU cannot be null");
        }

        if (sku.getSkuCode() == null || sku.getSkuCode().trim().isEmpty()) {
            throw new Exception("SKU code is required");
        }

        if (sku.getProduct() == null || sku.getProduct().getId() == null) {
            throw new Exception("Product ID is required");
        }

        if (sku.getQuantityInStock() < 0) {
            throw new Exception("Quantity cannot be negative");
        }
    }

    // Load data from CSV
    private void loadFromCSV() {

        File file = new File(FILE_PATH);

        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length >= 10) {

                    ProductSku s = new ProductSku();

                    s.setId(new BigInteger(data[0]));
                    s.setSkuCode(data[1]);
                    s.setColor(data[2]);
                    s.setSize(data[3]);
                    s.setMaterial(data[4]);
                    s.setQuantityInStock(Integer.parseInt(data[5]));
                    s.setWeight(Double.parseDouble(data[6]));
                    s.setBarcode(data[7]);
                    s.setActive(Boolean.parseBoolean(data[8]));

                    Product p = new Product();
                    p.setId(new BigInteger(data[9]));
                    s.setProduct(p);

                    listSkus.add(s);

                    if (s.getId().compareTo(currentId) > 0) {
                        currentId = s.getId();
                    }
                }

            }

        } catch (Exception e) {
            System.err.println("Error reading SKUs: " + e.getMessage());
        }
    }

    // Save data to CSV
    private void saveToCSV() throws Exception {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            bw.write("id,skuCode,color,size,material,quantityInStock,weight,barcode,active,productId");
            bw.newLine();

            for (ProductSku s : listSkus) {

                String productId =
                        s.getProduct() != null ? s.getProduct().getId().toString() : "";

                String line =
                        s.getId() + "," +
                                clean(s.getSkuCode()) + "," +
                                clean(s.getColor()) + "," +
                                clean(s.getSize()) + "," +
                                clean(s.getMaterial()) + "," +
                                s.getQuantityInStock() + "," +
                                s.getWeight() + "," +
                                clean(s.getBarcode()) + "," +
                                s.isActive() + "," +
                                productId;

                bw.write(line);
                bw.newLine();
            }
        }
    }

    private String clean(String text) {
        return text == null ? "" : text.replace(",", " ");
    }
}