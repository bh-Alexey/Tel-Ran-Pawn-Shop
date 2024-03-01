package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.LoanTerm;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "interest_grid")
public class Percentage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long percentageId;

    @Column(name = "period", nullable = false)
    @Enumerated(STRING)
    private LoanTerm term;

    @NotNull
    @Column(name = "interest", nullable = false, unique = true)
    private BigDecimal interest;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
