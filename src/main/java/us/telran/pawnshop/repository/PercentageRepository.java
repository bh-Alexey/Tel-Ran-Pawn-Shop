package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.CreditTerm;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.util.Optional;

@Repository
public interface PercentageRepository extends JpaRepository<Percentage, Long> {

    @Query("SELECT perc FROM Percentage perc WHERE perc.term = ?1")
    Optional<Percentage> findByTerm(CreditTerm term);

}
