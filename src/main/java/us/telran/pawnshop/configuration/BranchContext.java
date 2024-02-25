package us.telran.pawnshop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.PawnBranchRepository;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class BranchContext {

    private final PawnBranchRepository pawnBranchRepository;

    @Value("${pawnshop.branch.id}")
    private Long branchId;

    @Value("${pawnshop.address}")
    private String branchAddress;

    @Bean
    public PawnBranch currentBranch() {
        return Optional.ofNullable(branchId)
                .flatMap(pawnBranchRepository::findById)
                .or(() -> Optional.ofNullable(branchAddress)
                        .flatMap(pawnBranchRepository::findByAddress))
                .orElseThrow(() -> new RuntimeException("Unable to find Pawn Branch by provided ID or address"));
    }
}
