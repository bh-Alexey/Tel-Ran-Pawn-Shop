package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
