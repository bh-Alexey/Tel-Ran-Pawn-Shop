package us.telran.pawnshop.service;

import jakarta.transaction.Transactional;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.entity.LoanOrder;

import java.util.List;

public interface LoanOrderService {


    void createLoanReceiptOrder(LoanOrderRequest loanOrderRequest);

    void createLoanExpenseOrder(LoanOrderRequest loanOrderRequest);

    void createLoanProlongationOrder(LoanOrderRequest loanOrderRequest);

    List<LoanOrder> getAllLoanOrders();

}
