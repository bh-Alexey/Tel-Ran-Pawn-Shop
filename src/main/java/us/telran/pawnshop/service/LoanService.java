package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.LoanCreationRequest;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;

import java.math.BigDecimal;
import java.util.List;

public interface LoanService {
    void updateCreditStatus(Long creditId, LoanStatus status);

    void newCredit(LoanCreationRequest loanCreationRequest);

    List<Loan> getCredits();

    void updateCredit(Long creditId, BigDecimal creditAmount, BigDecimal ransomAmount, LoanTerm term, LoanStatus status);

    void deleteCredit(Long creditId);
}
