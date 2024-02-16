package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.LoanCreationRequest;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {

    void newLoan(LoanCreationRequest loanCreationRequest);

    List<Loan> getAllLoans();

    Loan getLoanById(Long loanId);

    void updateLoan(Long creditId, BigDecimal creditAmount, BigDecimal ransomAmount, LoanTerm term, LoanStatus status);

    void deleteLoan(Long creditId);

    void updateLoanStatus(Long loanId);
}
