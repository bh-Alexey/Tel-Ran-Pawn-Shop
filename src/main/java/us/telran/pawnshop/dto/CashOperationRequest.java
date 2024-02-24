package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.OrderType;

import java.math.BigDecimal;

@Data
public class CashOperationRequest {

    private OrderType orderType;
    private BigDecimal operationAmount;
}

