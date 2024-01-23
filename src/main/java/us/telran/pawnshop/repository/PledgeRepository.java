package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Pledge;

@Repository
public interface PledgeRepository extends JpaRepository<Pledge, Long> {

}
