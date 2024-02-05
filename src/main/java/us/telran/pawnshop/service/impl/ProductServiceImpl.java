package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.dto.ProductCreationRequest;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;
import us.telran.pawnshop.repository.ProductRepository;
import us.telran.pawnshop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void addNewProduct(ProductCreationRequest productCreationRequest) {
        Optional<Product> productOptional = productRepository
                .findByProductName(productCreationRequest.getProductName());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("Product presented");
        }

        Product product = new Product();
        product.setProductName(productCreationRequest.getProductName());
        product.setStatus(ProductStatus.ACTIVE);
        product.setInterestRate(productCreationRequest.getInterestRate());
        productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void updateProduct(Long productId,
                              ProductName productName,
                              ProductStatus productStatus,
                              BigDecimal interestRate
    ) {
        Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalStateException("Product with id " + productId + " doesn't exist"));

        if (productName != null && !Objects.equals(product.getProductName(), productName)) {
            product.setProductName(productName);
        }

        if (productStatus != null && !Objects.equals(product.getStatus(), productStatus)) {
            product.setStatus(productStatus);
        }

        if (interestRate != null && !Objects.equals(product.getInterestRate(), interestRate)) {
            product.setInterestRate(interestRate);
        }

    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
