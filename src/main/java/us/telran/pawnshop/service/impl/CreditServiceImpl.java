package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.CreditCreationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.CreditStatus;
import us.telran.pawnshop.entity.enums.CreditTerm;
import us.telran.pawnshop.repository.CreditRepository;
import us.telran.pawnshop.repository.PercentageRepository;
import us.telran.pawnshop.repository.PledgeRepository;
import us.telran.pawnshop.repository.ProductRepository;
import us.telran.pawnshop.service.CreditService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final PledgeRepository pledgeRepository;
    private final PercentageRepository percentageRepository;

    @Override
    @Transactional
    public void newCredit(CreditCreationRequest creditCreationRequest) {

        Pledge pledge = pledgeRepository.findById(creditCreationRequest.getPledgeId())
                .orElseThrow(() -> new IllegalStateException("Pledge not found"));


        Percentage percentage = percentageRepository.findByTerm(creditCreationRequest.getTerm())
                .orElseThrow();

        Credit credit = new Credit();
        credit.setPledge(pledge);
        credit.setCreditAmount(creditCreationRequest.getCreditAmount());
        credit.setTerm(creditCreationRequest.getTerm());
        credit.setRansomAmount(credit.getCreditAmount().multiply(percentage.getInterest())
                .add(credit.getCreditAmount()));
        credit.setStatus(CreditStatus.IN_USE);

        creditRepository.save(credit);
    }

    @Override
    public List<Credit> getCredits() {
        return creditRepository.findAll();
    }

    @Override
    @Transactional
    public void updateCredit(Long creditId, BigDecimal creditAmount, BigDecimal ransomAmount, CreditTerm term, CreditStatus status) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new IllegalStateException("Credit with id " + creditId + " doesn't exist"));

        credit.setCreditAmount(creditAmount);
        credit.setRansomAmount(ransomAmount);
        credit.setTerm(term);
        credit.setStatus(status);
    }

    @Override
    @Transactional
    public void updateCreditStatus(Long creditId, CreditStatus status) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new IllegalStateException("Credit with id " + creditId + " doesn't exist"));
        credit.setStatus(status);
    }

    @Override
    public void deleteCredit(Long creditId) {
        boolean exists = creditRepository.existsById(creditId);
        if (!exists) {
            throw new IllegalStateException("Pledge with id " + creditId + " doesn't exist");
        }
        creditRepository.deleteById(creditId);
    }
}
