package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.PawnBranch;

@Repository
public interface PawnBranchRepository extends JpaRepository<PawnBranch, Long> {

}
