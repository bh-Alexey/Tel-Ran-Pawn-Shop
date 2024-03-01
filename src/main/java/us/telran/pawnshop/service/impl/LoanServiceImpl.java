package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.LoanCreationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.entity.enums.PledgeStatus;
import us.telran.pawnshop.repository.LoanRepository;
import us.telran.pawnshop.repository.PercentageRepository;
import us.telran.pawnshop.repository.PledgeRepository;
import us.telran.pawnshop.service.LoanOrderService;
import us.telran.pawnshop.service.LoanService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final PledgeRepository pledgeRepository;
    private final PercentageRepository percentageRepository;
    private final LoanOrderService loanOrderService;

    @Value("${pawnshop.hundred.percents}")
    private BigDecimal hundred;

    @Value("${pawnshop.division.scale}")
    private int divisionScale;

    @Override
    @Transactional
    public void newLoan(LoanCreationRequest loanCreationRequest) {

        Pledge pledge = pledgeRepository.findById(loanCreationRequest.getPledgeId())
                .orElseThrow(() -> new EntityNotFoundException("Pledge not found"));

        Loan loan = new Loan();
        loan.setPledge(pledge);

        if (loanCreationRequest.getLoanAmount().compareTo(pledge.getEstimatedPrice()) > 0) {
            throw new IllegalStateException("Amount can't be higher than " + pledge.getEstimatedPrice());
        } else {
            loan.setLoanAmount(loanCreationRequest.getLoanAmount());
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

        pledge.setStatus(PledgeStatus.PLEDGED);
        loan.setStatus(LoanStatus.IN_USE);

        loanRepository.save(loan);

        loanOrderService.createLoanExpenseOrder(loan);

    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan with id " + loanId + " doesn't exist"));
    }

    @Override
    @Transactional
    public void updateLoan(Long loanId,
                             BigDecimal loanAmount,
                             BigDecimal ransomAmount,
                             LoanTerm term,
                             LoanStatus status
    ) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Credit with id " + loanId + " doesn't exist"));

        loan.setLoanAmount(loanAmount);
        loan.setRansomAmount(ransomAmount);
        loan.setTerm(term);
        loan.setStatus(status);
    }

    @Override
    @Transactional
    public void deleteLoan(Long loanId) {
        loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan with id " + loanId + " doesn't exist"));
        loanRepository.deleteById(loanId);
    }

    @Override
    @Transactional
    public void updateLoanStatus(Long loanId) {
        Loan loan = getLoanById(loanId);
        LocalDateTime expiredDate = loan.getExpiredAt();
        if (LocalDateTime.now().isAfter(expiredDate)) {
            loan.setStatus(LoanStatus.EXPIRED);
        }

        loanRepository.save(loan);
    }
}
