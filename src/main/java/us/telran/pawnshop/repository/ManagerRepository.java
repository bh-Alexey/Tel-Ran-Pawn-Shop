package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Manager;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM managers WHERE email LIKE CONCAT(:email, '%')")
    Optional<Manager> findManagerByEmail(String email);
}
