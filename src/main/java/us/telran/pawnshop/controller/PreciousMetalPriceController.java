package us.telran.pawnshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.service.PreciousMetalPriceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/price/precious-metal")
public class PreciousMetalPriceController {

    private final PreciousMetalPriceService preciousMetalPriceService;

    @PostMapping(value = "add")
    public void addNewPrice(@RequestBody PreciousMetalPrice preciousMetalPrice) {
        preciousMetalPriceService.addNewPrice(preciousMetalPrice);
    }

    @GetMapping
    public List<PreciousMetalPrice> getMetalPrice() {
        return preciousMetalPriceService.getMetalPrice();
    }

    @DeleteMapping(path = "remove/{priceId}")
    public void deleteMetalPrice(@PathVariable("priceId") Long priceId) {
        preciousMetalPriceService.deleteMetalPrice(priceId);
    }

    @PutMapping(path = "change/{priceId}")
    public void updateMetalPrice(@PathVariable("priceId") Long priceId,
                           @RequestParam(required = false) double metalPrice) {
        preciousMetalPriceService.updateMetalPrice(priceId, metalPrice);
    }

}
