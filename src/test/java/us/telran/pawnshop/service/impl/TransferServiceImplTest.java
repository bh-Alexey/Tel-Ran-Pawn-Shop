package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.IllegalTransactionStateException;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.service.CashOperationService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {
    @InjectMocks
    private TransferServiceImpl underTest;

    @Mock
    private PawnBranchRepository pawnBranchRepository;

    @Mock
    private CashOperationService cashOperationService;

    private PawnBranch branchSender;
    private PawnBranch branchRecipient;
    private TransferRequest transferRequest;

    @BeforeEach
    void setUp() {
        branchSender = new PawnBranch();
        branchSender.setBalance(new BigDecimal("100"));

        branchRecipient = new PawnBranch();
        branchRecipient.setBalance(new BigDecimal("50"));

        transferRequest = new TransferRequest();
        transferRequest.setFromBranchId(1L);
        transferRequest.setToBranchId(2L);
        transferRequest.setTransferAmount(new BigDecimal("50"));

    }

    @Test
    void canTransferCash() {
        //When
        when(pawnBranchRepository.findById(transferRequest.getFromBranchId()))
                .thenReturn(Optional.of(branchSender));
        when(pawnBranchRepository.findById(transferRequest.getToBranchId()))
                .thenReturn(Optional.of(branchRecipient));

        underTest.cashTransfer(transferRequest);

        //Then
        verify(pawnBranchRepository, times(1)).save(branchSender);
        verify(pawnBranchRepository, times(1)).save(branchRecipient);

        assertThat(branchSender.getBalance()).isEqualByComparingTo("50");
        assertThat(branchRecipient.getBalance()).isEqualByComparingTo("100");

        verify(cashOperationService).collectCashToBranch(transferRequest);
        verify(cashOperationService).replenishCashFromBranch(transferRequest);
    }

    @Test
    void willThrowIfRecipientDoesNotExist() {
        transferRequest.setToBranchId(3L);

        //When
        when(pawnBranchRepository.findById(transferRequest.getFromBranchId()))
                .thenReturn(Optional.of(branchSender));
        when(pawnBranchRepository.findById(transferRequest.getToBranchId()))
                .thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> underTest.cashTransfer(transferRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Recipient branch not found");
    }

    @Test
    void willThrowIfSenderDoesNotExist() {
        //Given
        transferRequest.setFromBranchId(4L);

        //When
        when(pawnBranchRepository.findById(transferRequest.getFromBranchId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.cashTransfer(transferRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Sender branch not found");
    }

    @Test
    void willThrowIfSenderDoesNotHaveEnoughMoney() {
        ///Given
        transferRequest.setTransferAmount(new BigDecimal("200"));

        //When
        when(pawnBranchRepository.findById(transferRequest.getFromBranchId()))
                .thenReturn(Optional.of(branchSender));
        when(pawnBranchRepository.findById(transferRequest.getToBranchId()))
                .thenReturn(Optional.of(branchRecipient));

        //Then
        assertThatThrownBy(() -> underTest.cashTransfer(transferRequest))
                .isInstanceOf(IllegalTransactionStateException.class)
                .hasMessageContaining("No enough money for the transfer");
    }
}
