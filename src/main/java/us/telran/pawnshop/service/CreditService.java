package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.CreditCreationRequest;
import us.telran.pawnshop.entity.Credit;
import us.telran.pawnshop.entity.enums.CreditStatus;

import java.math.BigDecimal;
import java.util.List;

public interface CreditService {
    void updateCreditStatus(Long creditId, CreditStatus status);

    void newCredit(CreditCreationRequest creditCreationRequest);

    List<Credit> getCredits();

    void updateCredit(Long creditId, BigDecimal creditAmount, BigDecimal ransomAmount, Credit.CreditTerm term, CreditStatus status);

    void deleteCredit(Long creditId);
}
