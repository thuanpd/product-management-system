package service;

import java.math.BigInteger;
import java.util.List;
import model.ProductSku;

public interface IProductSkuService {
    List<ProductSku> getSkusByProductId(BigInteger productId);
    void addSku(ProductSku sku) throws Exception;
    void updateSku(ProductSku sku) throws Exception;
    void deleteSku(BigInteger id) throws Exception;
}