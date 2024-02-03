package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.telran.pawnshop.entity.enums.CreditStatus;

import java.sql.Timestamp;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credits")
public class Credit {

    public enum CreditTerm {
        WEEK, TWO_WEEKS, THREE_WEEKS, MONTH
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long creditId;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "pledge_id")
    private Pledge pledge;

    @Column(name = "credit_amount")
    private double creditAmount;

    @Column(name = "term")
    @Enumerated(STRING)
    private CreditTerm term;

    @Column(name = "ransom_amount")
    private double ransomAmount;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "credit_status")
    @Enumerated(STRING)
    private CreditStatus status;
}
