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
@Table(name = "orders")
public class LoanOrder {

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
    private Long orderId;

    @Column(name = "order_type")
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(name = "amount")
    private BigDecimal orderAmount;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

}
