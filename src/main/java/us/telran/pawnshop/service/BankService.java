package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.Bank;

import java.util.List;

public interface BankService {


    void addBank(String address);

    List<Bank> getBanks();

    void updateBranch(Long bankId, String address);

    void deleteBranch(Long bankId);
}
