package service;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.ProductSku;
import model.ProductSkuPrice;
import service.IProductSkuPriceService;

public class ProductSkuPriceServiceImpl implements IProductSkuPriceService {

    private static final String FILE_PATH = "data/sku_prices.csv";

    private List<ProductSkuPrice> listPrices;
    private BigInteger currentId = BigInteger.ZERO;

    public ProductSkuPriceServiceImpl() {
        this.listPrices = new ArrayList<>();
        new File("data").mkdirs();
        loadFromCSV();
    }

    // Get prices by SKU code
    @Override
    public List<ProductSkuPrice> getPricesBySkuCode(String skuCode) {
        return listPrices.stream()
                .filter(p -> p.getProductSku() != null &&
                        skuCode.equals(p.getProductSku().getSkuCode()))
                .collect(Collectors.toList());
    }

    // Add new price
    @Override
    public void addPrice(ProductSkuPrice price) throws Exception {

        validatePrice(price);

        if (price.getId() == null) {
            currentId = currentId.add(BigInteger.ONE);
            price.setId(currentId);
        } else {
            if (price.getId().compareTo(currentId) > 0) {
                currentId = price.getId();
            }
        }

        listPrices.add(price);
        saveToCSV();
    }

    // Update price
    @Override
    public void updatePrice(ProductSkuPrice price) throws Exception {

        if (price.getId() == null) {
            throw new Exception("Price ID cannot be null");
        }

        validatePrice(price);

        boolean found = false;

        for (ProductSkuPrice item : listPrices) {
            if (item.getId().equals(price.getId())) {

                item.setPrice(price.getPrice());
                item.setOriginalPrice(price.getOriginalPrice());
                item.setCurrency(price.getCurrency());
                item.setActive(price.isActive());

                found = true;
                break;
            }
        }

        if (!found) {
            throw new Exception("Price not found for update");
        }

        saveToCSV();
    }

    // Delete price
    @Override
    public void deletePrice(BigInteger id) throws Exception {

        if (id == null) {
            throw new Exception("Price ID cannot be null");
        }

        boolean removed = listPrices.removeIf(p -> p.getId().equals(id));

        if (!removed) {
            throw new Exception("Price ID not found");
        }

        saveToCSV();
    }

    // Validate price data
    private void validatePrice(ProductSkuPrice price) throws Exception {

        if (price == null) {
            throw new Exception("Price cannot be null");
        }

        if (price.getPrice() == null) {
            throw new Exception("Price is required");
        }

        if (price.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Price cannot be negative");
        }

        if (price.getCurrency() == null || price.getCurrency().trim().isEmpty()) {
            throw new Exception("Currency is required");
        }

        if (price.getProductSku() == null ||
                price.getProductSku().getSkuCode() == null ||
                price.getProductSku().getSkuCode().trim().isEmpty()) {
            throw new Exception("SKU code is required");
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

                if (data.length >= 6) {

                    ProductSkuPrice p = new ProductSkuPrice();

                    p.setId(new BigInteger(data[0]));
                    p.setPrice(new BigDecimal(data[1]));

                    if (!data[2].isEmpty()) {
                        p.setOriginalPrice(new BigDecimal(data[2]));
                    }

                    p.setCurrency(data[3]);
                    p.setActive(Boolean.parseBoolean(data[4]));

                    ProductSku sku = new ProductSku();
                    sku.setSkuCode(data[5]);

                    p.setProductSku(sku);

                    listPrices.add(p);

                    if (p.getId().compareTo(currentId) > 0) {
                        currentId = p.getId();
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading prices: " + e.getMessage());
        }
    }

    // Save data to CSV
    private void saveToCSV() throws Exception {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            bw.write("id,price,originalPrice,currency,active,skuCode");
            bw.newLine();

            for (ProductSkuPrice p : listPrices) {

                String originalPrice =
                        p.getOriginalPrice() != null ? p.getOriginalPrice().toString() : "";

                String skuCode =
                        p.getProductSku() != null ? p.getProductSku().getSkuCode() : "";

                String line =
                        p.getId() + "," +
                                p.getPrice() + "," +
                                originalPrice + "," +
                                p.getCurrency() + "," +
                                p.isActive() + "," +
                                skuCode;

                bw.write(line);
                bw.newLine();
            }
        }
    }
}