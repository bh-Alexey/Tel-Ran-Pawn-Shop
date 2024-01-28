package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;

import java.util.List;

public interface ProductService {
    void addNewProduct(Product product);

    List<Product> getProducts();

    void deleteProduct(Long productId);

    void updateProduct(Long productId, ProductName productName, ProductStatus productStatus, double interestRate);
}
