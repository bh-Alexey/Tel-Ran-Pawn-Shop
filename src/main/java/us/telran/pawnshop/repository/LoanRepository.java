package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.telran.pawnshop.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
