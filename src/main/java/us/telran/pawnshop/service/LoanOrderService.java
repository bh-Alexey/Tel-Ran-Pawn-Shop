package us.telran.pawnshop.service;

import jakarta.transaction.Transactional;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.dto.LoanProlongationRequest;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.LoanOrder;

import java.math.BigDecimal;
import java.util.List;

public interface LoanOrderService {


    void createLoanReceiptOrder(LoanOrderRequest loanOrderRequest);

    void createLoanExpenseOrder(Loan loan);

    void createLoanProlongationOrder(LoanProlongationRequest loanProlongationRequest);

    List<LoanOrder> getAllLoanOrders();

}
