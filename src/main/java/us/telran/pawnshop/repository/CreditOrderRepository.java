package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.telran.pawnshop.entity.CreditOrder;

public interface CreditOrderRepository extends JpaRepository<CreditOrder, Long> {

}
