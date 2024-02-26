package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.service.CashOperationService;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "pawn-shop/cash-operations/")
public class CashOperationController {

    private final CashOperationService cashOperationService;

    @PostMapping(value = "replenish")
    @Operation(summary = "REPLENISH CASH", description = "Replenish cash register of current branch." +
            " Money from region director")
    public void addCash(@RequestParam BigDecimal operationAmount) {
        cashOperationService.replenishCash(operationAmount);
    }

    @PostMapping(value = "collect")
    @Operation(summary = "COLLECT CASH", description = "Collect cash from cash register of current branch")
    public void getCash(@RequestParam BigDecimal operationAmount) {
        cashOperationService.collectCash(operationAmount);
    }

    @GetMapping(value = "show")
    @Operation(summary = "CASH OPERATIONS", description = "Show cash all operations")
    public List<CashOperation> getAllOperations() {
        return cashOperationService.getOperations();
    }

    @PostMapping(value = "replenish/transfer/")
    @Operation(summary = "REPLENISH FROM BRANCH", description = "Replenish cash register of current branch." +
            " Transfer money from another branch")
    public void addCashFromBranch(@RequestBody TransferRequest transferRequest) {
        cashOperationService.replenishCashFromBranch(transferRequest);
    }

    @PostMapping(value = "collect/transfer/")
    @Operation(summary = "REPLENISH FROM BRANCH", description = "Replenish cash register of current branch." +
            " Collect money to another branch")
    public void getCashToBranch(@RequestBody TransferRequest transferRequest) {
        cashOperationService.collectCashToBranch(transferRequest);
    }

}
