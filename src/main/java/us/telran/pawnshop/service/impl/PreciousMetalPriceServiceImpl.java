package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.PreciousMetalPriceCreationRequest;
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
    public void addNewPrice(PreciousMetalPriceCreationRequest preciousMetalPriceCreationRequest) {

        Optional<PreciousMetalPrice> preciousMetalPriceOptional = preciousMetalPriceRepository
                .findByPurity(preciousMetalPriceCreationRequest.getPurity());
        if (preciousMetalPriceOptional.isPresent()) {
            throw new EntityExistsException("Price for this purity " + preciousMetalPriceCreationRequest.getPurity()
                                            + " already presented");
        }
        else {
            PreciousMetalPrice newPrice = new PreciousMetalPrice();
            newPrice.setCategory(pledgeCategoryRepository
                    .getReferenceById(preciousMetalPriceCreationRequest.getCategoryId()));
            newPrice.setPurity(preciousMetalPriceCreationRequest.getPurity());
            newPrice.setMetalPrice(preciousMetalPriceCreationRequest.getMetalPrice());

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
                .orElseThrow(() -> new EntityNotFoundException("Manager with id " + priceId + " doesn't exist"));
        preciousMetalPrice.setMetalPrice(metalPrice);
    }

    @Override
    @Transactional
    public void deleteMetalPrice(Long priceId) {
        boolean exists = preciousMetalPriceRepository.existsById(priceId);
        if (!exists) {
            throw new EntityNotFoundException("Price with id " + priceId + " doesn't exist");
        }
        preciousMetalPriceRepository.deleteById(priceId);
    }
}
