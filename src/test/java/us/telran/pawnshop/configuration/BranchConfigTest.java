package us.telran.pawnshop.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import us.telran.pawnshop.CurrentBranchInfo;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.PawnBranchRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BranchConfigTest {

    @Mock
    private PawnBranchRepository pawnBranchRepository;

    @Mock
    private CurrentBranchInfo currentBranchInfo;

    @InjectMocks
    private BranchConfig branchConfig;

    @Test
    public void whenBranchFoundById_thenSetCurrentBranchInfo() {
        //Given
        ReflectionTestUtils.setField(branchConfig, "branchId", 1L);
        ReflectionTestUtils.setField(branchConfig, "address", "Test Address");

        PawnBranch currentBranch = new PawnBranch();
        currentBranch.setBranchId(1L);
        currentBranch.setAddress("Test Address");
        currentBranch.setBalance(BigDecimal.valueOf(10000.0));

        //When
        when(pawnBranchRepository.findById(1L)).thenReturn(Optional.of(currentBranch));
        when(pawnBranchRepository.findByAddress("Test Address")).thenReturn(Optional.of(currentBranch));

        branchConfig.initCurrentBranch();

        //Then
        verify(currentBranchInfo).setBranchId(1L);
        verify(currentBranchInfo).setAddress("Test Address");
        verify(currentBranchInfo).setBalance(currentBranch.getBalance());
    }
}