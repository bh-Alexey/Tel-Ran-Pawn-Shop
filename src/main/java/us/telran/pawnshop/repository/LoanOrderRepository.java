package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.telran.pawnshop.entity.LoanOrder;

public interface LoanOrderRepository extends JpaRepository<LoanOrder, Long> {

}
