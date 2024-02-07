package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.CashOperation;

import java.util.List;

public interface CashOperationService {

    List<CashOperation> getOperations();

    void collectCashToBranch(TransferRequest transferRequest);

    void replenishCashFromBranch(TransferRequest transferRequest);

}
