package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.CurrentBranchInfo;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.CashOperationRepository;
import us.telran.pawnshop.repository.ManagerRepository;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.security.SecurityUtils;
import us.telran.pawnshop.service.CashOperationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashOperationServiceImpl implements CashOperationService {

    private final CashOperationRepository cashOperationRepository;
    private final PawnBranchRepository pawnBranchRepository;
    private final CurrentBranchInfo currentBranchInfo;
    private final ManagerRepository managerRepository;

    @Override
    @Transactional
    public void replenishCash(BigDecimal operationAmount) {

        Long currentBranchId = currentBranchInfo.getBranchId();
        PawnBranch currentBranch = getCurrentBranch(currentBranchId);

        CashOperation cashOperation = new CashOperation();

        cashOperation.setPawnBranch(currentBranch);
        cashOperation.setManager(getCurrentManager());
        cashOperation.setOrderType(OrderType.INCOME);
        cashOperation.setOperationAmount(operationAmount);
        cashOperation.setDescription("Replenish cash from Region Director");
        currentBranch.setBalance(currentBranch.getBalance().add(cashOperation.getOperationAmount()));

        pawnBranchRepository.save(currentBranch);
        cashOperationRepository.save(cashOperation);
    }

    @Override
    @Transactional
    public void collectCash(BigDecimal operationAmount) {

        Long currentBranchId = currentBranchInfo.getBranchId();
        PawnBranch currentBranch = getCurrentBranch(currentBranchId);

        CashOperation cashOperation = new CashOperation();

        cashOperation.setPawnBranch(currentBranch);
        cashOperation.setManager(getCurrentManager());
        cashOperation.setOrderType(OrderType.EXPENSE);
        cashOperation.setOperationAmount(operationAmount);
        cashOperation.setDescription("Collect cash from Region Director");

        currentBranch.setBalance(currentBranch.getBalance().subtract(cashOperation.getOperationAmount()));

        pawnBranchRepository.save(currentBranch);
        cashOperationRepository.save(cashOperation);
    }

    private Manager getCurrentManager() {
        Long currentManagerId = SecurityUtils.getCurrentManagerId();
        Optional<Manager> managerOptional = managerRepository.findById(currentManagerId);
        if (managerOptional.isPresent()) {
            return managerOptional.get();
        }
        throw new EntityNotFoundException("Manager not found");
    }

    private PawnBranch getCurrentBranch(Long branchId) {
        return pawnBranchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch with id: " + branchId
                        + " not found"));
    }

    @Override
    @Transactional
    public void collectCashToBranch(TransferRequest transferRequest) {

        Long currentBranchId = currentBranchInfo.getBranchId();
        PawnBranch currentBranch = getCurrentBranch(currentBranchId);

        CashOperation cashOperation = new CashOperation();

        cashOperation.setPawnBranch(currentBranch);
        cashOperation.setManager(getCurrentManager());
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

        Long currentBranchId = currentBranchInfo.getBranchId();
        PawnBranch currentBranch = getCurrentBranch(currentBranchId);

        CashOperation cashOperation = new CashOperation();

        cashOperation.setPawnBranch(currentBranch);
        cashOperation.setManager(getCurrentManager());
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
    public List<CashOperation> getOperations() {
        return cashOperationRepository.findAll();
    }

}