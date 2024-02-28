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

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

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
            sequenceName = "operation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "operation_sequence"
    )
    private Long operationId;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private PawnBranch pawnBranch;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "manager_id")
    private Manager manager;


    @Column(name = "order_type")
    @Enumerated(STRING)
    private OrderType orderType;

    @Column(name = "amount")
    private BigDecimal operationAmount;

    @Column(name = "description")
    private String description;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

}
