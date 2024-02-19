package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long productId;

    @NotBlank
    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @NotNull
    @Column(name = "status", nullable = false, updatable = true)
    @Enumerated(STRING)
    private ProductStatus status;

    @Column(name = "rate")
    private BigDecimal interestRate;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Product(String productName, ProductStatus status, BigDecimal interestRate) {
        this.productName = productName;
        this.status = status;
        this.interestRate = interestRate;
    }
}
