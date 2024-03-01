package us.telran.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.dto.PreciousMetalPriceCreationRequest;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.service.PreciousMetalPriceService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "pawn-shop/price/precious-metal")
public class PreciousMetalPriceController {

    private final PreciousMetalPriceService preciousMetalPriceService;

    @PostMapping(value = "add")
    @Operation(summary = "NEW PRICE", description = "Add price for new subject of pledge and save to the DB")
    public void addNewPrice(@Valid @RequestBody PreciousMetalPriceCreationRequest preciousMetalPriceCreationRequest) {
        preciousMetalPriceService.addNewPrice(preciousMetalPriceCreationRequest);
    }

    @GetMapping(value = "show")
    @Operation(summary = "ALL PRICES", description = "Show price for all subjects of pledge in DB")
    public List<PreciousMetalPrice> getMetalPrice() {
        return preciousMetalPriceService.getMetalPrice();
    }

    @PutMapping(path = "change/{priceId}")
    @Operation(summary = "EDIT PRICE", description = "Change price for subject of pledge with mentioned id")
    public void updateMetalPrice(@PathVariable("priceId") Long priceId,
                                 @RequestParam(required = false) BigDecimal metalPrice) {
        preciousMetalPriceService.updateMetalPrice(priceId, metalPrice);
    }

    @DeleteMapping(path = "remove/{priceId}")
    @Operation(summary = "DELETE PRICE", description = "Remove subject's of pledge price with specified id from DB")
    public void deleteMetalPrice(@PathVariable("priceId") Long priceId) {
        preciousMetalPriceService.deleteMetalPrice(priceId);
    }

}
