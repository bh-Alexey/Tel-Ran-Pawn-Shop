package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.Product;

import java.util.Optional;

@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long> {
    @Query("SELECT pl.product FROM Pledge pl WHERE pl.pledgeId = ?1")
    Optional<Product> findProductByPledgeId(Long pledgeId);

}
