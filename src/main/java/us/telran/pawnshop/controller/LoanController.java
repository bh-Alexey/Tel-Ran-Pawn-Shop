package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.LoanCreationRequest;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.service.LoanService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "pawn-shop/loans")
public class LoanController {

    private final LoanService loanService;

    @PostMapping(value = "new")
    @Operation(summary = "NEW LOAN", description = "Create loan for specific pledge and make an expense operation")
    public void createNewLoan(@RequestBody LoanCreationRequest loanCreationRequest) {
        loanService.newLoan(loanCreationRequest);
    }

    @GetMapping(value = "show")
    @Operation(summary = "LOAN BASE", description = "Show loans for all time ")
    public List<Loan> getLoans() {
        return loanService.getAllLoans();
    }

    @PutMapping(path = "update/{loanId}")
    @Operation(summary = "EDIT LOAN", description = "Modify loan details with specified id")
    public void updateLoan(
            @PathVariable("loanId") Long loanId,
            @RequestParam(required = false) BigDecimal creditAmount,
            @RequestParam(required = false) BigDecimal ransomAmount,
            @RequestParam(required = false) LoanTerm term,
            @RequestParam(required = false) LoanStatus status) {
        loanService.updateLoan(loanId, creditAmount, ransomAmount, term, status);
    }

    @DeleteMapping(path = "remove/{loanId}")
    @Operation(summary = "DELETE LOAN", description = "Delete loan with specified id from the DB")
    public void deleteLoan(@PathVariable("loanId") Long loanId) {
        loanService.deleteLoan(loanId);
    }
}
