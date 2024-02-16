package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.service.CashOperationService;
import us.telran.pawnshop.dto.CashOperationRequest;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/cash-operation/")
public class CashOperationController {

    private final CashOperationService cashOperationService;

    @PostMapping(value = "replenish")
    public void addCash(@RequestBody CashOperationRequest cashOperationRequest) {
        cashOperationService.replenishCash(cashOperationRequest);
    }

    @PostMapping(value = "collect")
    public void getCash(@RequestBody CashOperationRequest cashOperationRequest) {
        cashOperationService.collectCash(cashOperationRequest);
    }

    @GetMapping(value = "all")
    public List<CashOperation> getAllOperations() {
        return cashOperationService.getOperations();
    }

}
