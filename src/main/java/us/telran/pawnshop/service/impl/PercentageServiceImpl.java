package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.Product;
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

    private final static int WEEK = 7;
    private final static int TWO_WEEKS = 14;
    private final static int THREE_WEEKS = 21;
    private final static int MONTH = 28;


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
        switch (percentageCreationRequest.getTerm()) {
            case WEEK:
                percentage.setInterest(calculateInterest(baseInterestRate, WEEK));
                break;
            case TWO_WEEKS:
                percentage.setInterest(calculateInterest(baseInterestRate, TWO_WEEKS));
                break;
            case THREE_WEEKS:
                percentage.setInterest(calculateInterest(baseInterestRate, THREE_WEEKS));
                break;
            case MONTH:
                percentage.setInterest(baseInterestRate);
                break;
        }
        percentageRepository.save(percentage);
    }

    private BigDecimal calculateInterest(BigDecimal baseInterestRate, int period) {
        return baseInterestRate.divide(BigDecimal.valueOf(MONTH), 10, RoundingMode.HALF_UP)
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
