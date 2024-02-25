package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.PledgeStatus;

import java.util.List;

@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long> {

    @Query("SELECT p FROM Pledge p WHERE p.status = ?1" )
    List<Pledge> findAllByStatus(PledgeStatus status);

}
