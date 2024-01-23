package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.telran.pawnshop.entity.enums.MetalPurity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
public class MetalPrice {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private PledgeCategory category;

    @Column(name = "purity")
    @Enumerated(STRING)
    public MetalPurity purity;

    @Column
    private double metalPrice;


}
