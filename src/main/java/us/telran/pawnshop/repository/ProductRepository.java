package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ProductName;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT pr FROM Product pr WHERE pr.productName = ?1")
    Optional<Product> findByProductName(ProductName productName);
}
