package us.telran.pawnshop.service;

import us.telran.pawnshop.dto.PreciousMetalPriceCreationRequest;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.entity.enums.MetalPurity;

import java.math.BigDecimal;
import java.util.List;

public interface PreciousMetalPriceService {

    void addNewPrice(PreciousMetalPriceCreationRequest preciousMetalPriceCreationRequest);
    void updateMetalPrice(Long priceId, BigDecimal metalPrice);
    List<PreciousMetalPrice> getMetalPrice();
    void deleteMetalPrice(Long priceId);
}
