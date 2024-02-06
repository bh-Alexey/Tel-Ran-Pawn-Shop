package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}
