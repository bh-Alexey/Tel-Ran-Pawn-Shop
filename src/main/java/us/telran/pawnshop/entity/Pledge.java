package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.telran.pawnshop.entity.enums.ItemType;
import us.telran.pawnshop.entity.enums.PledgeStatus;

import java.sql.Timestamp;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "pledges")
public class Pledge {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long pledgeId;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private PledgeCategory category;

    @Column(name = "item")
    @Enumerated(STRING)
    private ItemType item;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "price")
    private Double estimatedPrice;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    @Column(name = "status")
    @Enumerated(STRING)
    private PledgeStatus status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
