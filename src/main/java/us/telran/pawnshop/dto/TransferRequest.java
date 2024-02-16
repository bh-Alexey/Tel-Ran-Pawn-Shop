package us.telran.pawnshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    private Long fromBranchId;
    private Long toBranchId;
    private BigDecimal transferAmount;
}
