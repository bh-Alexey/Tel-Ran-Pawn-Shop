package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.service.CashOperationService;
import us.telran.pawnshop.dto.CashOperationRequest;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "pawn-shop/cash-operations/")
public class CashOperationController {

    private final CashOperationService cashOperationService;

    @PostMapping(value = "replenish")
    @Operation(summary = "REPLENISH CASH", description = "Replenish cash register of current branch")
    public void addCash(@RequestBody CashOperationRequest cashOperationRequest) {
        cashOperationService.replenishCash(cashOperationRequest);
    }

    @PostMapping(value = "collect")
    @Operation(summary = "COLLECT CASH", description = "Collect cash from cash register of current branch")
    public void getCash(@RequestBody CashOperationRequest cashOperationRequest) {
        cashOperationService.collectCash(cashOperationRequest);
    }

    @GetMapping(value = "all")
    @Operation(summary = "CASH OPERATIONS", description = "Show cash all operations")
    public List<CashOperation> getAllOperations() {
        return cashOperationService.getOperations();
    }

}
