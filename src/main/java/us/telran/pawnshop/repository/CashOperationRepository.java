package us.telran.pawnshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import us.telran.pawnshop.entity.CashOperation;

public interface CashOperationRepository extends JpaRepository<CashOperation, Long> {

}
