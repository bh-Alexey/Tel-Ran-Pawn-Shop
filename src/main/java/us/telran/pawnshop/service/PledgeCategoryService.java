package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.util.List;

public interface PledgeCategoryService {

    void addNewCategory(PledgeCategory categoryName);

    List<PledgeCategory> getCategories();

    void deleteCategory(Long categoryId);

    void updateCategory(Long categoryId, PreciousMetal categoryName);
}
