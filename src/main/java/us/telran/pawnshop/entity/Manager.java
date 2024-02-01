package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import us.telran.pawnshop.entity.enums.ManagerStatus;

import java.sql.Timestamp;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@Table(name = "managers")
public class Manager {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long managerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "status")
    @Enumerated(STRING)
    private ManagerStatus managerStatus;

    public Manager(String firstName, String lastName, String email, ManagerStatus managerStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.managerStatus = managerStatus;
    }
}
