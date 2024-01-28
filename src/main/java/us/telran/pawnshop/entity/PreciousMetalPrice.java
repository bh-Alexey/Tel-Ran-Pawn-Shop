package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.telran.pawnshop.entity.enums.MetalPurity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "metal_price")
public class PreciousMetalPrice {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long priceId;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "category_id")
    private PledgeCategory categoryId;

    @Column(name = "purity")
    @Enumerated(STRING)
    public MetalPurity purity;

    @Column
    private double metalPrice;
}
