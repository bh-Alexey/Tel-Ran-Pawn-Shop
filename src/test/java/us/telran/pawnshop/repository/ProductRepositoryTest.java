package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository underTest;

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfProductNameExist() {
        //given
        String productName = "Test Product";
        Product product = new Product();
        product.setProductName(productName);
        product.setStatus(ProductStatus.ACTIVE);
        product.setInterestRate(BigDecimal.valueOf(35));

        underTest.save(product);

        //when
        Optional<Product> expected = underTest.findByProductName(productName);

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(product);

    }

    @Test
    void itShouldCheckIfProductNameDoesNotExist() {
        //given
        String productName = "Test Product";

        //when
        Optional<Product> expected = underTest.findByProductName(productName);

        //then
        assertThat(expected).isEmpty();
    }
}