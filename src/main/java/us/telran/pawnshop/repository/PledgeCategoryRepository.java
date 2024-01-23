package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.PledgeCategory;

@Repository
public interface PledgeCategoryRepository extends JpaRepository<PledgeCategory, Long> {

}
