package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.CashOperationRequest;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.CashOperationRepository;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.security.SecurityUtils;
import us.telran.pawnshop.service.CashOperationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashOperationServiceImpl implements CashOperationService {

    private final CashOperationRepository cashOperationRepository;
    private final PawnBranchRepository pawnBranchRepository;
    private final PawnBranch currentBranch;
    Long currentManagerId = SecurityUtils.getCurrentManagerId();

    @Override
    public List<CashOperation> getOperations() {
        return cashOperationRepository.findAll();
    }

    @Override
    @Transactional
    public void collectCashToBranch(TransferRequest transferRequest) {
        CashOperation cashOperation = new CashOperation();

        Manager manager = new Manager();
        manager.setManagerId(currentManagerId);

        cashOperation.setManager(manager);
        cashOperation.setPawnBranch(currentBranch);
        cashOperation.setOrderType(OrderType.EXPENSE);
        cashOperation.setOperationAmount(transferRequest.getTransferAmount());
        String pawnBranchRecipient = pawnBranchRepository
                .findById(transferRequest.getToBranchId())
                .map(PawnBranch::getAddress)
                .orElse("Recipient");

        cashOperation.setDescription("Collect cash to Pawn branch " + pawnBranchRecipient);

        cashOperationRepository.save(cashOperation);

    }

    @Override
    @Transactional
    public void replenishCashFromBranch(TransferRequest transferRequest) {
        CashOperation cashOperation = new CashOperation();

        Manager manager = new Manager();
        manager.setManagerId(currentManagerId);

        cashOperation.setManager(manager);
        cashOperation.setPawnBranch(currentBranch);
        cashOperation.setOrderType(OrderType.INCOME);
        cashOperation.setOperationAmount(transferRequest.getTransferAmount());
        String pawnBranchSender = pawnBranchRepository
                .findById(transferRequest.getFromBranchId())
                .map(PawnBranch::getAddress)
                .orElse("Sender");
        cashOperation.setDescription("Replenish cash from Pawn branch " + pawnBranchSender);

        cashOperationRepository.save(cashOperation);
    }

    @Override
    @Transactional
    public void replenishCash(CashOperationRequest cashOperationRequest) {
        CashOperation cashOperation = new CashOperation();

        Manager manager = new Manager();
        manager.setManagerId(currentManagerId);

        cashOperation.setManager(manager);
        cashOperation.setOrderType(OrderType.INCOME);
        cashOperation.setOperationAmount(cashOperationRequest.getOperationAmount());
        cashOperation.setDescription("Replenish cash from Region Director");
        currentBranch.setBalance(currentBranch.getBalance().add(cashOperation.getOperationAmount()));

        cashOperationRepository.save(cashOperation);
    }

    @Override
    @Transactional
    public void collectCash(CashOperationRequest cashOperationRequest) {
        CashOperation cashOperation = new CashOperation();

        Manager manager = new Manager();
        manager.setManagerId(currentManagerId);

        cashOperation.setManager(manager);
        cashOperation.setOrderType(OrderType.EXPENSE);
        cashOperation.setOperationAmount(cashOperationRequest.getOperationAmount());
        cashOperation.setDescription("Replenish cash for Region Director");

        currentBranch.setBalance(currentBranch.getBalance().subtract(cashOperation.getOperationAmount()));

        cashOperationRepository.save(cashOperation);
    }
}
