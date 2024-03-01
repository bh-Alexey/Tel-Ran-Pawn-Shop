package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.MetalPurity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "metal_price")
public class PreciousMetalPrice {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long priceId;

    @ManyToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private PledgeCategory category;

    @Column(name = "purity",nullable = false, unique = true)
    @Enumerated(STRING)
    public MetalPurity purity;

    @Column(name = "price", nullable = false)
    private BigDecimal metalPrice;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
