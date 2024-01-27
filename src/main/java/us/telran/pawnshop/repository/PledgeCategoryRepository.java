package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.util.Optional;

@Repository
public interface PledgeCategoryRepository extends JpaRepository<PledgeCategory, Long> {
    @Query("SELECT pc FROM PledgeCategory pc WHERE pc.categoryName = ?1")
    Optional<PledgeCategory> findByCategoryName(PreciousMetal categoryName);

}
