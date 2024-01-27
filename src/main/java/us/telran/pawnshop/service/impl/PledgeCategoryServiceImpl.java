package us.telran.pawnshop.service.impl;

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
            throw new IllegalStateException("Category introduced");
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
            throw new IllegalStateException("Category with id " + categoryId + " doesn't exist");
        }
        pledgeCategoryRepository.deleteById(categoryId);
    }


    @Override
    @Transactional
    public void updateCategory(Long categoryId, PreciousMetal categoryName) {

            PledgeCategory category = pledgeCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalStateException("Category with id " + categoryId + " doesn't exist"));

            if (categoryName != null && !Objects.equals(category.getCategoryName(), categoryName)) {

                Optional<PledgeCategory> pledgeCategoryOptional = pledgeCategoryRepository.findByCategoryName(categoryName);
                if (pledgeCategoryOptional.isPresent()) {
                    throw new IllegalStateException("Category introduced");
                }
                category.setCategoryName(categoryName);
            }
    }
}
