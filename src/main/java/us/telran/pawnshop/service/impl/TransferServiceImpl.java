package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.service.CashOperationService;
import us.telran.pawnshop.service.TransferService;

@Service
@Transactional
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final PawnBranchRepository pawnBranchRepository;
    private final CashOperationService cashOperationService;

    @Override
    @Transactional
    public void cashTransfer(TransferRequest transferRequest) {

            PawnBranch fromBranch = pawnBranchRepository.findById(transferRequest.getFromBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch sender not found"));
            PawnBranch toBranch = pawnBranchRepository.findById(transferRequest.getToBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch receiver not found "));

            if (fromBranch.getBalance().compareTo(transferRequest.getTransferAmount()) < 0) {
                throw new RuntimeException("No enough money for the transfer ");
            }

            fromBranch.setBalance(fromBranch.getBalance().subtract(transferRequest.getTransferAmount()));
            pawnBranchRepository.save(fromBranch);

            cashOperationService.collectCashToBranch(transferRequest);

            toBranch.setBalance(toBranch.getBalance().add(transferRequest.getTransferAmount()));
            pawnBranchRepository.save(toBranch);

            cashOperationService.replenishCashFromBranch(transferRequest);
        }

}


