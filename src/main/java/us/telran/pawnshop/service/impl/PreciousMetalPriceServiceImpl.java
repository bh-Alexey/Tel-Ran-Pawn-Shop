package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.repository.PledgeCategoryRepository;
import us.telran.pawnshop.repository.PreciousMetalPriceRepository;
import us.telran.pawnshop.service.PreciousMetalPriceService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreciousMetalPriceServiceImpl implements PreciousMetalPriceService {

    private final PreciousMetalPriceRepository preciousMetalPriceRepository;

    private final PledgeCategoryRepository pledgeCategoryRepository;


    @Override
    @Transactional
    public void addNewPrice(Long categoryId, MetalPurity purity, BigDecimal metalPrice) {

        Optional<PreciousMetalPrice> preciousMetalPriceOptional = preciousMetalPriceRepository.findMetalByPurity(purity);
        if (preciousMetalPriceOptional.isPresent()) {
            throw new IllegalStateException("Price for this purity " + purity + " already presented");
        }
        else {
            PreciousMetalPrice newPrice = new PreciousMetalPrice();
            newPrice.setCategory(pledgeCategoryRepository.getReferenceById(categoryId));
            newPrice.setPurity(purity);
            newPrice.setMetalPrice(metalPrice);
            preciousMetalPriceRepository.save(newPrice);
        }
    }

    @Override
    public List<PreciousMetalPrice> getMetalPrice() {
        return preciousMetalPriceRepository.findAll();
    }

    @Override
    @Transactional
    public void updateMetalPrice(Long priceId, BigDecimal metalPrice) {
        PreciousMetalPrice preciousMetalPrice = preciousMetalPriceRepository.findById(priceId)
                .orElseThrow(() -> new IllegalStateException("Manager with id " + priceId + " doesn't exist"));
        preciousMetalPrice.setMetalPrice(metalPrice);
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
