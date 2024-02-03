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

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

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

    @Column(name = "purity")
    @Enumerated(STRING)
    public MetalPurity purity;

    @Column(name = "price")
    private BigDecimal metalPrice;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public PreciousMetalPrice(PledgeCategory category, MetalPurity purity, BigDecimal metalPrice) {
        this.category = category;
        this.purity = purity;
        this.metalPrice = metalPrice;
    }
}
