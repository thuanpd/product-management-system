package service;

import java.math.BigInteger;
import java.util.List;
import model.ProductSkuPrice;

public interface IProductSkuPriceService {
    List<ProductSkuPrice> getPricesBySkuCode(String skuCode);
    void addPrice(ProductSkuPrice price) throws Exception;
    void updatePrice(ProductSkuPrice price) throws Exception;
    void deletePrice(BigInteger id) throws Exception;
}