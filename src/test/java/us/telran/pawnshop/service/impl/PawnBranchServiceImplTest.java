package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.PawnBranchRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PawnBranchServiceImplTest {

    @Mock
    private PawnBranchRepository pawnBranchRepository;
    private PawnBranchServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new PawnBranchServiceImpl(pawnBranchRepository);
    }

    @Test
    void canAddBranch() {
        //Given
        String address = "6010 Main St Flushing, NY 11355";
        PawnBranch branch = new PawnBranch();
        branch.setAddress(address);
        branch.setBalance(BigDecimal.valueOf(0));

        //When
        underTest.addBranch(address);

        //Then
        ArgumentCaptor<PawnBranch> branchArgumentCaptor = ArgumentCaptor.forClass(PawnBranch.class);
        verify(pawnBranchRepository).save(branchArgumentCaptor.capture());

        PawnBranch capturedBranch = branchArgumentCaptor.getValue();

        assertThat(capturedBranch).isEqualTo(branch);

    }

    @Test
    void canUpdateBranch() {
        //Given
        PawnBranch branch = new PawnBranch();
        //When
        when(pawnBranchRepository.findById(3L)).thenReturn(Optional.of(branch));

        underTest.updateBranch(3L, anyString());

        //Then
        verify(pawnBranchRepository).save(branch);
    }

    @Test
    public void willThrowWhenBranchForUpdateNotFound() {
        //When
        //Then
        when(pawnBranchRepository.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.updateBranch(3L, anyString()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageMatching("Pawn shop not found");

        verify(pawnBranchRepository, never()).save(any());
    }

    @Test
    void canDeleteBranch() {
        //Given
        PawnBranch branch = new PawnBranch();
        when(pawnBranchRepository.findById(3L)).thenReturn(Optional.of(branch));

        //When
        underTest.deleteBranch(3L);

        //Then
        verify(pawnBranchRepository).delete(branch);
        verify(pawnBranchRepository).deleteById(3L);
    }

    @Test
    public void willThrowWhenBranchForDeleteNotFound() {
        when(pawnBranchRepository.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.deleteBranch(3L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageMatching("Pawn shop not found");

        verify(pawnBranchRepository, never()).delete(any());
        verify(pawnBranchRepository, never()).deleteById(any());
    }

    @Test
    void canGetAllBranches() {
        //When
        underTest.getBranches();
        //Then
        verify(pawnBranchRepository).findAll();
    }
}