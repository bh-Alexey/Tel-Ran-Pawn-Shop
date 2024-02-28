package us.telran.pawnshop.service.impl;

import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.CurrentBranchInfo;
import us.telran.pawnshop.dto.TransferRequest;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.repository.CashOperationRepository;
import us.telran.pawnshop.repository.ManagerRepository;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.security.SecurityUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static us.telran.pawnshop.entity.enums.OrderType.*;


@ExtendWith(MockitoExtension.class)
class CashOperationServiceImplTest {

    @Mock
    private CashOperationRepository cashOperationRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private CurrentBranchInfo currentBranchInfo;

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
        Long currentManagerId = SecurityUtils.getCurrentManagerId();

        Long fromBranchId = 1L;
        Long toBranchId = 2L;

        BigDecimal initialBalance = new BigDecimal("10000.0");
        BigDecimal transferAmount = new BigDecimal("5000.0");

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromBranchId(fromBranchId);
        transferRequest.setToBranchId(toBranchId);
        transferRequest.setTransferAmount(transferAmount);

        PawnBranch pawnBranch = new PawnBranch();
        pawnBranch.setAddress("Address");
        pawnBranch.setBranchId(toBranchId);

        PawnBranch currentBranch = new PawnBranch();
        currentBranch.setBranchId(fromBranchId);
        currentBranch.setBalance(initialBalance);

        Manager currentManager = new Manager();
        currentManager.setManagerId(currentManagerId);

        //When
        when(currentBranchInfo.getBranchId()).thenReturn(1L);
        when(pawnBranchRepository.findById(1L)).thenReturn(Optional.of(currentBranch));
        when(managerRepository.findById(eq(currentManagerId))).thenReturn(Optional.of(currentManager));
        when(pawnBranchRepository.findById(2L)).thenReturn(Optional.of(pawnBranch));

        underTest.collectCashToBranch(transferRequest);

        BigDecimal expectedBalance = currentBranch.getBalance().subtract(transferAmount);

        //Then
        verify(cashOperationRepository).save(argThat(cashOperation ->
                cashOperation.getManager().equals(currentManager) &&
                        cashOperation.getOrderType() == EXPENSE &&
                        cashOperation.getOperationAmount().compareTo(transferRequest.getTransferAmount()) == 0
        ));
        assertThat(expectedBalance).isEqualByComparingTo("5000");
    }

    @Test
    void canReplenishCashFromBranch() {
        //Given
        Long currentManagerId = SecurityUtils.getCurrentManagerId();

        Long toBranchId = 1L;
        Long fromBranchId = 2L;
        BigDecimal initialBalance = new BigDecimal("10000.0");
        BigDecimal transferAmount = new BigDecimal("5000.0");

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromBranchId(fromBranchId);
        transferRequest.setToBranchId(toBranchId);
        transferRequest.setTransferAmount(transferAmount);

        PawnBranch pawnBranch = new PawnBranch();
        pawnBranch.setAddress("Address");
        pawnBranch.setBranchId(fromBranchId);

        PawnBranch currentBranch = new PawnBranch();
        currentBranch.setBranchId(toBranchId);
        currentBranch.setBalance(initialBalance);

        Manager currentManager = new Manager();
        currentManager.setManagerId(currentManagerId);

        //When
        when(currentBranchInfo.getBranchId()).thenReturn(1L);
        when(pawnBranchRepository.findById(1L)).thenReturn(Optional.of(currentBranch));
        when(managerRepository.findById(eq(currentManagerId))).thenReturn(Optional.of(currentManager));
        when(pawnBranchRepository.findById(2L)).thenReturn(Optional.of(pawnBranch));

        underTest.replenishCashFromBranch(transferRequest);

        BigDecimal expectedBalance = currentBranch.getBalance().add(transferAmount);

        //Then
        verify(cashOperationRepository).save(argThat(cashOperation ->
                cashOperation.getManager().equals(currentManager) &&
                        cashOperation.getOrderType() == INCOME &&
                        cashOperation.getOperationAmount().compareTo(transferRequest.getTransferAmount()) == 0
        ));
        assertThat(expectedBalance).isEqualByComparingTo("15000");
    }

    @Test
    void canReplenishCash() {
        //Given
        Long currentManagerId = SecurityUtils.getCurrentManagerId();

        BigDecimal initialBalance = new BigDecimal("0");
        BigDecimal operationAmount = new BigDecimal("500.0");

        PawnBranch currentBranch = new PawnBranch();
        currentBranch.setBalance(initialBalance);

        Manager currentManager = new Manager();
        currentManager.setManagerId(currentManagerId);

        //When
        when(currentBranchInfo.getBranchId()).thenReturn(1L);
        when(pawnBranchRepository.findById(1L)).thenReturn(Optional.of(currentBranch));
        when(managerRepository.findById(eq(currentManagerId))).thenReturn(Optional.of(currentManager));

        underTest.replenishCash(operationAmount);

        //Then
        assertThat(currentBranch.getBalance()).isEqualByComparingTo(operationAmount);
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));
        verify(pawnBranchRepository, times(1)).save(currentBranch);

    }

    @Test
    void canCollectCash() {
        //Given
        Long currentManagerId = SecurityUtils.getCurrentManagerId();

        BigDecimal initialBalance = new BigDecimal("1500.0");
        BigDecimal operationAmount = new BigDecimal("500.0");

        PawnBranch currentBranch = new PawnBranch();
        currentBranch.setBalance(initialBalance);

        Manager currentManager = new Manager();
        currentManager.setManagerId(currentManagerId);

        //When
        when(currentBranchInfo.getBranchId()).thenReturn(1L);
        when(pawnBranchRepository.findById(1L)).thenReturn(Optional.of(currentBranch));
        when(managerRepository.findById(eq(currentManagerId))).thenReturn(Optional.of(currentManager));


        //Then
        underTest.collectCash(operationAmount);

        assertThat(currentBranch.getBalance()).isEqualByComparingTo(initialBalance.subtract(operationAmount));
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));
        verify(pawnBranchRepository, times(1)).save(currentBranch);
    }
}