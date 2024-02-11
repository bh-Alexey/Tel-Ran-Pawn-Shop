package us.telran.pawnshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.LoanTerm;
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




    private final PercentageRepository percentageRepository;
    private final ProductRepository productRepository;

    private final static String PRODUCT = "BORROW";
    private final static BigDecimal COEFFICIENT_CHANGE = new BigDecimal("0.00783");
    private final static BigDecimal DAYS_IN_MONTH = new BigDecimal("30");
    private final static int DIVISION_SCALE = 8;

    @Override
    @Transactional
    public void addPercentage(PercentageCreationRequest percentageCreationRequest) {
        LoanTerm requestTerm = percentageCreationRequest.getTerm();
        percentageRepository.findByTerm(requestTerm)
                .ifPresent(s -> {
                    throw new IllegalStateException("interests for this term introduced");
                });

        Product product = productRepository.findByProductName(ProductName.valueOf(PRODUCT))
                .orElseThrow(() -> new IllegalStateException("Product not found"));

        BigDecimal baseInterestRate = product.getInterestRate();
        int period = requestTerm.getDays();
        BigDecimal interest = calculateInterest(baseInterestRate, period);

        Percentage newPercentage = createNewPercentage(requestTerm, interest);
        percentageRepository.save(newPercentage);
    }

    private BigDecimal calculateInterest(BigDecimal baseInterestRate, int period) {
        BigDecimal periodDays = new BigDecimal(period);

        BigDecimal dailyRateForMonth = baseInterestRate
                .divide(DAYS_IN_MONTH, DIVISION_SCALE, RoundingMode.HALF_UP);
        BigDecimal dailyInterest = dailyRateForMonth.add(COEFFICIENT_CHANGE
                .multiply(periodDays.subtract(DAYS_IN_MONTH)));

        return dailyInterest.setScale(DIVISION_SCALE, RoundingMode.HALF_UP);
    }

    private Percentage createNewPercentage(LoanTerm term, BigDecimal interest){
        Percentage percentage = new Percentage();
        percentage.setTerm(term);
        percentage.setInterest(interest);
        return percentage;
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
