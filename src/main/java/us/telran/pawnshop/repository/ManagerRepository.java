package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
