package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    public final static BigDecimal HUNDRED_PERCENTS = new BigDecimal("100");
    private final static int DIVISION_SCALE = 8;

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
                    .add((percentage.getInterest().divide(HUNDRED_PERCENTS, DIVISION_SCALE, RoundingMode.HALF_UP))
                            .multiply(BigDecimal.valueOf(loan.getTerm().getDays())))));
        }

        loan.setStatus(LoanStatus.IN_USE);

        loanRepository.save(loan);
    }

    @Override
    public List<Loan> getCredits() {
        return loanRepository.findAll();
    }

    @Override
    @Transactional
    public void updateCredit(Long creditId,
                             BigDecimal loanAmount,
                             BigDecimal ransomAmount,
                             LoanTerm term,
                             LoanStatus status
    ) {
        Loan loan = loanRepository.findById(creditId)
                .orElseThrow(() -> new IllegalStateException("Credit with id " + creditId + " doesn't exist"));

        loan.setLoanAmount(loanAmount);
        loan.setRansomAmount(ransomAmount);
        loan.setTerm(term);
        loan.setStatus(status);
    }

    @Override
    @Transactional
    public void updateCreditStatus(Long creditId, LoanStatus status) {
        Loan loan = loanRepository.findById(creditId)
                .orElseThrow(() -> new IllegalStateException("Credit with id " + creditId + " doesn't exist"));
        loan.setStatus(status);
    }

    @Override
    public void deleteCredit(Long creditId) {
        boolean exists = loanRepository.existsById(creditId);
        if (!exists) {
            throw new IllegalStateException("Pledge with id " + creditId + " doesn't exist");
        }
        loanRepository.deleteById(creditId);
    }
}
