package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.Bank;
import us.telran.pawnshop.service.BankService;

import java.util.List;

@RestController
@RequestMapping(path = "api/bank/")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @PostMapping(value = "new")
    public void addBranch(@RequestParam String address) {
        bankService.addBank(address);
    }
    @GetMapping
    public List<Bank> getAllBanks() {
        return bankService.getBanks();
    }
    @PutMapping(path = "update/{bankId}")
    public void updateBranch(
            @PathVariable("bankId") Long bankId,
            @RequestParam(required = false) String address) {
        bankService.updateBranch(bankId, address);
    }

    @DeleteMapping(path = "remove/{bankId}")
    public void deleteBranch(@PathVariable("bankId") Long bankId) {
        bankService.deleteBranch(bankId);
    }
}
