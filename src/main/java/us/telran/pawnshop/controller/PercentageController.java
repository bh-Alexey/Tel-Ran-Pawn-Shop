package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.service.PercentageService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "pawn-shop/interest-grid")
public class PercentageController {

    private final PercentageService percentageService;

    @PostMapping(value = "add")
    @Operation(summary = "NEW PERCENTAGE", description = "Add percentage for particular term to interest grid and save to the DB")
    public void addPercentage(@RequestBody PercentageCreationRequest percentageCreationRequest) {
        percentageService.addPercentage(percentageCreationRequest);
    }

    @GetMapping("show")
    @Operation(summary = "INTEREST GRID", description = "Show interest grid for all terms in DB")
    public List<Percentage> getInterestGrid() {
        return percentageService.getInterestGrid();
    }

    @PutMapping(path = "update/{percentageId}")
    @Operation(summary = "EDIT PERCENTAGE", description = "Modify and percentage details with specified id")
    public void updatePercentage(
            @PathVariable("percentageId") Long percentageId,
            @RequestParam(required = false) BigDecimal interest) {
        percentageService.updatePercentage(percentageId, interest);
    }

    @DeleteMapping(path = "remove/{percentageId}")
    @Operation(summary = "DELETE PERCENTAGE", description = "Delete percentage with specified id from the DB")
    public void deletePercentage(@PathVariable("percentageId") Long percentageId) {
        percentageService.deletePercentage(percentageId);
    }

}
