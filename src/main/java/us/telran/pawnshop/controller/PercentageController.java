package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.service.PercentageService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/interest-grid")
public class PercentageController {

    private final PercentageService percentageService;

    @PostMapping(value = "add")
    public void addPercentage(@RequestBody PercentageCreationRequest percentageCreationRequest) {
        percentageService.addPercentage(percentageCreationRequest);
    }

    @GetMapping("show")
    public List<Percentage> getInterestGrid() {
        return percentageService.getInterestGrid();
    }

    @DeleteMapping(path = "remove/{percentageId}")
    public void deletePercentage(@PathVariable("percentageId") Long percentageId) {
        percentageService.deletePercentage(percentageId);
    }

    @PutMapping(path = "update/{percentageId}")
    public void updatePercentage(
            @PathVariable("percentageId") Long percentageId,
            @RequestParam(required = false) BigDecimal interest) {
        percentageService.updatePercentage(percentageId, interest);
    }

}
