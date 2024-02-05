package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.CreditStatus;
import us.telran.pawnshop.entity.enums.CreditTerm;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credits")
public class Credit {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long creditId;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "pledge_id")
    private Pledge pledge;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    @Column(name = "term")
    @Enumerated(STRING)
    private CreditTerm term;

    @Column(name = "ransom_amount")
    private BigDecimal ransomAmount;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "credit_status")
    @Enumerated(STRING)
    private CreditStatus status;
}
