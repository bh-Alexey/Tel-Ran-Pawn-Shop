package us.telran.pawnshop.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientCreationRequest {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String address;
}
