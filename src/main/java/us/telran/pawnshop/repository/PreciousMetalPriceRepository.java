package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.entity.enums.MetalPurity;

import java.util.Optional;

@Repository
public interface PreciousMetalPriceRepository extends JpaRepository<PreciousMetalPrice, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM metal_price WHERE purity LIKE :purity% ")
    Optional<PreciousMetalPrice> findByPurity(MetalPurity purity);

}
