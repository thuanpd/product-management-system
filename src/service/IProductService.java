package service;

import java.math.BigInteger;
import java.util.List;
import model.Product;

public interface IProductService {
    List<Product> getAllProducts();
    void addProduct(Product product) throws Exception;
    void updateProduct(Product product) throws Exception;
    void deleteProduct(BigInteger id) throws Exception;
}