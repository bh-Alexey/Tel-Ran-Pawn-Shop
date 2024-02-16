package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.LoanCreationRequest;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.service.LoanOrderService;
import us.telran.pawnshop.service.LoanService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/loan")
public class LoanController {

    private final LoanService loanService;

    @PostMapping(value = "new")
    public void createNewLoan(@RequestBody LoanCreationRequest loanCreationRequest) {
        loanService.newLoan(loanCreationRequest);
    }

    @GetMapping
    public List<Loan> getLoans() {
        return loanService.getAllLoans();
    }

    @DeleteMapping(path = "remove/{loanId}")
    public void deleteLoan(@PathVariable("loanId") Long loanId) {
        loanService.deleteLoan(loanId);
    }

    @PutMapping(path = "update/{loanId}")
    public void updateLoan(
            @PathVariable("loanId") Long loanId,
            @RequestParam(required = false) BigDecimal creditAmount,
            @RequestParam(required = false) BigDecimal ransomAmount,
            @RequestParam(required = false) LoanTerm term,
            @RequestParam(required = false) LoanStatus status) {
        loanService.updateLoan(loanId, creditAmount, ransomAmount, term, status);
    }
}
