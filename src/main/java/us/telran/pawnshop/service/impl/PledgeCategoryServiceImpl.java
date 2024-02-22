package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.PreciousMetal;
import us.telran.pawnshop.repository.PledgeCategoryRepository;
import us.telran.pawnshop.service.PledgeCategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PledgeCategoryServiceImpl implements PledgeCategoryService {

    public final PledgeCategoryRepository pledgeCategoryRepository;

    @Override
    public void addNewCategory(PledgeCategory categoryName) {
        Optional<PledgeCategory> pledgeCategoryOptional = pledgeCategoryRepository.findByCategoryName(categoryName.getCategoryName());
        if (pledgeCategoryOptional.isPresent()) {
            throw new EntityExistsException("Category introduced");
        }
        pledgeCategoryRepository.save(categoryName);
    }

    @Override
    public List<PledgeCategory> getCategories() {
        return pledgeCategoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        boolean exists = pledgeCategoryRepository.existsById(categoryId);
        if (!exists) {
            throw new EntityNotFoundException("Category with id " + categoryId + " doesn't exist");
        }
        pledgeCategoryRepository.deleteById(categoryId);
    }


    @Override
    @Transactional
    public void updateCategory(Long categoryId, PreciousMetal newCategoryName) {
        PledgeCategory category = pledgeCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + categoryId + " does not exist"));

        if (newCategoryName == null || Objects.equals(category.getCategoryName(), newCategoryName)) {
            throw new IllegalStateException("Invalid category name provided");
        }

        category.setCategoryName(newCategoryName);
        pledgeCategoryRepository.save(category);
    }
}



