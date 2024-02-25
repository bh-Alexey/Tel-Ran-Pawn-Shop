package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.PreciousMetal;
import us.telran.pawnshop.service.PledgeCategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "pawn-shop/pledge-categories/")
@RequiredArgsConstructor
public class PledgeCategoryController {

    private final PledgeCategoryService pledgeCategoryService;

    @PostMapping(value = "new")
    @Operation(summary = "NEW CATEGORY", description = "Create and save category of pledge as a subject")
    public void createNewCategory(@RequestBody PledgeCategory categoryName) {
        pledgeCategoryService.addNewCategory(categoryName);
    }

    @GetMapping(value = "show")
    @Operation(summary = "ALL CATEGORIES", description = "Show all pledge categories")
    public List<PledgeCategory> getCategories() {
        return pledgeCategoryService.getCategories();
    }

    @PutMapping(path = "update/{categoryId}")
    @Operation(summary = "EDIT CATEGORY", description = "Modify category's details with specified id")
    public void updateCategory(@PathVariable("categoryId") Long categoryId,
                               @RequestParam(required = false) PreciousMetal categoryName
    ) {
        pledgeCategoryService.updateCategory(categoryId, categoryName);
    }

    @DeleteMapping(path = "remove/{categoryId}")
    @Operation(summary = "DELETE CATEGORY", description = "Delete pledge category with specified id from the DB")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        pledgeCategoryService.deleteCategory(categoryId);
    }

}
