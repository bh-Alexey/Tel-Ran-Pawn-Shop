package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.CreditTerm;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.repository.PercentageRepository;
import us.telran.pawnshop.repository.ProductRepository;
import us.telran.pawnshop.service.PercentageService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PercentageServiceImpl implements PercentageService {

    private final static String PRODUCT = "BORROW";


    private final PercentageRepository percentageRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void addPercentage(PercentageCreationRequest percentageCreationRequest) {
        Optional<Percentage> percentageOptional = percentageRepository.findByTerm(percentageCreationRequest.getTerm());
        if (percentageOptional.isPresent()) {
            throw new IllegalStateException("interests for this term introduced");
        }

        Optional<Product> productOptional = productRepository.findByProductName(ProductName.valueOf(PRODUCT));
        Product product = productOptional.get();
        BigDecimal baseInterestRate = product.getInterestRate();

        Percentage percentage = new Percentage();
        percentage.setTerm(percentageCreationRequest.getTerm());
        percentage.setInterest(calculateInterest(baseInterestRate, percentage.getTerm().getDays()));
        percentageRepository.save(percentage);
    }

    private BigDecimal calculateInterest(BigDecimal baseInterestRate, int period) {
        return baseInterestRate.divide(BigDecimal.valueOf(CreditTerm.MONTH.getDays()), 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(period));
    }

    @Override
    public List<Percentage> getInterestGrid() {
        return percentageRepository.findAll();
    }

    @Override
    public void updatePercentage(Long percentageId, int period, BigDecimal interest) {

    }

    @Override
    public void deletePercentage(Long percentageId) {

    }

}
