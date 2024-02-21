package us.telran.pawnshop.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CashOperationServiceImplTest {

    @Mock
    private CashOperationRepository cashOperationRepository;

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
        when(pawnBranchRepository.findById(fromBranchId)).thenReturn(Optional.of(pawnBranch));

        //When
        underTest.collectCashToBranch(transferRequest);

        //Then
        verify(pawnBranchRepository, times(1)).findById(fromBranchId);
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));

        ArgumentCaptor<CashOperation> argumentCaptor = ArgumentCaptor.forClass(CashOperation.class);

        verify(cashOperationRepository).save(argumentCaptor.capture());
        assertEquals(OrderType.EXPENSE, argumentCaptor.getValue().getOrderType());
        assertEquals(transferAmount, argumentCaptor.getValue().getOperationAmount());
        assertEquals("Collect cash to Pawn branch " + toBranchId, argumentCaptor.getValue().getDescription());
        assertEquals(pawnBranch, argumentCaptor.getValue().getPawnBranch());
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
        when(pawnBranchRepository.findById(toBranchId)).thenReturn(Optional.of(pawnBranch));

        //When
        underTest.replenishCashFromBranch(transferRequest);

        //Then
        verify(pawnBranchRepository, times(1)).findById(toBranchId);
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));

        ArgumentCaptor<CashOperation> argumentCaptor = ArgumentCaptor.forClass(CashOperation.class);

        verify(cashOperationRepository).save(argumentCaptor.capture());

        assertEquals(OrderType.INCOME, argumentCaptor.getValue().getOrderType());
        assertEquals(transferAmount, argumentCaptor.getValue().getOperationAmount());
        assertEquals("Replenish cash from Pawn branch " + fromBranchId, argumentCaptor.getValue().getDescription());
        assertEquals(pawnBranch, argumentCaptor.getValue().getPawnBranch());
    }

    @Test
    void canReplenishCash() {
        //Given
        Long branchId = 1L;
        BigDecimal operationAmount = new BigDecimal("500.0");

        CashOperationRequest cashOperationRequest = new CashOperationRequest();
        cashOperationRequest.setBranchId(branchId);
        cashOperationRequest.setOperationAmount(operationAmount);

        PawnBranch pawnBranch = new PawnBranch();
        pawnBranch.setBalance(new BigDecimal("1000.0"));

        when(pawnBranchRepository.findById(branchId)).thenReturn(Optional.of(pawnBranch));

        //When
        underTest.replenishCash(cashOperationRequest);

        //Then
        verify(pawnBranchRepository, times(1)).findById(branchId);
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));

        ArgumentCaptor<CashOperation> argumentCaptor = ArgumentCaptor.forClass(CashOperation.class);
        verify(cashOperationRepository).save(argumentCaptor.capture());

        assertEquals(OrderType.INCOME, argumentCaptor.getValue().getOrderType());
        assertEquals(operationAmount, argumentCaptor.getValue().getOperationAmount());
        assertEquals("Replenish cash from Region Director", argumentCaptor.getValue().getDescription());
        assertEquals(pawnBranch, argumentCaptor.getValue().getPawnBranch());
        assertEquals(new BigDecimal("1500.0"), pawnBranch.getBalance());
    }

    @Test
    void collectCash() {
        //Given
        Long branchId = 1L;
        BigDecimal operationAmount = new BigDecimal("500.0");
        CashOperationRequest cashOperationRequest = new CashOperationRequest();
        cashOperationRequest.setBranchId(branchId);
        cashOperationRequest.setOperationAmount(operationAmount);

        PawnBranch pawnBranch = new PawnBranch();
        pawnBranch.setBalance(new BigDecimal("1000.0"));

        when(pawnBranchRepository.findById(branchId)).thenReturn(Optional.of(pawnBranch));

        //When
        underTest.collectCash(cashOperationRequest);

        //Then
        verify(pawnBranchRepository, times(1)).findById(branchId);
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));

        ArgumentCaptor<CashOperation> argumentCaptor = ArgumentCaptor.forClass(CashOperation.class);
        verify(cashOperationRepository).save(argumentCaptor.capture());

        assertEquals(OrderType.EXPENSE, argumentCaptor.getValue().getOrderType());
        assertEquals(operationAmount, argumentCaptor.getValue().getOperationAmount());
        assertEquals("Replenish cash for Region Director", argumentCaptor.getValue().getDescription());
        assertEquals(pawnBranch, argumentCaptor.getValue().getPawnBranch());
        assertEquals(new BigDecimal("500.0"), pawnBranch.getBalance());  // Subtracting 500 from existing 1000
    }
}