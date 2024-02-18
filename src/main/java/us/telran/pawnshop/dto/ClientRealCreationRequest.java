package us.telran.pawnshop.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientRealCreationRequest {
    private String firstName;
    private String lastName;
    private int socialSecurityNumber;
    private LocalDate dateOfBirth;
    private String email;
    private String address;
}
