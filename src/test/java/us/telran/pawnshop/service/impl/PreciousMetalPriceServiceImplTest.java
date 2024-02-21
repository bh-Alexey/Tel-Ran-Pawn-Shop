package us.telran.pawnshop.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.dto.PreciousMetalPriceCreationRequest;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.repository.PledgeCategoryRepository;
import us.telran.pawnshop.repository.PreciousMetalPriceRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PreciousMetalPriceServiceImplTest {

    @Mock
    private PreciousMetalPriceRepository preciousMetalPriceRepository;

    @Mock
    private PledgeCategoryRepository pledgeCategoryRepository;

    @InjectMocks
    private PreciousMetalPriceServiceImpl underTest;

    @Test
    void canAddNewPrice() {
        //Given
        PreciousMetalPriceCreationRequest request = new PreciousMetalPriceCreationRequest();
        request.setCategoryId(1L);
        request.setPurity(MetalPurity.GOLD_585);
        request.setMetalPrice(BigDecimal.valueOf(25.0));

        //When
        when(preciousMetalPriceRepository.findByPurity(request.getPurity()))
                .thenReturn(Optional.empty());

        underTest.addNewPrice(request);

        //Then
        verify(preciousMetalPriceRepository, times(1)).findByPurity(request.getPurity());
        verify(preciousMetalPriceRepository, times(1)).save(any(PreciousMetalPrice.class));
    }

    @Test
    public void willThrowIfPriceExists() {
        //Given
        PreciousMetalPriceCreationRequest request = new PreciousMetalPriceCreationRequest();
        request.setCategoryId(1L);
        request.setPurity(MetalPurity.GOLD_999);
        request.setMetalPrice(BigDecimal.valueOf(250.0));

        PreciousMetalPrice existingPrice = new PreciousMetalPrice();
        existingPrice.setPurity(request.getPurity());

        //When
        //Then
        when(preciousMetalPriceRepository.findByPurity(request.getPurity()))
                .thenReturn(Optional.of(existingPrice));

        Throwable thrown = catchThrowable(() -> underTest.addNewPrice(request));

        assertThat(thrown)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Price for this purity " + request.getPurity() + " already presented");

        verify(preciousMetalPriceRepository, times(1)).findByPurity(request.getPurity());
        verify(preciousMetalPriceRepository, times(0)).save(any(PreciousMetalPrice.class));
    }

    @Test
    void canGetAllMetalPrices() {
        //When
        underTest.getMetalPrice();

        //Then
        verify(preciousMetalPriceRepository, times(1)).findAll();
    }

    @Test
    void canUpdateMetalPrice() {
        //Given
        Long priceId = 1L;
        BigDecimal newPrice = new BigDecimal("30.0");

        PreciousMetalPrice existingPrice = new PreciousMetalPrice();
        existingPrice.setPurity(MetalPurity.GOLD_585);
        existingPrice.setMetalPrice(new BigDecimal("25.0"));

        //When
        when(preciousMetalPriceRepository.findById(priceId)).thenReturn(Optional.of(existingPrice));

        underTest.updateMetalPrice(priceId, newPrice);

        //Then
        assertThat(existingPrice.getMetalPrice()).isEqualTo(newPrice);
    }

    @Test
    void willThrowIfMetalPriceForUpdateDoesNotExist() {
        //Given
        Long priceId = 1L;
        BigDecimal newPrice = new BigDecimal("30.0");

        //When
        when(preciousMetalPriceRepository.findById(priceId)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> underTest.updateMetalPrice(priceId, newPrice));

        //Then
        assertThat(thrown)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Manager with id " + priceId + " doesn't exist");
    }

    @Test
    void canDeleteMetalPrice() {
        //Given
        Long priceId = 1L;

        //When
        when(preciousMetalPriceRepository.existsById(priceId)).thenReturn(true);

        underTest.deleteMetalPrice(priceId);

        //Then
        verify(preciousMetalPriceRepository, times(1)).existsById(priceId);
        verify(preciousMetalPriceRepository, times(1)).deleteById(priceId);
    }

    @Test
    void willThrowIfMetalPriceForDeleteDoesNotExist() {
        Long priceId = 1L;

        when(preciousMetalPriceRepository.existsById(priceId)).thenReturn(false);

        Throwable thrown = catchThrowable(() -> underTest.deleteMetalPrice(priceId));

        assertThat(thrown)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Price with id " + priceId + " doesn't exist");

        verify(preciousMetalPriceRepository, times(1)).existsById(priceId);
        verify(preciousMetalPriceRepository, times(0)).deleteById(anyLong());
    }
}