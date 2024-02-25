package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.dto.LoanProlongationRequest;
import us.telran.pawnshop.entity.LoanOrder;
import us.telran.pawnshop.service.LoanOrderService;

import java.util.List;

@RestController
@RequestMapping(path ="pawn-shop/loan-orders")
@RequiredArgsConstructor
public class LoanOrderController {

    private final LoanOrderService loanOrderService;

    @PostMapping(value = "income/prolongation")
    @Operation(summary = "PROLONGATION", description = "Pay loan fee for the previous period and extend the term of pledge")
    public void createLoanProlongation(@RequestBody LoanProlongationRequest loanProlongationRequest) {
        loanOrderService.createLoanProlongationOrder(loanProlongationRequest);
    }

    @PostMapping(value = "income/ransom")
    @Operation(summary = "RANSOM", description = "Ransom pledge and pay loan fee")
    public void createLoanReceipt(@RequestBody LoanOrderRequest loanOrderRequest) {
        loanOrderService.createLoanReceiptOrder(loanOrderRequest);
    }

    @GetMapping("all")
    @Operation(summary = "ALL ORDERS", description = "Show order details for all time")
    public List<LoanOrder> getLoanOrders() {
        return loanOrderService.getAllLoanOrders();
    }


}

