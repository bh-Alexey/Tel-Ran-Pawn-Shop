package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.Credit;
import us.telran.pawnshop.entity.enums.CreditStatus;
import us.telran.pawnshop.service.CreditService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/credit")
public class CreditController {

    private final CreditService creditService;

    @PostMapping(value = "new")
    public void createNewClient(@RequestBody Credit credit) {
        creditService.newCredit(credit);
    }

    @GetMapping
    public List<Credit> getClients() {
        return creditService.getCredits();
    }

    @DeleteMapping(path = "remove/{creditId}")
    public void deleteCredit(@PathVariable("creditId") Long clientId) {
        creditService.deleteCredit(clientId);
    }

    @PutMapping(path = "update/{creditId}")
    public void updateCredit(
            @PathVariable("creditId") Long creditId,
            @RequestParam(required = false) double creditAmount,
            @RequestParam(required = false) double ransomAmount,
            @RequestParam(required = false) Credit.CreditTerm term,
            @RequestParam(required = false) CreditStatus status) {
        creditService.updateCredit(creditId, creditAmount, ransomAmount, term, status);
    }
}
