package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import static jakarta.persistence.EnumType.*;

@Entity
@Data
@AllArgsConstructor
public class PledgeCategory {

    @Id
    private Long categoryId;

    @Column(name = "precious_metal")
    @Enumerated(STRING)
    private PreciousMetal categoryName;
}
