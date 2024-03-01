package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.ItemType;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.entity.enums.PledgeStatus;

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
@Table(name = "pledges")
public class Pledge {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long pledgeId;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private PledgeCategory category;

    @Column(name = "item")
    @Enumerated(STRING)
    private ItemType item;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int itemQuantity;

    @Column(name = "stamp")
    @Enumerated(STRING)
    private MetalPurity purity;

    @Column(name = "gross_weight")
    private BigDecimal weightGross;

    @Column(name = "net_weight")
    private BigDecimal weightNet;

    @Positive
    @Column(name = "price", nullable = false)
    private BigDecimal estimatedPrice;

    @NotNull
    @Column(name = "status")
    @Enumerated(STRING)
    private PledgeStatus status;

    @NotNull
    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;

    @NotNull
    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;

    public Pledge(Product product,
                  Manager manager,
                  Client client,
                  PledgeCategory category,
                  ItemType item,
                  String description,
                  int itemQuantity,
                  MetalPurity purity,
                  BigDecimal weightGross,
                  BigDecimal weightNet
    ) {
        this.product = product;
        this.manager = manager;
        this.client = client;
        this.category = category;
        this.item = item;
        this.description = description;
        this.itemQuantity = itemQuantity;
        this.purity = purity;
        this.weightGross = weightGross;
        this.weightNet = weightNet;
    }
}
