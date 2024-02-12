package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.telran.pawnshop.service.CashOperationService;
import us.telran.pawnshop.dto.CashOperationRequest;


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

}
