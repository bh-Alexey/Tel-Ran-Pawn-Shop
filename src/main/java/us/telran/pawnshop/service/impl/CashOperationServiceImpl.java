package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.CashOperationRepository;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.service.CashOperationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashOperationServiceImpl implements CashOperationService {

    private final CashOperationRepository cashOperationRepository;
    private final PawnBranchRepository pawnBranchRepository;
    @Override
    public List<CashOperation> getOperations() {
        return cashOperationRepository.findAll();
    }

    @Override
    @Transactional
    public void collectCashToBranch(TransferRequest transferRequest) {
        CashOperation cashOperation = new CashOperation();

        Optional<PawnBranch> pawnBranchOptional = pawnBranchRepository.findById(transferRequest.getFromBranchId());
        if (pawnBranchOptional.isPresent()) {
            PawnBranch pawnBranch = pawnBranchOptional.get();
            cashOperation.setPawnBranch(pawnBranch);
        }
        cashOperation.setOrderType(OrderType.EXPENSE);
        cashOperation.setOperationAmount(transferRequest.getTransferAmount());
        cashOperationRepository.save(cashOperation);

    }

    @Override
    @Transactional
    public void replenishCashFromBranch(TransferRequest transferRequest) {
        CashOperation cashOperation = new CashOperation();

        Optional<PawnBranch> pawnBranchOptional = pawnBranchRepository.findById(transferRequest.getToBranchId());
        if (pawnBranchOptional.isPresent()) {
            PawnBranch pawnBranch = pawnBranchOptional.get();
            cashOperation.setPawnBranch(pawnBranch);
        }
        cashOperation.setOrderType(OrderType.INCOME);
        cashOperation.setOperationAmount(transferRequest.getTransferAmount());
        cashOperationRepository.save(cashOperation);
    }

}
