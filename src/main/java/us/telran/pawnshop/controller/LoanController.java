package us.telran.pawnshop.controller;

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
@RequestMapping(path = "api/loan")
public class LoanController {

    private final LoanService loanService;

    @PostMapping(value = "new")
    public void createNewClient(@RequestBody LoanCreationRequest loanCreationRequest) {
        loanService.newCredit(loanCreationRequest);
    }

    @GetMapping
    public List<Loan> getClients() {
        return loanService.getAllLoans();
    }

    @DeleteMapping(path = "remove/{loanId}")
    public void deleteCredit(@PathVariable("loanId") Long loanId) {
        loanService.deleteCredit(loanId);
    }

    @PutMapping(path = "update/{loanId}")
    public void updateCredit(
            @PathVariable("loanId") Long loanId,
            @RequestParam(required = false) BigDecimal creditAmount,
            @RequestParam(required = false) BigDecimal ransomAmount,
            @RequestParam(required = false) LoanTerm term,
            @RequestParam(required = false) LoanStatus status) {
        loanService.updateCredit(loanId, creditAmount, ransomAmount, term, status);
    }
}
