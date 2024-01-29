package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.Credit;
import us.telran.pawnshop.entity.enums.CreditStatus;
import us.telran.pawnshop.repository.CreditRepository;
import us.telran.pawnshop.service.CreditService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    @Override
    public void newCredit(Credit credit) {
        creditRepository.save(credit);
    }

    @Override
    public List<Credit> getCredits() {
        return creditRepository.findAll();
    }

    @Override
    @Transactional
    public void updateCredit(Long creditId, double creditAmount, double ransomAmount, Credit.CreditTerm term, CreditStatus status) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new IllegalStateException("Credit with id " + creditId + " doesn't exist"));
        credit.setCreditAmount(creditAmount);

        credit.setRansomAmount(ransomAmount);

        credit.setTerm(term);

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
