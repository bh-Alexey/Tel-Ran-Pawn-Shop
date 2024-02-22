package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.PreciousMetal;
import us.telran.pawnshop.repository.PledgeCategoryRepository;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static us.telran.pawnshop.entity.enums.PreciousMetal.*;

@ExtendWith(MockitoExtension.class)
class PledgeCategoryServiceImplTest {

    @Mock
    private PledgeCategoryRepository pledgeCategoryRepository;

    @InjectMocks
    private PledgeCategoryServiceImpl underTest;

    @Test
    void canAddNewCategory() {
        //Given
        PledgeCategory newCategory = new PledgeCategory(SILVER);

        //When
        when(pledgeCategoryRepository.findByCategoryName(newCategory.getCategoryName()))
                .thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> underTest.addNewCategory(newCategory));

        assertThat(thrown).isNull();
        verify(pledgeCategoryRepository, times(1)).findByCategoryName(newCategory.getCategoryName());
        verify(pledgeCategoryRepository, times(1)).save(newCategory);

    }

    @Test
    void willThrowIfCategoryIntroduced() {
        //Given
        PledgeCategory existingCategory = new PledgeCategory(SILVER);

        //When
        when(pledgeCategoryRepository.findByCategoryName(existingCategory.getCategoryName()))
                .thenReturn(Optional.of(existingCategory));

        Throwable thrown = catchThrowable(() -> underTest.addNewCategory(existingCategory));

        //Then
        assertThat(thrown)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Category introduced");

        verify(pledgeCategoryRepository, times(1)).findByCategoryName(existingCategory.getCategoryName());
        verify(pledgeCategoryRepository, times(0)).save(any(PledgeCategory.class));
    }

    @Test
    void canGetAllCategories() {
        //when
        underTest.getCategories();

        //then
        verify(pledgeCategoryRepository).findAll();
    }

    @Test
    void canDeleteCategory() {
        //Given
        Long categoryId = 1L;

        //When
        //Then
        when(pledgeCategoryRepository.existsById(categoryId)).thenReturn(true);

        Throwable thrown = catchThrowable(() -> underTest.deleteCategory(categoryId));

        assertThat(thrown).isNull();
        verify(pledgeCategoryRepository, times(1)).existsById(categoryId);
        verify(pledgeCategoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void willThrowIfCategoryForDeleteNotFound() {
        //Given
        Long categoryId = 1L;

        //When
        //Then
        when(pledgeCategoryRepository.existsById(categoryId)).thenReturn(false);

        Throwable thrown = catchThrowable(() -> underTest.deleteCategory(categoryId));

        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Category with id " + categoryId + " doesn't exist");

        verify(pledgeCategoryRepository, times(1)).existsById(categoryId);
        verify(pledgeCategoryRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void canUpdateCategory() {
        // Given
        Long categoryId = 1L;
        PledgeCategory pledgeCategory = new PledgeCategory();
        pledgeCategory.setCategoryName(PreciousMetal.GOLD);

        when(pledgeCategoryRepository.findById(anyLong())).thenReturn(Optional.of(pledgeCategory));

        // When
        underTest.updateCategory(categoryId, PreciousMetal.SILVER);

        // Then
        verify(pledgeCategoryRepository, times(1)).findById(categoryId);
        verify(pledgeCategoryRepository, times(1)).save(any(PledgeCategory.class));
    }

    @Test
    void itShouldThrowIfNameTheSame() {
        // Given
        Long categoryId = 1L;
        PledgeCategory pledgeCategory = new PledgeCategory();
        pledgeCategory.setCategoryName(PreciousMetal.GOLD);

        when(pledgeCategoryRepository.findById(anyLong())).thenReturn(Optional.of(pledgeCategory));

        // Then
        assertThatThrownBy(() -> underTest.updateCategory(categoryId, GOLD))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid category name provided");

        verify(pledgeCategoryRepository, times(1)).findById(categoryId);
        verifyNoMoreInteractions(pledgeCategoryRepository);
    }

    @Test
    void itShouldThrowWhenCategoryForUpdateDoesNotExist() {
        // Given
        Long categoryId = 1L;

        when(pledgeCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> underTest.updateCategory(categoryId, PreciousMetal.SILVER))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Category with id " + categoryId + " does not exist");

        verify(pledgeCategoryRepository, times(1)).findById(categoryId);
        verifyNoMoreInteractions(pledgeCategoryRepository);
    }

}