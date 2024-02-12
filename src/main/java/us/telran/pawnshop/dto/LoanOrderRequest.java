package us.telran.pawnshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanOrderRequest {

    private Long loanId;
    private BigDecimal orderAmount;

}
