package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class PledgeCategory {

    @Id
    @SequenceGenerator(
            name = "categories_sequence",
            sequenceName = "categories_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "categories_sequence"
    )
    private Long categoryId;

    @Column(name = "precious_metal")
    @Enumerated(STRING)
    private PreciousMetal categoryName;

    @OneToMany(mappedBy = "category", cascade = ALL, orphanRemoval = true)
    private List<PreciousMetalPrice> metalPrices;
}
