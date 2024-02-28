package us.telran.pawnshop.configuration;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import us.telran.pawnshop.CurrentBranchInfo;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.PawnBranchRepository;

@Configuration
@RequiredArgsConstructor
public class BranchConfig {

    @Value("${pawnshop.branch.id}")
    Long branchId;

    @Value("${pawnshop.address}")
    String address;

    private final PawnBranchRepository pawnBranchRepository;
    private final CurrentBranchInfo currentBranchInfo;

    @EventListener(ApplicationReadyEvent.class)
    public void initCurrentBranch() {
        PawnBranch currentBranch = pawnBranchRepository.findById(branchId)
                .orElse(pawnBranchRepository.findByAddress(address)
                .orElseThrow(() -> new EntityNotFoundException("Current Branch not initialized")));
        currentBranchInfo.setBranchId(currentBranch.getBranchId());
        currentBranchInfo.setAddress(currentBranch.getAddress());
        currentBranchInfo.setBalance(currentBranch.getBalance());
    }
}
