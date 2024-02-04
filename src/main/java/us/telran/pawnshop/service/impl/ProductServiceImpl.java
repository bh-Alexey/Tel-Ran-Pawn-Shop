package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;
import us.telran.pawnshop.repository.ProductRepository;
import us.telran.pawnshop.service.ProductService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void addNewProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(Long productId, ProductName productName, ProductStatus productStatus, double interestRate) {
        Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalStateException("Product with id " + productId + " doesn't exist"));

        if (productName != null && !Objects.equals(product.getProductName(), productName)) {
            product.setProductName(productName);
        }

        if (productStatus != null && !Objects.equals(product.getStatus(), productStatus)) {
            product.setStatus(productStatus);
        }

    }
}
