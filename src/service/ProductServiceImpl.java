package service;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import service.IProductService;

public class ProductServiceImpl implements IProductService {

    private static final String FILE_PATH = "data/products.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private List<Product> listProducts;
    private BigInteger currentId = BigInteger.ZERO;

    public ProductServiceImpl() {
        this.listProducts = new ArrayList<>();
        // Đảm bảo thư mục data tồn tại
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        loadFromCSV();
    }

    @Override
    public List<Product> getAllProducts() {
        return this.listProducts;
    }

    @Override
    public void addProduct(Product p) throws Exception {
        if (p.getId() == null) {
            currentId = currentId.add(BigInteger.ONE);
            p.setId(currentId);
        } else {
            for(Product exist : listProducts) {
                if(exist.getId().equals(p.getId())) {
                    throw new Exception("ID đã tồn tại! Vui lòng để trống để hệ thống tự cấp.");
                }
            }
            if (p.getId().compareTo(currentId) > 0) {
                currentId = p.getId();
            }
        }

        p.setCreatedAt(LocalDateTime.now());
        listProducts.add(p);
        saveToCSV();
    }

    @Override
    public void updateProduct(Product p) throws Exception {
        boolean found = false;
        for (Product item : listProducts) {
            if (item.getId().equals(p.getId())) {
                item.setProductCode(p.getProductCode());
                item.setName(p.getName());
                item.setDescription(p.getDescription());
                item.setCategory(p.getCategory());
                item.setBrand(p.getBrand());
                item.setActive(p.isActive());
                item.setUpdatedAt(LocalDateTime.now());
                found = true;
                break;
            }
        }
        if (!found) {
            throw new Exception("Không tìm thấy sản phẩm để cập nhật!");
        }
        saveToCSV();
    }

    @Override
    public void deleteProduct(BigInteger id) throws Exception {
        boolean removed = listProducts.removeIf(p -> p.getId().equals(id));
        if (!removed) {
            throw new Exception("Không tìm thấy ID sản phẩm để xóa!");
        }
        saveToCSV();
    }

    private void loadFromCSV() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] data = line.split(",", -1);
                if (data.length >= 7) {
                    Product p = new Product();
                    p.setId(new BigInteger(data[0]));
                    p.setProductCode(data[1]);
                    p.setName(data[2]);
                    p.setDescription(data[3]);
                    p.setCategory(data[4]);
                    p.setBrand(data[5]);
                    p.setActive(Boolean.parseBoolean(data[6]));

                    if (data.length > 7 && !data[7].isEmpty()) p.setCreatedAt(LocalDateTime.parse(data[7], FORMATTER));
                    if (data.length > 8 && !data[8].isEmpty()) p.setUpdatedAt(LocalDateTime.parse(data[8], FORMATTER));

                    listProducts.add(p);
                    if (p.getId().compareTo(currentId) > 0) currentId = p.getId();
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    private void saveToCSV() throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("id,productCode,name,description,category,brand,active,createdAt,updatedAt");
            bw.newLine();
            for (Product p : listProducts) {
                String line = p.getId() + "," + cleanText(p.getProductCode()) + ","
                        + cleanText(p.getName()) + "," + cleanText(p.getDescription()) + ","
                        + cleanText(p.getCategory()) + "," + cleanText(p.getBrand()) + ","
                        + p.isActive() + ","
                        + (p.getCreatedAt() != null ? p.getCreatedAt().format(FORMATTER) : "") + ","
                        + (p.getUpdatedAt() != null ? p.getUpdatedAt().format(FORMATTER) : "");
                bw.write(line);
                bw.newLine();
            }
        }
    }

    private String cleanText(String text) {
        return text == null ? "" : text.replace(",", " ");
    }
}