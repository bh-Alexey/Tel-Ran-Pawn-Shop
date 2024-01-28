package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;

import java.sql.Timestamp;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    private ProductName productName;

    @Column(name = "status")
    @Enumerated(STRING)
    private ProductStatus status;

    @Column(name = "rate")
    private double interestRate;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
