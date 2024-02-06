package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.entity.Bank;
import us.telran.pawnshop.repository.BankRepository;
import us.telran.pawnshop.service.BankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    
    @Override
    @Transactional
    public void addBank(String address) {
        Bank bank = new Bank();
        bank.setAddress(address);
        bank.setBalance(BigDecimal.valueOf(0));
        bankRepository.save(bank);
    }

    @Override
    public List<Bank> getBanks() {
        return bankRepository.findAll();
    }

    @Override
    @Transactional
    public void updateBranch(Long bankId, String address) {

        Optional<Bank> optionalBank = bankRepository.findById(bankId);
        if (optionalBank.isPresent()) {
            Bank bank = optionalBank.get();
            bank.setAddress(address);
            bankRepository.save(bank);
        }
        else {
            throw new NoSuchElementException("Bank not found");
        }

    }

    @Override
    @Transactional
    public void deleteBranch(Long categoryId) {
        Optional<Bank> optionalBank = bankRepository.findById(categoryId);
        if (optionalBank.isPresent()) {
            Bank bank = optionalBank.get();
            bankRepository.delete(bank);
        }
        else {
            throw new NoSuchElementException("Bank not found");
        }
        bankRepository.deleteById(categoryId);
    }
}
