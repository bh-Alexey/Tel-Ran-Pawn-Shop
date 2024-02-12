package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.PawnBranch;

import java.util.Optional;

@Repository
public interface PawnBranchRepository extends JpaRepository<PawnBranch, Long> {

    @Query("SELECT b FROM PawnBranch b WHERE b.address = ?1")
    Optional<PawnBranch> findByAddress(String address);

}
