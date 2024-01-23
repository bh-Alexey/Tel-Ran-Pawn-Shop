package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
