package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.repository.PercentageRepository;
import us.telran.pawnshop.repository.ProductRepository;
import us.telran.pawnshop.service.PercentageService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PercentageServiceImpl implements PercentageService {

    private final PercentageRepository percentageRepository;
    private final ProductRepository productRepository;

    @Value("${pawnshop.product}")
    private String productName;

    @Value("${pawnshop.coefficient.change}")
    private BigDecimal coefficientChange;

    @Value("${pawnshop.days.in.month}")
    private BigDecimal daysInMonth;

    @Value("${pawnshop.division.scale}")
    private int divisionScale;

    @Override
    @Transactional
    public void addPercentage(PercentageCreationRequest percentageCreationRequest) {
        LoanTerm requestTerm = percentageCreationRequest.getTerm();

        checkRequest(requestTerm);

        BigDecimal baseInterestRate = getProductRate(this.productName);
        int period = requestTerm.getDays();
        BigDecimal interest = calculateInterest(baseInterestRate, period);

        Percentage newPercentage = createNewPercentage(requestTerm, interest);

        percentageRepository.save(newPercentage);
    }

    private BigDecimal calculateInterest(BigDecimal baseInterestRate, int period) {
        BigDecimal periodDays = new BigDecimal(period);

        BigDecimal dailyRateForMonth = baseInterestRate
                .divide(daysInMonth, divisionScale, RoundingMode.HALF_UP);
        BigDecimal dailyInterest = dailyRateForMonth.add(coefficientChange
                .multiply(periodDays.subtract(daysInMonth)));

        return dailyInterest.setScale(divisionScale, RoundingMode.HALF_UP);
    }

    private Percentage createNewPercentage(LoanTerm term, BigDecimal interest){
        Percentage percentage = new Percentage();
        percentage.setTerm(term);
        percentage.setInterest(interest);
        return percentage;
    }

    private void checkRequest(LoanTerm term) {
        percentageRepository.findByTerm(term)
                .ifPresent(exception -> {
                    throw new IllegalStateException("Percentage for this term introduced");
                });
    }

    private BigDecimal getProductRate(String productName) {
        return productRepository.findByProductName(productName)
                .map(Product::getInterestRate)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with name: " + productName));
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
