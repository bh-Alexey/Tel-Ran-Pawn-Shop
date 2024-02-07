package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.service.PawnBranchService;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PawnBranchServiceImpl implements PawnBranchService {

    private final PawnBranchRepository pawnBranchRepository;
    
    @Override
    @Transactional
    public void addBranch(String address) {
        PawnBranch bank = new PawnBranch();
        bank.setAddress(address);
        bank.setBalance(BigDecimal.valueOf(0));
        pawnBranchRepository.save(bank);
    }

    @Override
    @Transactional
    public void updateBranch(Long bankId, String address) {

        Optional<PawnBranch> optionalBank = pawnBranchRepository.findById(bankId);
        if (optionalBank.isPresent()) {
            PawnBranch bank = optionalBank.get();
            bank.setAddress(address);
            pawnBranchRepository.save(bank);
        }
        else {
            throw new NoSuchElementException("Bank not found");
        }

    }

    @Override
    @Transactional
    public void deleteBranch(Long categoryId) {
        Optional<PawnBranch> optionalBank = pawnBranchRepository.findById(categoryId);
        if (optionalBank.isPresent()) {
            PawnBranch bank = optionalBank.get();
            pawnBranchRepository.delete(bank);
        }
        else {
            throw new NoSuchElementException("Bank not found");
        }
        pawnBranchRepository.deleteById(categoryId);
    }

    @Override
    public List<PawnBranch> getBranches() {
        return pawnBranchRepository.findAll();
    }
}
