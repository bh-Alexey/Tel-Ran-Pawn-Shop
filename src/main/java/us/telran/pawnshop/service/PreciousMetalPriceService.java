package us.telran.pawnshop.service;

import us.telran.pawnshop.entity.PreciousMetalPrice;

import java.util.List;

public interface PreciousMetalPriceService {
    void updateMetalPrice(Long priceId, double metalPrice);

    void addNewPrice(PreciousMetalPrice preciousMetalPrice);

    List<PreciousMetalPrice> getMetalPrice();

    void deleteMetalPrice(Long priceId);
}
