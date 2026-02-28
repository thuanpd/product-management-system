package service.impl;

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

    @Override
    public List<ProductSku> getSkusByProductId(BigInteger productId) {
        // Chỉ lọc ra các SKU thuộc về Product đang được chọn
        return listSkus.stream()
                .filter(sku -> sku.getProduct() != null && sku.getProduct().getId().equals(productId))
                .collect(Collectors.toList());
    }

    @Override
    public void addSku(ProductSku sku) throws Exception {
        if (sku.getId() == null) {
            currentId = currentId.add(BigInteger.ONE);
            sku.setId(currentId);
        } else {
            if (listSkus.stream().anyMatch(s -> s.getId().equals(sku.getId()))) {
                throw new Exception("ID SKU đã tồn tại!");
            }
            if (sku.getId().compareTo(currentId) > 0) currentId = sku.getId();
        }
        sku.setCreatedAt(LocalDateTime.now());
        listSkus.add(sku);
        saveToCSV();
    }

    @Override
    public void updateSku(ProductSku sku) throws Exception {
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
        if (!found) throw new Exception("Không tìm thấy SKU để cập nhật!");
        saveToCSV();
    }

    @Override
    public void deleteSku(BigInteger id) throws Exception {
        if (!listSkus.removeIf(s -> s.getId().equals(id))) {
            throw new Exception("Không tìm thấy ID SKU để xóa!");
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

                    // Tạo một Product ảo chỉ chứa ID để gắn vào (phục vụ việc filter)
                    Product p = new Product();
                    p.setId(new BigInteger(data[9]));
                    s.setProduct(p);

                    listSkus.add(s);
                    if (s.getId().compareTo(currentId) > 0) currentId = s.getId();
                }
            }
        } catch (Exception e) { System.err.println("Lỗi đọc SKUs: " + e.getMessage()); }
    }

    private void saveToCSV() throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("id,skuCode,color,size,material,quantityInStock,weight,barcode,active,productId");
            bw.newLine();
            for (ProductSku s : listSkus) {
                String line = s.getId() + "," + clean(s.getSkuCode()) + "," + clean(s.getColor()) + ","
                        + clean(s.getSize()) + "," + clean(s.getMaterial()) + "," + s.getQuantityInStock() + ","
                        + s.getWeight() + "," + clean(s.getBarcode()) + "," + s.isActive() + ","
                        + s.getProduct().getId(); // Lưu ID của Product
                bw.write(line);
                bw.newLine();
            }
        }
    }
    private String clean(String text) { return text == null ? "" : text.replace(",", " "); }
}