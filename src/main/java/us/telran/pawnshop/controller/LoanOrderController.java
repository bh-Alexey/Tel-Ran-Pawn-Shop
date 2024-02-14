package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.entity.LoanOrder;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.service.LoanOrderService;
import us.telran.pawnshop.service.PawnBranchService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path ="api/loan-orders")
@RequiredArgsConstructor
public class LoanOrderController {

    private final LoanOrderService loanOrderService;

    @PostMapping(value = "new/expense")
    public void createLoanExpense(@RequestBody LoanOrderRequest loanOrderRequest) {
        loanOrderService.createLoanExpenseOrder(loanOrderRequest);
    }
    @GetMapping
    public List<LoanOrder> getLoanOrders() {
        return loanOrderService.getAllLoanOrders();
    }

}

