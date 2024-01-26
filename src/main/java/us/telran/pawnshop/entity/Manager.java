package us.telran.pawnshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import us.telran.pawnshop.entity.enums.ManagerStatus;

import java.sql.Timestamp;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
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

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "status")
    @Enumerated(STRING)
    private ManagerStatus managerStatus;

}
