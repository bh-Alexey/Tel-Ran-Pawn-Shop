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

/**
 * Represents a record of a cash operation in the system, linked to a specific pawn branch and manager.
 * This class is annotated to be managed by JPA for persistence in a relational database and leverages Spring's
 * auditing capabilities to automatically capture the creation timestamp of each operation.
 * Fields:
 * <ul>
 *     <li>{@code operationId} - The primary key of the cash operation record.</li>
 *     <li>{@code pawnBranch} - The pawn branch associated with this cash operation.</li>
 *     <li>{@code manager} - The manager responsible for the cash operation.</li>
 *     <li>{@code orderType} - The type of operation (e.g., income, expense).</li>
 *     <li>{@code operationAmount} - The monetary amount involved in the operation.</li>
 *     <li>{@code description} - A brief description of the cash operation.</li>
 *     <li>{@code createdAt} - The timestamp when the record was created, automatically set by Spring Data JPA.</li>
 * </ul>
 *
 * This entity is essential for tracking financial transactions related to pawn operations, offering insights into cash
 * flow and managerial activities.
 *
 * @author bh-alexey
 *
 */
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
