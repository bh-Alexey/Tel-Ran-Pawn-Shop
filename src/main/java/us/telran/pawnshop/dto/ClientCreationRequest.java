package us.telran.pawnshop.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientCreationRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int socialSecurityNumber;
    private String email;
    private String address;
    public boolean isValid() {
        return firstName != null && !firstName.isEmpty() &&
                lastName != null && !lastName.isEmpty() &&
                dateOfBirth != null &&
                email != null && !email.isEmpty() &&
                address != null && !address.isEmpty();
    }
}
