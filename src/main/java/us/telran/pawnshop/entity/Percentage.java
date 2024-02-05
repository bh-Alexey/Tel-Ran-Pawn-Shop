package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.CreditTerm;

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
    private CreditTerm term;

    @Column(name = "interest")
    private BigDecimal interest;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
