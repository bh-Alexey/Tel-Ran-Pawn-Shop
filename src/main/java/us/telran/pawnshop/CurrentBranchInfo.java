package us.telran.pawnshop;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class CurrentBranchInfo {

    private Long branchId;
    private String address;
    BigDecimal balance;

}
