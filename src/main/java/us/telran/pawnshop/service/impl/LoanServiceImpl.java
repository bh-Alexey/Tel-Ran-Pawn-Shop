package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.LoanCreationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.repository.LoanRepository;
import us.telran.pawnshop.repository.PercentageRepository;
import us.telran.pawnshop.repository.PledgeRepository;
import us.telran.pawnshop.service.LoanService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final PledgeRepository pledgeRepository;
    private final PercentageRepository percentageRepository;

    @Value("${pawnshop.hundred.percents}")
    private static BigDecimal hundred;

    @Value("${pawnshop.division.scale}")
    private static int divisionScale;

    @Override
    @Transactional
    public void newCredit(LoanCreationRequest loanCreationRequest) {

        Pledge pledge = pledgeRepository.findById(loanCreationRequest.getPledgeId())
                .orElseThrow(() -> new IllegalStateException("Pledge not found"));

        Loan loan = new Loan();
        loan.setPledge(pledge);

        if (loanCreationRequest.getCreditAmount().compareTo(pledge.getEstimatedPrice()) > 0) {
            throw new IllegalStateException("Amount can't higher than " + pledge.getEstimatedPrice());
        } else {
            loan.setLoanAmount(loanCreationRequest.getCreditAmount());
        }

        loan.setTerm(loanCreationRequest.getTerm());

        Optional<Percentage> percentageOptional = percentageRepository
                .findByTerm(loanCreationRequest.getTerm());
        if (percentageOptional.isPresent()) {
            Percentage percentage = percentageOptional.get();
            loan.setRansomAmount(loan.getLoanAmount().multiply(BigDecimal.ONE
                    .add((percentage.getInterest().divide(hundred, divisionScale, RoundingMode.HALF_UP))
                            .multiply(BigDecimal.valueOf(loan.getTerm().getDays())))));
        }

        loan.setStatus(LoanStatus.IN_USE);

        loanRepository.save(loan);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isPresent()) {
            return loanOptional.get();
        }
        else {
            throw new IllegalStateException("Loan with id " + loanId + " doesn't exist");
        }
    }



    @Override
    @Transactional
    public void updateCredit(Long loanId,
                             BigDecimal loanAmount,
                             BigDecimal ransomAmount,
                             LoanTerm term,
                             LoanStatus status
    ) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalStateException("Credit with id " + loanId + " doesn't exist"));

        loan.setLoanAmount(loanAmount);
        loan.setRansomAmount(ransomAmount);
        loan.setTerm(term);
        loan.setStatus(status);
    }

    @Override
    @Transactional
    public void updateCreditStatus(Long loanId, LoanStatus status) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalStateException("Credit with id " + loanId + " doesn't exist"));
        loan.setStatus(status);
    }

    @Override
    public void deleteCredit(Long loanId) {
        boolean exists = loanRepository.existsById(loanId);
        if (!exists) {
            throw new IllegalStateException("Pledge with id " + loanId + " doesn't exist");
        }
        loanRepository.deleteById(loanId);
    }
}
