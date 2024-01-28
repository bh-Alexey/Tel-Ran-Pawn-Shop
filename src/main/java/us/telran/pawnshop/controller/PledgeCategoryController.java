package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.PreciousMetal;
import us.telran.pawnshop.service.PledgeCategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "api/pledge-category/")
@RequiredArgsConstructor
public class PledgeCategoryController {

    private final PledgeCategoryService pledgeCategoryService;

    @GetMapping(value = "show")
    public List<PledgeCategory> getCategories() {
        return pledgeCategoryService.getCategories();
    }

    @PostMapping(value = "new")
    public void createNewCategory(@RequestBody PledgeCategory categoryName) {
        pledgeCategoryService.addNewCategory(categoryName);
    }

    @DeleteMapping(path = "remove/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        pledgeCategoryService.deleteCategory(categoryId);
    }

    @PutMapping(path = "update/{categoryId}")
    public void updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(required = false) PreciousMetal categoryName) {
        pledgeCategoryService.updateCategory(categoryId, categoryName);
    }

}
