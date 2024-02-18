package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.util.List;

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

    @NotNull
    @Column(name = "precious_metal", nullable = false, unique = true)
    @Enumerated(STRING)
    private PreciousMetal categoryName;

    public PledgeCategory(PreciousMetal categoryName) {
        this.categoryName = categoryName;
    }

}
