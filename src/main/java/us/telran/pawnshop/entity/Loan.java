package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long loanId;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "pledge_id")
    private Pledge pledge;

    @NotNull
    @Column(name = "loan_amount", nullable = false)
    private BigDecimal loanAmount;

    @NotNull
    @Column(name = "term", nullable = false)
    @Enumerated(STRING)
    private LoanTerm term;

    @NotNull
    @Column(name = "ransom_amount", nullable = false)
    private BigDecimal ransomAmount;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Future
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "loan_status")
    @Enumerated(STRING)
    private LoanStatus status;

    @PrePersist
    public void expiredAt() {
        this.expiredAt = this.createdAt.toLocalDateTime().plusDays(this.getTerm().getDays());
    }

    public Loan(Pledge pledge, BigDecimal loanAmount, LoanTerm term) {
        this.pledge = pledge;
        this.loanAmount = loanAmount;
        this.term = term;
    }
}
