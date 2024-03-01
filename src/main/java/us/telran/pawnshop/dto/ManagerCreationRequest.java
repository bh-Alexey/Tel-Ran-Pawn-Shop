package us.telran.pawnshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.enums.ManagerStatus;

@Data
public class ManagerCreationRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private ManagerStatus managerStatus;

}
