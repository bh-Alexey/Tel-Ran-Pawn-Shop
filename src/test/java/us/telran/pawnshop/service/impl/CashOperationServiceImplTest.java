package us.telran.pawnshop.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.dto.CashOperationRequest;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.CashOperationRepository;
import us.telran.pawnshop.repository.PawnBranchRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashOperationServiceImplTest {

    @Mock
    private CashOperationRepository cashOperationRepository;

    @Mock
    private PawnBranch currentBranch;

    @Mock
    private PawnBranchRepository pawnBranchRepository;

    @InjectMocks
    private CashOperationServiceImpl underTest;

    @Test
    void canGetAllOperations() {
        //When
        underTest.getOperations();

        //Then
        verify(cashOperationRepository).findAll();
    }

    @Test
    void canCollectCashToBranch() {
        //Given
        Long fromBranchId = 1L;
        Long toBranchId = 2L;
        BigDecimal transferAmount = new BigDecimal("500.0");

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromBranchId(fromBranchId);
        transferRequest.setToBranchId(toBranchId);
        transferRequest.setTransferAmount(transferAmount);

        PawnBranch pawnBranch = new PawnBranch();
        pawnBranch.setAddress("Address");

        //When
        when(pawnBranchRepository.findById(transferRequest.getToBranchId())).thenReturn(Optional.of(pawnBranch));

        underTest.collectCashToBranch(transferRequest);

        //Then
        then(cashOperationRepository).should().save(argThat(cashOperation -> {
            assertThat(cashOperation.getPawnBranch()).isEqualTo(currentBranch);
            assertThat(cashOperation.getOrderType()).isEqualTo(OrderType.EXPENSE);
            assertThat(cashOperation.getOperationAmount()).isEqualTo(transferRequest.getTransferAmount());
            assertThat(cashOperation.getDescription()).isEqualTo("Collect cash to Pawn branch " + pawnBranch.getAddress());
            return true;
        }));
    }

    @Test
    void canReplenishCashFromBranch() {
        //Given
        Long fromBranchId = 1L;
        Long toBranchId = 2L;
        BigDecimal transferAmount = new BigDecimal("500.0");

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromBranchId(fromBranchId);
        transferRequest.setToBranchId(toBranchId);
        transferRequest.setTransferAmount(transferAmount);

        PawnBranch pawnBranch = new PawnBranch();
        pawnBranch.setAddress("Address");

        //When
        when(pawnBranchRepository.findById(transferRequest.getFromBranchId())).thenReturn(Optional.of(pawnBranch));

        underTest.replenishCashFromBranch(transferRequest);

        //Then
        then(cashOperationRepository).should().save(argThat(cashOperation -> {
            assertThat(cashOperation.getPawnBranch()).isEqualTo(currentBranch);
            assertThat(cashOperation.getOrderType()).isEqualTo(OrderType.INCOME);
            assertThat(cashOperation.getOperationAmount()).isEqualTo(transferRequest.getTransferAmount());
            assertThat(cashOperation.getDescription()).isEqualTo("Replenish cash from Pawn branch " + pawnBranch.getAddress());
            return true;
        }));
    }

    @Test
    void canReplenishCash() {
        //Given
        BigDecimal initialBalance = new BigDecimal("1500.0");
        BigDecimal operationAmount = new BigDecimal("500.0");

        CashOperationRequest cashOperationRequest = new CashOperationRequest();
        cashOperationRequest.setOperationAmount(operationAmount);


        //When
        when(currentBranch.getBalance()).thenReturn(initialBalance)
                .thenReturn(initialBalance, initialBalance.add(operationAmount));

        underTest.replenishCash(cashOperationRequest);

        //Then
        verify(currentBranch, times(1)).getBalance();
        verify(currentBranch).setBalance(initialBalance.add(operationAmount));
        verify(cashOperationRepository).save(any(CashOperation.class));
    }

    @Test
    void canCollectCash() {
        //Given
        BigDecimal initialBalance = new BigDecimal("1500.0");
        BigDecimal operationAmount = new BigDecimal("500.0");

        CashOperationRequest cashOperationRequest = new CashOperationRequest();
        cashOperationRequest.setOperationAmount(operationAmount);


        //When
        when(currentBranch.getBalance()).thenReturn(initialBalance)
                .thenReturn(initialBalance, initialBalance.subtract(operationAmount));

        underTest.replenishCash(cashOperationRequest);

        //Then
        verify(currentBranch, times(1)).getBalance();
        verify(currentBranch).setBalance(initialBalance.add(operationAmount));
        verify(cashOperationRepository).save(any(CashOperation.class));
    }
}