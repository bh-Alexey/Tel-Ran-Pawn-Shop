package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.repository.PreciousMetalPriceRepository;
import us.telran.pawnshop.service.PreciousMetalPriceService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreciousMetalPriceServiceImpl implements PreciousMetalPriceService {

    private final PreciousMetalPriceRepository preciousMetalPriceRepository;
    @Override
    public void updateMetalPrice(Long priceId, double metalPrice) {
        PreciousMetalPrice preciousMetalPrice = preciousMetalPriceRepository.findById(priceId)
                .orElseThrow(() -> new IllegalStateException("Manager with id " + priceId + " doesn't exist"));
        preciousMetalPrice.setMetalPrice(metalPrice);
    }

    @Override
    public void addNewPrice(PreciousMetalPrice preciousMetalPrice) {
        Optional<PreciousMetalPrice> preciousMetalPriceOptional = preciousMetalPriceRepository.findMetalByPurity(preciousMetalPrice.getPurity());
        if (preciousMetalPriceOptional.isPresent()) {
            throw new IllegalStateException("Price presented");
        }
        preciousMetalPriceRepository.save(preciousMetalPrice);
    }

    @Override
    public List<PreciousMetalPrice> getMetalPrice() {
        return preciousMetalPriceRepository.findAll();
    }

    @Override
    public void deleteMetalPrice(Long priceId) {
        boolean exists = preciousMetalPriceRepository.existsById(priceId);
        if (!exists) {
            throw new IllegalStateException("Price with id " + priceId + " doesn't exist");
        }
        preciousMetalPriceRepository.deleteById(priceId);
    }
}
