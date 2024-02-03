package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.Credit;
import us.telran.pawnshop.entity.enums.CreditStatus;

import java.util.List;

public interface CreditService {
    void updateCreditStatus(Long creditId, CreditStatus status);

    void newCredit(Credit credit);

    List<Credit> getCredits();

    void updateCredit(Long creditId, double creditAmount, double ransomAmount, Credit.CreditTerm term, CreditStatus status);

    void deleteCredit(Long creditId);
}
