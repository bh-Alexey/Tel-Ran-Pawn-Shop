package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.ProductCreationRequest;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void addNewProduct(ProductCreationRequest productCreationRequest);

    List<Product> getProducts();

    void updateProduct(Long productId,
                       String productName,
                       ProductStatus productStatus,
                       BigDecimal interestRate);

    void deleteProduct(Long productId);

}
