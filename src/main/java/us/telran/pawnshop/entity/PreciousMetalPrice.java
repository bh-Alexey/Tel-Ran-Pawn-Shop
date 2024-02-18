package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "purity",nullable = false, unique = true)
    @Enumerated(STRING)
    public MetalPurity purity;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal metalPrice;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
