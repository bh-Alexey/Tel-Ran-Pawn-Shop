package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.OrderType;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cash_operations")
public class CashOperation {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "order_sequence"
    )
    private Long operationId;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private PawnBranch pawnBranch;

    @Column(name = "order_type")
    private OrderType orderType;

    @Column(name = "amount")
    private BigDecimal operationAmount;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;


}
