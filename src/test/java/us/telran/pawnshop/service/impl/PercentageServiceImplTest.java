package us.telran.pawnshop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.LoanTerm;

import us.telran.pawnshop.repository.PercentageRepository;
import us.telran.pawnshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PercentageServiceImplTest {

    @Mock
    private PercentageRepository percentageRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PercentageServiceImpl underTest;

    @Value("${pawnshop.product}")
    private String productName;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(underTest, "productName", "BORROW");
        ReflectionTestUtils.setField(underTest, "coefficientChange", new BigDecimal("0.006"));
        ReflectionTestUtils.setField(underTest, "daysInMonth", new BigDecimal("30"));
        ReflectionTestUtils.setField(underTest, "divisionScale", 8);
    }
    @Test
    void canAddPercentage() {
        //Given
        PercentageCreationRequest request = new PercentageCreationRequest();

        Product product = new Product();
        product.setInterestRate(new BigDecimal("25.1"));
        product.setProductName(productName);
        LoanTerm term = LoanTerm.TWO_WEEKS;
        request.setTerm(term);

        //When
        when(productRepository.findByProductName("BORROW")).thenReturn(Optional.of(product));

        underTest.addPercentage(request);

        //Then
        verify(percentageRepository).save(any(Percentage.class));
    }

    @Test
    void willThrowWhenPercentageIntroduced() {
        //Given
        PercentageCreationRequest request = new PercentageCreationRequest();
        LoanTerm term = LoanTerm.TWO_WEEKS;
        request.setTerm(term);
        Percentage percentage = new Percentage();

        given(percentageRepository.findByTerm(any(LoanTerm.class))).willReturn(Optional.of(percentage));

        //Then
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> {
                    underTest.addPercentage(request);
                })
                .withMessageMatching("Percentage for this term introduced");
    }

    @Test
    void getInterestGrid() {
        //When
        underTest.getInterestGrid();

        //Then
        verify(percentageRepository).findAll();
    }

    @Test
    void updatePercentage() {
    }

    @Test
    void deletePercentage() {
    }
}