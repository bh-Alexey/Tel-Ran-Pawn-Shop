package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.ClientStatus;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class Client {

    @Value("${pawnshop.ssn.origin}")
    @Transient
    private int ssnOrigin;

    @Value("${pawnshop.ssn.bound}")
    @Transient
    private int ssnBound;

    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "client_sequence"
    )
    @Column(name = "client_id", updatable = false)
    private Long clientId;

    @NotNull
    @Column(name = "status", nullable = false, updatable = true)
    @Enumerated(STRING)
    private ClientStatus status;

    @NotNull
    @Column(name = "ssn", length = 9, nullable = false, unique = true)
    private int socialSecurityNumber;

    @NotNull
    @Past
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "address", nullable = false)
    private String address;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    public void generateSocialSecurityNumber() {
        this.socialSecurityNumber = ThreadLocalRandom.current().nextInt(ssnOrigin, ++ssnBound);
    }

    public Client(ClientStatus status,
                  int socialSecurityNumber,
                  LocalDate dateOfBirth,
                  String firstName,
                  String lastName,
                  String email,
                  String address
    ) {
        this.status = status;
        this.socialSecurityNumber = socialSecurityNumber;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

}
