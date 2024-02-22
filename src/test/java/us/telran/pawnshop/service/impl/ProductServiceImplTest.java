package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.dto.ProductCreationRequest;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductStatus;
import us.telran.pawnshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static us.telran.pawnshop.entity.enums.ProductStatus.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl underTest;

    @Test
    void canAddNewProduct() {
        //Given
        ProductCreationRequest creationRequest = new ProductCreationRequest();
        creationRequest.setProductName("Test product");
        creationRequest.setInterestRate(BigDecimal.valueOf(15.0));

        Product product = new Product();
        product.setProductName(creationRequest.getProductName());
        product.setStatus(ACTIVE);
        product.setInterestRate(creationRequest.getInterestRate());

        //When
        when(productRepository.findByProductName(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Throwable thrown = catchThrowable(() -> underTest.addNewProduct(creationRequest));

        //Then
        assertThat(thrown).isNull();
        verify(productRepository, times(1)).findByProductName(creationRequest.getProductName());
        verify(productRepository, times(1)).save(any(Product.class));

    }

    @Test
    public void willThrowIfProductExists() {
        //Given
        ProductCreationRequest creationRequest = new ProductCreationRequest();
        creationRequest.setProductName("Test product");
        creationRequest.setInterestRate(BigDecimal.valueOf(15.0));

        Product existingProduct = new Product();
        existingProduct.setProductName("Test product");
        existingProduct.setStatus(ACTIVE);
        existingProduct.setInterestRate(BigDecimal.valueOf(15.0));

        //When
        //Then
        when(productRepository.findByProductName(anyString())).thenReturn(Optional.of(existingProduct));

        Throwable thrown = catchThrowable(() -> underTest.addNewProduct(creationRequest));

        Assertions.assertThat(thrown)
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Product presented");

        verify(productRepository, times(1)).findByProductName(creationRequest.getProductName());
        verify(productRepository, times(0)).save(any(Product.class));
    }



    @Test
    void getProducts() {
        //When
        underTest.getProducts();
        //Then
        verify(productRepository).findAll();
    }

    @Test
    void canUpdateProduct() {
        //Given
        Long productId = 1L;
        String productName = "Updated Product";
        ProductStatus productStatus = INACTIVE;
        BigDecimal interestRate = new BigDecimal("25");

        Product existingProduct = new Product();
        existingProduct.setProductName("Existing Product");
        existingProduct.setStatus(ProductStatus.ACTIVE);
        existingProduct.setInterestRate(new BigDecimal("22"));

        //When
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        underTest.updateProduct(productId, productName, productStatus, interestRate);

        //Then
        assertThat(existingProduct.getProductName()).isEqualTo(productName);
        assertThat(existingProduct.getStatus()).isEqualTo(productStatus);
        assertThat(existingProduct.getInterestRate()).isEqualTo(interestRate);
    }

    @Test
    public void willThrowWhenProductForUpdateNotFound() {
        //Given
        Long productId = 1L;
        String productName = "Updated Product";
        BigDecimal interestRate = new BigDecimal("5");

        //When
        //Then
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> underTest.updateProduct(productId, productName, INACTIVE, interestRate));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Product with id " + productId + " doesn't exist");
    }

    @Test
    void deleteProduct() {
        //Given
        Long productId = 1L;
        //When
        underTest.deleteProduct(productId);
        //Then
        verify(productRepository, times(1)).deleteById(productId);
    }
}