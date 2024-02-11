package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.LoanOrder;
import us.telran.pawnshop.repository.LoanOrderRepository;
import us.telran.pawnshop.repository.LoanRepository;
import us.telran.pawnshop.service.LoanOrderService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanOrderServiceImpl implements LoanOrderService {

    private final LoanRepository loanRepository;
    private final LoanOrderRepository loanOrderRepository;

    @Override
    @Transactional
    public void createLoanReceiptOrder() {



    }

    @Override
    public void createLoanReceiptOrder(LoanOrderRequest loanOrderRequest) {

    }

    @Override
    @Transactional
    public void createLoanExpenseOrder(LoanOrderRequest loanOrderRequest) {

        LoanOrder loanOrder = new LoanOrder();

        Optional<Loan> loanOptional = loanRepository.findById(loanOrderRequest.getLoanId());
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            loanOrder.setLoanId(loan);
        }

        loanOrder.setOperationAmount(loanOrderRequest.getLoanAmount());

    }

    @Override
    @Transactional
    public void createLoanProlongationOrder(LoanOrderRequest loanOrderRequest) {

    }

    @Override
    public List<LoanOrder> getLoanOrders() {
        return null;
    }

}
