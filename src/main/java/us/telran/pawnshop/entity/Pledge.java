package us.telran.pawnshop.entity;

import jakarta.persistence.*;
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
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "category_id")
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

    @Column(name = "price")
    private BigDecimal estimatedPrice;

    @Column(name = "status")
    @Enumerated(STRING)
    private PledgeStatus status;

    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;

}
