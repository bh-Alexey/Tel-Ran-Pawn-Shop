package us.telran.pawnshop.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientRealCreationRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @Digits(integer=9, fraction=0)
    private int socialSecurityNumber;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Email
    private String email;

    @NotBlank
    private String address;
}
