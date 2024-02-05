package us.telran.pawnshop.service;


import us.telran.pawnshop.dto.PercentageCreationRequest;
import us.telran.pawnshop.entity.Percentage;

import java.math.BigDecimal;
import java.util.List;

public interface PercentageService {
    void addPercentage(PercentageCreationRequest percentageCreationRequest);

    List<Percentage> getInterestGrid();

    void updatePercentage(Long percentageId, int period, BigDecimal interest);

    void deletePercentage(Long percentageId);
}
